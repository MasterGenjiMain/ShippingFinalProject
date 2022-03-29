package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.*;
import com.my.deliverysystem.db.entity.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRequestService {
    final static Logger logger = Logger.getLogger(DeliveryRequestService.class.getName());

    public static void getAllLocationsToRequest(HttpServletRequest req){
        logger.debug("Entered getAllLocationsToRequest-> " + DeliveryRequestService.class.getName());
        LocationDAOImplementation locationService = new LocationDAOImplementation();
        List<Location> locations = new ArrayList<>();
        try {
            locations = locationService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("locations", locations);
    }

    public static void getAllDeliveryTypesToRequest(HttpServletRequest req){
        logger.debug("Entered getAllDeliveryTypesToRequest-> " + DeliveryRequestService.class.getName());
        DeliveryTypeDAOImplementation deliveryTypeService = new DeliveryTypeDAOImplementation();
        List<DeliveryType> deliveryTypes = new ArrayList<>();
        try {
            deliveryTypes = deliveryTypeService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("deliveryTypes", deliveryTypes);
    }

    public static void getAllTariffsToRequest(HttpServletRequest req){
        TariffDAOImplementation tariffService = new TariffDAOImplementation();
        List<Tariff> tariffs = new ArrayList<>();
        try {
            tariffs = tariffService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("tariffs", tariffs);
    }

    public static void createNewDeliveryRequest(HttpServletRequest req) {
        //--------------------------------receipt-----------------------------------------//
        Receipt receipt = createReceipt(req);


        //--------------------------------delivery order-----------------------------------------//
        DeliveryOrder deliveryOrder = createDeliveryOrder(req, receipt);

        //--------------------------cargo------------------------------
        createCargo(req, deliveryOrder);

        logger.debug("Delivery Request created successfully!");
        req.getSession().setAttribute("message", "Created successfully!");
    }

    private static void createCargo(HttpServletRequest req, DeliveryOrder deliveryOrder) {
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
    }

    private static DeliveryOrder createDeliveryOrder(HttpServletRequest req, Receipt receipt) {
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

        if (locations != null) {
            for (Location location : locations) {
                if (location.getLocationName().equals(req.getParameter("locationFrom"))) {
                    locationFromId = location.getId();
                }
            }
        }
        deliveryOrder.setLocationFromID(locationFromId); //1

        if (locations != null) {
            for (Location location : locations) {
                if (location.getLocationName().equals(req.getParameter("locationTo"))) {
                    locationToId = location.getId();
                }
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
        if (deliveryTypes != null) {
            for (DeliveryType deliveryType : deliveryTypes) {
                if (deliveryType.getTypeName().equals(req.getParameter("deliveryType"))) {
                    deliveryTypeId = deliveryType.getId();
                }
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
        if (tariffs != null) {
            for (Tariff tariff : tariffs) {
                if (tariff.getTariffName().equals(req.getParameter("tariff"))) {
                    tariffId = tariff.getId();
                }
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
        return deliveryOrder;
    }

    private static Receipt createReceipt(HttpServletRequest req) {
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
        return receipt;
    }
}
