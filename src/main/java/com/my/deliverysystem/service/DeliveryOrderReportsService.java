package com.my.deliverysystem.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.my.deliverysystem.dao.implementation.beanImpl.DeliveryOrderBeanDAOImpl;
import com.my.deliverysystem.dao.implementation.beanImpl.ReceiptBeanDAOImpl;
import com.my.deliverysystem.db.entity.bean.DeliveryOrderBean;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DeliveryOrderReportsService {
    final static Logger logger = Logger.getLogger(DeliveryOrderReportsService.class.getName());

    public static void showDeliveryOrders(HttpServletRequest req) {
        DeliveryOrderBeanDAOImpl deliveryOrderService = new DeliveryOrderBeanDAOImpl();
        List<DeliveryOrderBean> deliveryOrders = null;
        try {
            deliveryOrders = deliveryOrderService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("deliveryOrders", deliveryOrders);
    }

    public static void showReportPDF(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Entered showReportPDF()");
        resp.setContentType("application/pdf");
        long id = Long.parseLong(req.getParameter("id"));
        ReceiptBean receipt = null;
        DeliveryOrderBean deliveryOrder = null;

        ReceiptBeanDAOImpl receiptBeanDAOService = new ReceiptBeanDAOImpl();
        DeliveryOrderBeanDAOImpl deliveryOrderBeanDAOService = new DeliveryOrderBeanDAOImpl();
        try {
            receipt = receiptBeanDAOService.getById(id);
            logger.debug(receipt);
            deliveryOrder = deliveryOrderBeanDAOService.getById(receipt.getDeliveryOrderId());
            logger.debug(deliveryOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, resp.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            document.open();

            BaseFont baseFontR= null;
            try {
                baseFontR = BaseFont.createFont("/fonts/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }

            Font fontCNR = new Font(baseFontR);
            fontCNR.setStyle(Font.BOLD);
            fontCNR.setSize(36);
            Paragraph paragraphCNR = new Paragraph("Report\n", fontCNR);
            paragraphCNR.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphCNR);

            Font fontPR = new Font(baseFontR);
            fontPR.setStyle(Font.NORMAL);
            fontPR.setSize(24);

            Paragraph paragraphR = new Paragraph("Receipt", fontPR);
            document.add(paragraphR);

            Font fontT = new Font(baseFontR);
            fontT.setStyle(Font.NORMAL);
            fontT.setSize(16);

            String receiptInfo = null;
            if (receipt != null) {
                receiptInfo = "Receipt id: " + receipt.getId() + "\n" +
                        "User id: " + receipt.getUserId() + "\n" +
                        "Manager: " + receipt.getManagerName() + "\n" +
                        "Price: " + receipt.getPrice() + " UAH\n" +
                        "Current status: " + receipt.getReceiptStatusName() + "\n\n";
            }
            Chunk chunkR = null;
            if (receiptInfo != null) {
                chunkR = new Chunk(receiptInfo, fontT);
            }
            document.add(chunkR);

            Paragraph paragraphDO = new Paragraph("Cargo", fontPR);
            document.add(paragraphDO);

            String deliveryOrderInfo = null;
            if (deliveryOrder != null) {
                deliveryOrderInfo = "Location from: " + deliveryOrder.getLocationFrom() + "\n" +
                        "Location to: " + deliveryOrder.getLocationTo() + "\n" +
                        "Cargo name: " + deliveryOrder.getCargoName() + "\n" +
                        "Cargo description: " + deliveryOrder.getCargoDescription() + "\n" +
                        "Delivery address: " + deliveryOrder.getAddress() + "\n" +
                        "Delivery type: " + deliveryOrder.getDeliveryType() + "\n" +
                        "Weight: " + deliveryOrder.getWeight() + "\n" +
                        "Volume: " + deliveryOrder.getVolume() + "\n" +
                        "Receiving date: " +
                        (deliveryOrder.getReceivingDate() == null ? (!receipt.getReceiptStatusName().equals("Canceled") ?
                                "Delivery in progress" : "Delivery canceled") : deliveryOrder.getReceivingDate()) + "\n" +
                        "Tariff: " + deliveryOrder.getTariffName();
            }

            Chunk chunkDO = null;
            if (deliveryOrderInfo != null) {
                chunkDO = new Chunk(deliveryOrderInfo, fontT);
            }
            document.add(chunkDO);

            document.close();
        } catch (DocumentException de) {
            logger.error(de);
        }
        logger.debug("finished");
    }
}
