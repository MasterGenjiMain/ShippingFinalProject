package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.beanImpl.ReceiptBeanDAOImpl;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class ApproveService {
    final static Logger logger = Logger.getLogger(ApproveService.class.getName());

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

    }
}
