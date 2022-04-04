package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.ApproveService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/manager/approving/approve/*")
public class ApprovingApproveServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(ApprovingApproveServlet.class);
    private static final long APPROVED_STATUS = 2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ApproveService.approve(req, APPROVED_STATUS);
        req.getRequestDispatcher("/manager/approving").forward(req, resp);
    }
}
