package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.ReceiptDAOImplementation;
import com.my.deliverysystem.dao.implementation.beanImpl.ReceiptBeanDAOImpl;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            receipt.setReceiptStatusId(status);
            logger.debug(receipt);
            service.update(receipt);
        }
    }
}
