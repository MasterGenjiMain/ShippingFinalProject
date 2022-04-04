package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.AccountService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/account/cancel/*")
public class AccountCancelServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(AccountCancelServlet.class);
    private static final long CANCELED_STATUS = 8;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet" + getClass().getName());

        AccountService.changeStatus(req, CANCELED_STATUS);

        req.getRequestDispatcher("/user/account").forward(req, resp);
    }
}
