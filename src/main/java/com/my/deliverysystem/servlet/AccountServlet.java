package com.my.deliverysystem.servlet;

import com.my.deliverysystem.dao.implementation.ReceiptDAOImplementation;
import com.my.deliverysystem.dao.implementation.beanImpl.DeliveryOrderBeanDAOImpl;
import com.my.deliverysystem.dao.implementation.beanImpl.ReceiptBeanDAOImpl;
import com.my.deliverysystem.service.AccountService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/account/*")
public class  AccountServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(AccountServlet.class);
    private static final long CANCELED_STATUS = 8;
    private static final long PAYED_STATUS = 3;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet()" + AccountServlet.class.getName());
        AccountService accountService = new AccountService(new ReceiptBeanDAOImpl(), new ReceiptDAOImplementation(), new DeliveryOrderBeanDAOImpl());

        String action = req.getPathInfo();
        logger.debug(action);
        if (action != null) {
            switch (action) {
                case "/cancel":
                    accountService.changeStatus(req, CANCELED_STATUS);
                    resp.sendRedirect("user/account");
                    break;
                case "/pay":
                    accountService.changeStatus(req, PAYED_STATUS);
                    resp.sendRedirect("user/account");
                    break;
                case "/receipt-download":
                    accountService.showReceiptPDF(req, resp);
                    break;
                default:
                    accountService.showReceipts(req);
                    req.getRequestDispatcher("/account.jsp").forward(req, resp);
            }
        }
    }
}
