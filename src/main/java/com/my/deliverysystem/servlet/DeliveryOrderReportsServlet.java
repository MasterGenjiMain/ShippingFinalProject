package com.my.deliverysystem.servlet;

import com.my.deliverysystem.dao.daoInterface.beanDAO.DeliveryOrderBeanDAO;
import com.my.deliverysystem.dao.daoInterface.beanDAO.ReceiptBeanDAO;
import com.my.deliverysystem.dao.implementation.beanImpl.DeliveryOrderBeanDAOImpl;
import com.my.deliverysystem.dao.implementation.beanImpl.ReceiptBeanDAOImpl;
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
        DeliveryOrderReportsService deliveryOrderReportsService = new DeliveryOrderReportsService(new DeliveryOrderBeanDAOImpl(), new ReceiptBeanDAOImpl());

        String action = req.getPathInfo();
        logger.debug(action);
        switch (action){
            case "/report-download":
                deliveryOrderReportsService.showReportPDF(req, resp);
                break;
            default:
                deliveryOrderReportsService.showDeliveryOrders(req);
                req.getRequestDispatcher("/deliveryOrderReports.jsp").forward(req, resp);
        }
    }
}
