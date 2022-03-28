package com.my.deliverysystem.servlet;

import com.my.deliverysystem.dao.implementation.ReceiptDAOImplementation;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.service.DeliveryRequestService;
import com.my.deliverysystem.service.TariffCalculatorService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delivery-request")
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
        logger.debug(req.getParameter("tariff"));

        //--------------------------------receipt-----------------------------------------//
        double TEMPORARY_DISTANCE = 450;
        int DEFAULT_MANAGER = 1;
        double weight = Double.parseDouble(req.getParameter("weight"));
        double volume = Double.parseDouble(req.getParameter("volume"));
        double price = TariffCalculatorService.getPrice(TEMPORARY_DISTANCE, weight, volume);

        User user = (User) req.getSession().getAttribute("user");

        Receipt receipt = new Receipt();
        receipt.setUserId(user.getId());
        receipt.setManagerId(DEFAULT_MANAGER);
        receipt.setPrice(price);
        receipt.setReceiptStatusId(1);

        ReceiptDAOImplementation receiptService = new ReceiptDAOImplementation();
        try {
            logger.debug(receipt);
            receiptService.add(receipt);
            logger.debug("receipt added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }



        //--------------------------------delivery order-----------------------------------------//

        super.doPost(req, resp);
    }
}
