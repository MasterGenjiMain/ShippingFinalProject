package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.ApproveService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/manager/approving/next-status/*")
public class ApprovingNextStatusServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ApprovingNextStatusServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ApproveService.nextStatus(req);
        req.getRequestDispatcher("/manager/approving").forward(req, resp);
    }
}
