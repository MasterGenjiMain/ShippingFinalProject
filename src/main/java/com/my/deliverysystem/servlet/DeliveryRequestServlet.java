package com.my.deliverysystem.servlet;

import com.my.deliverysystem.dao.implementation.*;
import com.my.deliverysystem.db.entity.*;
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
import java.util.List;

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
            logger.debug(receipt);
            logger.debug("receipt added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //--------------------------------delivery order-----------------------------------------//
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        DeliveryOrderDAOImplementation deliveryOrderService = new DeliveryOrderDAOImplementation();
        long locationFromId = 0;
        long locationToId = 0;

        LocationDAOImplementation locationService = new LocationDAOImplementation();

        List<Location> locations = null;
        try {
            locations = locationService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Location location : locations) {
            if (location.getLocationName().equals(req.getParameter("locationFrom"))) {
                locationFromId = location.getId();
            }
        }
        deliveryOrder.setLocationFromID(locationFromId); //1

        for (Location location : locations) {
            if (location.getLocationName().equals(req.getParameter("locationTo"))) {
                locationToId = location.getId();
            }
        }
        deliveryOrder.setLocationToId(locationToId); //2

        deliveryOrder.setAddress(req.getParameter("address")); //3

        DeliveryTypeDAOImplementation deliveryTypeService = new DeliveryTypeDAOImplementation();
        List<DeliveryType> deliveryTypes = null;
        long deliveryTypeId = 0;
        try {
            deliveryTypes = deliveryTypeService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (DeliveryType deliveryType : deliveryTypes) {
            if (deliveryType.getTypeName().equals(req.getParameter("deliveryType"))) {
                deliveryTypeId = deliveryType.getId();
            }
        }
        deliveryOrder.setDeliveryTypeId(deliveryTypeId); //4

        deliveryOrder.setWeight(Double.parseDouble(req.getParameter("weight"))); //5
        deliveryOrder.setVolume(Double.parseDouble(req.getParameter("volume"))); //6
        deliveryOrder.setReceivingDate(null); //7

        TariffDAOImplementation tariffService = new TariffDAOImplementation();
        List<Tariff> tariffs = null;
        long tariffId = 0;
        try {
            tariffs = tariffService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Tariff tariff : tariffs) {
            if (tariff.getTariffName().equals(req.getParameter("tariff"))) {
                tariffId = tariff.getId();
            }
        }
        deliveryOrder.setTariffId(tariffId); //8
        deliveryOrder.setReceiptId(receipt.getId()); //9

        try {
            logger.debug(deliveryOrder);
            deliveryOrderService.add(deliveryOrder);
            logger.debug("Delivery order added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //--------------------------cargo------------------------------

        CargoDAOImplementation cargoService = new CargoDAOImplementation();
        Cargo cargo = new Cargo();
        cargo.setName(req.getParameter("cargoName"));
        cargo.setDescription(req.getParameter("cargoDescription"));
        cargo.setDeliveryOrderId(deliveryOrder.getId());
        try {
            cargoService.add(cargo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("message", "Created successfully!");
        resp.sendRedirect("/FinalProject_war_exploded/user/delivery-request");
    }
}
