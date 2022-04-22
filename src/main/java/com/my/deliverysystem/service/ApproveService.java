package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.DeliveryOrderDAO;
import com.my.deliverysystem.dao.daoInterface.ReceiptDAO;
import com.my.deliverysystem.dao.daoInterface.beanDAO.ReceiptBeanDAO;
import com.my.deliverysystem.db.entity.DeliveryOrder;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * A class works with ApproveServlet and realizes all it's main functions
 * -showReceipts
 * -nextStatus
 * -approve
 * -changeStatus
 */
public class ApproveService {
    final Logger logger = Logger.getLogger(ApproveService.class.getName());
    private static final int CLOSED_STATUS = 7;

    private ReceiptBeanDAO receiptService;
    private ReceiptDAO service;
    private DeliveryOrderDAO orderService;

    public ApproveService(ReceiptBeanDAO receiptService, ReceiptDAO service, DeliveryOrderDAO orderService) {
        this.receiptService = receiptService;
        this.service = service;
        this.orderService = orderService;
    }

    protected ApproveService(ReceiptBeanDAO receiptService) {
        this.receiptService = receiptService;
    }

    protected ApproveService(ReceiptDAO service) {
        this.service = service;
    }

    public void showReceipts(HttpServletRequest req) {
        List<ReceiptBean> receipts = null;
        try {
            receipts = receiptService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("receipts", receipts);
    }

    public void nextStatus(HttpServletRequest req) {
        long id = Integer.parseInt(req.getParameter("id"));
        Receipt receipt = null;
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

    public void approve(HttpServletRequest req, long status) {
        long id = Integer.parseInt(req.getParameter("id"));
        Receipt receipt = null;
        User user = (User) req.getSession().getAttribute("user");
        logger.debug(id);
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

    public void changeStatus(HttpServletRequest req, long status) {
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
