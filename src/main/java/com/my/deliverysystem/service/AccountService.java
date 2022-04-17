package com.my.deliverysystem.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.my.deliverysystem.dao.implementation.ReceiptDAOImplementation;
import com.my.deliverysystem.dao.implementation.beanImpl.DeliveryOrderBeanDAOImpl;
import com.my.deliverysystem.dao.implementation.beanImpl.ReceiptBeanDAOImpl;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.db.entity.bean.DeliveryOrderBean;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private static final Logger logger = Logger.getLogger(AccountService.class);

    public static void showReceipts(HttpServletRequest req) {
        ReceiptBeanDAOImpl service = new ReceiptBeanDAOImpl();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<ReceiptBean> receipts = new ArrayList<>();
        try{
            receipts = service.getByUserId(user.getId());
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("receipts", receipts);
    }

    public static void changeStatus(HttpServletRequest req, long status) {
        long id = Long.parseLong(req.getParameter("id"));
        Receipt receipt = null;
        logger.debug(id);
        ReceiptDAOImplementation service = new ReceiptDAOImplementation();
        try {
            receipt = service.getByReceiptId(id);
            logger.debug(receipt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (receipt != null) {
            receipt.setReceiptStatusId(status);
            logger.debug(receipt);
            service.update(receipt);
        }
    }

    public static void showReceiptPDF(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Entered downloadReceipt()");
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

            BaseFont baseFont= null;
            try {
                baseFont = BaseFont.createFont("/fonts/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }

            Font fontCN = new Font(baseFont);
            fontCN.setStyle(Font.BOLD);
            fontCN.setSize(36);
            Paragraph paragraphCN = new Paragraph("CDS\n", fontCN);
            paragraphCN.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphCN);

            Font fontP = new Font(baseFont);
            fontP.setStyle(Font.NORMAL);
            fontP.setSize(24);

            Paragraph paragraphR = new Paragraph("Receipt", fontP);
            document.add(paragraphR);

            Font fontT = new Font(baseFont);
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

            Paragraph paragraphDO = new Paragraph("Cargo", fontP);
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
