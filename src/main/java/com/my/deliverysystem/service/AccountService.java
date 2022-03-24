package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.ReceiptDAOImplementation;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.servlet.AccountServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private static final Logger logger = Logger.getLogger(AccountService.class);

    public static void showReceipts(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        ReceiptDAOImplementation service = new ReceiptDAOImplementation();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<Receipt> receipts = new ArrayList<>();
        try{
            receipts = service.getByUserId(user.getId());
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("receipts", receipts);

        req.getRequestDispatcher("/account.jsp").forward(req, resp);
    }
}
