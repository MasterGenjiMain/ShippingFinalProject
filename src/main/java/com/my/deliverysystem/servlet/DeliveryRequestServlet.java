package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.DeliveryRequestService;
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

        DeliveryRequestService.getAllLocationsToRequest(req);
        DeliveryRequestService.getAllDeliveryTypesToRequest(req);
        DeliveryRequestService.getAllTariffsToRequest(req);

        req.getRequestDispatcher("/deliveryRequest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doPost " + getClass().getName());

        logger.debug(req.getParameter("cargoName"));
        logger.debug(req.getParameter("cargoDescription"));
        logger.debug(req.getParameter("locationFrom"));
        logger.debug(req.getParameter("locationTo"));
        logger.debug(req.getParameter("address"));
        logger.debug(req.getParameter("deliveryType"));
        logger.debug(req.getParameter("weight"));
        logger.debug(req.getParameter("volume"));
        logger.debug(req.getParameter("distance"));
        logger.debug(req.getParameter("tariff"));

        DeliveryRequestService.createNewDeliveryRequest(req);
        resp.sendRedirect("/FinalProject_war_exploded/user/delivery-request");
    }
}
