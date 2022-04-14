package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.DeliveryOrderReportsService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/manager/delivery-order-reports/*")
public class DeliveryOrderReportsServlet extends HttpServlet {
    Logger logger = Logger.getLogger(DeliveryOrderReportsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet() " + getClass().getName());
        String action = req.getPathInfo();
        logger.debug(action);
        switch (action){
            case "/report-download":
                DeliveryOrderReportsService.showReportPDF(req, resp);
                break;
            default:
                DeliveryOrderReportsService.showDeliveryOrders(req);
                req.getRequestDispatcher("/deliveryOrderReports.jsp").forward(req, resp);
        }
    }
}
