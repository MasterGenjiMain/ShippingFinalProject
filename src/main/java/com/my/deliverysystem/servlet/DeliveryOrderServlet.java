package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.DeliveryOrderService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/manager/delivery-order")
public class DeliveryOrderServlet extends HttpServlet {
    Logger logger = Logger.getLogger(DeliveryOrderServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet() " + getClass().getName());
        DeliveryOrderService.showDeliveryOrders(req);
        req.getRequestDispatcher("/deliveryOrder.jsp").forward(req, resp);
    }
}
