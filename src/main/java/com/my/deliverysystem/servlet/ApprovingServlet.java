package com.my.deliverysystem.servlet;

import com.my.deliverysystem.dao.implementation.ReceiptDAOImplementation;
import com.my.deliverysystem.db.entity.Receipt;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/manager/approving")
public class ApprovingServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(ApprovingServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet " + getClass().getName());

        ReceiptDAOImplementation receiptService = new ReceiptDAOImplementation();
        List<Receipt> receipts = null;
        try {
            receipts = receiptService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("receipts", receipts);

        req.getRequestDispatcher("/approving.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doPost " + getClass().getName());
        super.doPost(req, resp);
    }
}
