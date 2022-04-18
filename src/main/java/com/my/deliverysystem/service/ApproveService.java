package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.ReceiptDAO;
import com.my.deliverysystem.dao.implementation.DeliveryOrderDAOImplementation;
import com.my.deliverysystem.dao.implementation.ReceiptDAOImplementation;
import com.my.deliverysystem.dao.implementation.beanImpl.ReceiptBeanDAOImpl;
import com.my.deliverysystem.db.entity.DeliveryOrder;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ApproveService {
    final static Logger logger = Logger.getLogger(ApproveService.class.getName());
    private static final int CLOSED_STATUS = 7;

    public static void showReceipts(HttpServletRequest req) {
        ReceiptBeanDAOImpl receiptService = new ReceiptBeanDAOImpl();
        List<ReceiptBean> receipts = null;
        try {
            receipts = receiptService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("receipts", receipts);
    }

    public static void nextStatus(HttpServletRequest req) {
        long id = Integer.parseInt(req.getParameter("id"));
        Receipt receipt = null;
        ReceiptDAOImplementation service = new ReceiptDAOImplementation();
        try {
            receipt = service.getByReceiptId(id);
            logger.debug(receipt);
        } catch (SQLException e) {
            logger.error(e);
        }
        if (receipt != null) {
            long receiptStatus = receipt.getReceiptStatusId();
            logger.debug(receiptStatus);
            if (receiptStatus < CLOSED_STATUS - 1) {
                logger.debug("CLOSED_STATUS - 1");
                receipt.setReceiptStatusId(++receiptStatus);
            } else if (receiptStatus == CLOSED_STATUS - 1) {
                logger.debug("CLOSED_STATUS");
                DeliveryOrderDAOImplementation orderService = new DeliveryOrderDAOImplementation();
                DeliveryOrder deliveryOrder = null;
                try {
                    deliveryOrder = orderService.getById(receipt.getDeliveryOrderId());
                } catch (SQLException e) {
                    logger.error(e);
                }
                if (deliveryOrder != null) {
                    deliveryOrder.setReceivingDate(new Timestamp(System.currentTimeMillis()));
                }
                logger.debug(deliveryOrder);
                orderService.update(deliveryOrder);

                receipt.setReceiptStatusId(++receiptStatus);
            }
            logger.debug(receipt);
            service.update(receipt);
        }
    }

    public static void approve(HttpServletRequest req, long status) {
        long id = Integer.parseInt(req.getParameter("id"));
        Receipt receipt = null;
        User user = (User) req.getSession().getAttribute("user");
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
            receipt.setManagerId(user.getId());
            logger.debug(receipt);
            service.update(receipt);
        }
    }

    public static void changeStatus(HttpServletRequest req, long status, ReceiptDAO service) {
        long id = Long.parseLong(req.getParameter("id"));
        Receipt receipt = null;
        logger.debug(id);
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
}
