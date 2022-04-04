package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.ReceiptDAOImplementation;
import com.my.deliverysystem.dao.implementation.beanImpl.ReceiptBeanDAOImpl;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class ApproveService {
    final static Logger logger = Logger.getLogger(ApproveService.class.getName());
    private static final int CANCEL_STATUS = 8;

    public static void showReceipts(HttpServletRequest req) {
        ReceiptBeanDAOImpl receiptService = new ReceiptBeanDAOImpl();
        List<ReceiptBean> receipts = null;
        try {
            receipts = receiptService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("receipts", receipts);
    }

    public static void nextStatus(HttpServletRequest req) {
        long id = Integer.parseInt(req.getParameter("id"));
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
            long receiptStatus = receipt.getReceiptStatusId();
            logger.debug(receiptStatus);
            if (receiptStatus < CANCEL_STATUS - 1) {
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
}
