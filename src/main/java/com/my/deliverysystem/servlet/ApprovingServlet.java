package com.my.deliverysystem.servlet;

import com.my.deliverysystem.dao.implementation.ReceiptDAOImplementation;
import com.my.deliverysystem.service.AccountService;
import com.my.deliverysystem.service.ApproveService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/manager/approving/*")
public class ApprovingServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(ApprovingServlet.class);
    private static final long CANCELED_STATUS = 8;
    private static final long APPROVED_STATUS = 2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet " + getClass().getName());

        String action = req.getPathInfo();
        logger.debug(action);
        switch (action){
            case "/next-status":
                ApproveService.nextStatus(req);
                resp.sendRedirect("manager/approving");
                break;
            case "/cancel":
                ApproveService.changeStatus(req, CANCELED_STATUS, new ReceiptDAOImplementation());
                resp.sendRedirect("manager/approving");
                break;
            case "/approve":
                ApproveService.approve(req, APPROVED_STATUS);
                resp.sendRedirect("manager/approving");
                break;
            default:
                ApproveService.showReceipts(req);
                req.getRequestDispatcher("/approving.jsp").forward(req, resp);
        }
    }
}
