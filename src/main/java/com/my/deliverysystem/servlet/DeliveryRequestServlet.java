package com.my.deliverysystem.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/delivery-request")
public class DeliveryRequestServlet extends HttpServlet {
    Logger logger = Logger.getLogger(DeliveryRequestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet " + getClass().getName());
        req.getRequestDispatcher("/deliveryRequest.jsp").forward(req,resp);
//        resp.sendRedirect("/deliveryRequest.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
