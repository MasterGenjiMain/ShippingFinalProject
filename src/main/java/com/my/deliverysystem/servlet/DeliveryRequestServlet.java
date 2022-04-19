package com.my.deliverysystem.servlet;

import com.my.deliverysystem.dao.implementation.*;
import com.my.deliverysystem.service.DeliveryRequestService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/user/delivery-request")
public class DeliveryRequestServlet extends HttpServlet {
    Logger logger = Logger.getLogger(DeliveryRequestServlet.class);
    DeliveryRequestService deliveryRequestService = new DeliveryRequestService(new LocationDAOImplementation(), new DeliveryTypeDAOImplementation(), new TariffDAOImplementation(), new DeliveryOrderDAOImplementation(), new ReceiptDAOImplementation());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet " + getClass().getName());

        deliveryRequestService.getAllLocationsToRequest(req);
        deliveryRequestService.getAllDeliveryTypesToRequest(req);
        deliveryRequestService.getAllTariffsToRequest(req);

        req.getRequestDispatcher("/deliveryRequest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Entered doPost " + getClass().getName());
        String appPath = File.separator + req.getContextPath()
                .replace("/", "") + File.separator;

        deliveryRequestService.createNewDeliveryRequest(req);
        resp.sendRedirect( appPath + "user/delivery-request");
    }
}
