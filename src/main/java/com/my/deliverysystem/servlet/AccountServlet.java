package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.AccountService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/account")
public class AccountServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(AccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet()" + AccountServlet.class.getName());

        AccountService.showReceipts(req, resp);
    }
}