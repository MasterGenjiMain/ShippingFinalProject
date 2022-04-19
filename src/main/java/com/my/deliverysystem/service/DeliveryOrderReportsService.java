package com.my.deliverysystem.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.my.deliverysystem.dao.daoInterface.beanDAO.DeliveryOrderBeanDAO;
import com.my.deliverysystem.dao.daoInterface.beanDAO.ReceiptBeanDAO;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.db.entity.bean.DeliveryOrderBean;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DeliveryOrderReportsService {
    final Logger logger = Logger.getLogger(DeliveryOrderReportsService.class.getName());

    DeliveryOrderBeanDAO deliveryOrderService;
    ReceiptBeanDAO receiptBeanDAOService;

    public DeliveryOrderReportsService(DeliveryOrderBeanDAO deliveryOrderService, ReceiptBeanDAO receiptBeanDAOService) {
        this.deliveryOrderService = deliveryOrderService;
        this.receiptBeanDAOService = receiptBeanDAOService;
    }

    public void showDeliveryOrders(HttpServletRequest req) {
        List<DeliveryOrderBean> deliveryOrders = null;
        try {
            deliveryOrders = deliveryOrderService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("deliveryOrders", deliveryOrders);
    }

    public void showReportPDF(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Entered showReportPDF()");
        resp.setContentType("application/pdf");
        long id = Long.parseLong(req.getParameter("id"));
        ReceiptBean receipt = null;
        DeliveryOrderBean deliveryOrder = null;

        try {
            receipt = receiptBeanDAOService.getById(id);
            logger.debug(receipt);
            deliveryOrder = deliveryOrderService.getById(receipt.getDeliveryOrderId());
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
            Paragraph paragraphCNR = new Paragraph("Report", fontCNR);
            paragraphCNR.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphCNR);

            Font fontPR = new Font(baseFontR);
            fontPR.setStyle(Font.NORMAL);
            fontPR.setSize(24);

            Font fontUI = new Font(baseFontR);
            fontUI.setStyle(Font.NORMAL);
            fontUI.setSize(12);

            String managerName = "Created by: " + ((User) req.getSession().getAttribute("user")).getUsername();
            Chunk chunkUN = new Chunk(managerName, fontUI);

            document.add(chunkUN);

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

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);

            String userDate = String.format("\nDate: %s", time);

            Chunk chunkUD = null;
            if (deliveryOrderInfo != null) {
                chunkUD = new Chunk(userDate, fontUI);
            }
            document.add(chunkUD);

            document.close();
        } catch (DocumentException de) {
            logger.error(de);
        }
        logger.debug("finished");
    }
}
