package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/manager/approving/cancel/*")
public class ApprovingCancelServlet extends HttpServlet {
    private static final long CANCELED_STATUS = 8;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountService.changeStatus(req, CANCELED_STATUS);
        req.getRequestDispatcher("/manager/approving").forward(req, resp);
    }
}
