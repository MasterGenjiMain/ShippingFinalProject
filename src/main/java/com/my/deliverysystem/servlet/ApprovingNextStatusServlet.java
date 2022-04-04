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

@WebServlet("/manager/approving/next-status/*")
public class ApprovingNextStatusServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ApprovingNextStatusServlet.class);
    private static final int CANCEL_STATUS = 8;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Integer.parseInt(req.getParameter("id"));
        Receipt receipt = null;
        logger.debug(id);
        ReceiptDAOImplementation service = new ReceiptDAOImplementation();
        try {
            receipt = service.getByReceiptId(id);
            logger.debug(receipt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (receipt != null) {
            long receiptStatus = receipt.getReceiptStatusId();
            logger.debug(receiptStatus);
            if (receiptStatus < CANCEL_STATUS - 1) {
                receipt.setReceiptStatusId(++receiptStatus);
            }
            logger.debug(receipt);
            service.update(receipt);
        }

        req.getRequestDispatcher("/manager/approving").forward(req, resp);
    }
}
