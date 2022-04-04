package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.AccountService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/account/pay/*")
public class AccountPayServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(AccountPayServlet.class);
    private static final long PAYED_STATUS = 3;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet" + getClass().getName());

        AccountService.changeStatus(req, PAYED_STATUS);

        req.getRequestDispatcher("/user/account").forward(req, resp);
    }



}
