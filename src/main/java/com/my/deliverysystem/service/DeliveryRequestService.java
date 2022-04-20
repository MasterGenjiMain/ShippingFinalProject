package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.*;
import com.my.deliverysystem.dao.implementation.*;
import com.my.deliverysystem.dao.implementation.beanImpl.LocationBeanDAOImpl;
import com.my.deliverysystem.db.entity.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class works with DeliveryRequestServlet and realizes all it's main functions
 * -showDeliveryOrders
 * -showReportPDF
 */

public class DeliveryRequestService {
    final Logger logger = Logger.getLogger(DeliveryRequestService.class.getName());

    private LocationDAO locationService;
    private DeliveryTypeDAO deliveryTypeService;
    private TariffDAO tariffService;
    private DeliveryOrderDAO deliveryOrderService;
    private ReceiptDAO receiptService;
    private LanguageDAO languageService;

    public DeliveryRequestService(LocationDAO locationService, DeliveryTypeDAO deliveryTypeService,
                                  TariffDAO tariffService, DeliveryOrderDAO deliveryOrderService,
                                  ReceiptDAO receiptService, LanguageDAO languageService) {
        this.locationService = locationService;
        this.deliveryTypeService = deliveryTypeService;
        this.tariffService = tariffService;
        this.deliveryOrderService = deliveryOrderService;
        this.receiptService = receiptService;
        this.languageService = languageService;
    }

    public DeliveryRequestService(LocationDAO locationService) {
        this.locationService = locationService;
    }

    public DeliveryRequestService(DeliveryTypeDAO deliveryTypeService, LanguageDAO languageService) {
        this.deliveryTypeService = deliveryTypeService;
        this.languageService = languageService;
    }

    public DeliveryRequestService(TariffDAO tariffService, LanguageDAO languageService) {
        this.tariffService = tariffService;
        this.languageService = languageService;
    }

    public void getAllLocationsToRequest(HttpServletRequest req){
        logger.debug("Entered getAllLocationsToRequest-> " + DeliveryRequestService.class.getName());
        List<Location> locations = new ArrayList<>();
        try {
            locations = locationService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("locations", locations);
    }

    public void getAllDeliveryTypesToRequest(HttpServletRequest req){
        logger.debug("Entered getAllDeliveryTypesToRequest-> " + DeliveryRequestService.class.getName());

        Language currentLanguage = getCurrentLanguage(req);
        List<DeliveryType> deliveryTypes = new ArrayList<>();
        try {
            deliveryTypes = deliveryTypeService.getByLanguageId(currentLanguage != null ? currentLanguage.getId() : 1);
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("deliveryTypes", deliveryTypes);
    }

    public void getAllTariffsToRequest(HttpServletRequest req){
        Language currentLanguage = getCurrentLanguage(req);

        List<Tariff> tariffs = new ArrayList<>();
        try {
            tariffs = tariffService.getByLanguageId(currentLanguage != null ? currentLanguage.getId() : 1);
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("tariffs", tariffs);
    }

    public void createNewDeliveryRequest(HttpServletRequest req) {

        logger.debug(req.getParameter("cargoName"));
        logger.debug(req.getParameter("cargoDescription"));
        logger.debug(req.getParameter("locationFrom"));
        logger.debug(req.getParameter("locationTo"));
        logger.debug(req.getParameter("address"));
        logger.debug(req.getParameter("deliveryType"));
        logger.debug(req.getParameter("weight"));
        logger.debug(req.getParameter("height"));
        logger.debug(req.getParameter("width"));
        logger.debug(req.getParameter("length"));
        logger.debug(req.getParameter("distance"));
        logger.debug(req.getParameter("tariff"));

        //--------------------------------delivery order-----------------------------------------//
        DeliveryOrder deliveryOrder = createDeliveryOrder(req);
        //--------------------------------receipt-----------------------------------------//
        createReceipt(deliveryOrder, req);

        logger.debug("Delivery Request created successfully!");
        req.getSession().setAttribute("message", "Created successfully!");
    }


    private DeliveryOrder createDeliveryOrder(HttpServletRequest req) {
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        long locationFromId = 0;
        long locationToId = 0;

        List<Location> locations = null;
        try {
            locations = locationService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }

        if (locations != null) {
            for (Location location : locations) {
                if (location.getLocationName().equals(req.getParameter("locationFrom"))) {
                    locationFromId = location.getId();
                }
            }
        }
        deliveryOrder.setLocationFromId(locationFromId); //1

        if (locations != null) {
            for (Location location : locations) {
                if (location.getLocationName().equals(req.getParameter("locationTo"))) {
                    locationToId = location.getId();
                }
            }
        }
        deliveryOrder.setLocationToId(locationToId); //2

        deliveryOrder.setCargoName(req.getParameter("cargoName"));
        deliveryOrder.setCargoDescription(req.getParameter("cargoDescription"));
        deliveryOrder.setAddress(req.getParameter("address"));

        List<DeliveryType> deliveryTypes = null;
        long deliveryTypeId = 0;
        try {
            deliveryTypes = deliveryTypeService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        if (deliveryTypes != null) {
            for (DeliveryType deliveryType : deliveryTypes) {
                if (deliveryType.getTypeName().equals(req.getParameter("deliveryType"))) {
                    deliveryTypeId = deliveryType.getId();
                }
            }
        }
        deliveryOrder.setDeliveryTypeId(deliveryTypeId); //6

        deliveryOrder.setWeight(Double.parseDouble(req.getParameter("weight"))); //7

        double height = Double.parseDouble(req.getParameter("height"));
        double width = Double.parseDouble(req.getParameter("width"));
        double length = Double.parseDouble(req.getParameter("length"));
        deliveryOrder.setVolume(height * width * length); //8
        logger.debug("Delivery order volume -> " + (height * width * length));
        deliveryOrder.setReceivingDate(null); //9

        List<Tariff> tariffs = null;
        long tariffId = 0;
        try {
            tariffs = tariffService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        if (tariffs != null) {
            for (Tariff tariff : tariffs) {
                if (tariff.getTariffName().equals(req.getParameter("tariff"))) {
                    tariffId = tariff.getId();
                }
            }
        }
        deliveryOrder.setTariffId(tariffId); //10

        try {
            logger.debug(deliveryOrder + "to add !");
            deliveryOrderService.add(deliveryOrder);
            logger.debug("Delivery order added!");
        } catch (SQLException e) {
            logger.error(e);
        }
        return deliveryOrder;
    }

    private void createReceipt(DeliveryOrder deliveryOrder ,HttpServletRequest req) {
        int DEFAULT_MANAGER = 1;
        double weight = Double.parseDouble(req.getParameter("weight"));
        double height = Double.parseDouble(req.getParameter("height"));
        double width = Double.parseDouble(req.getParameter("width"));
        double length = Double.parseDouble(req.getParameter("length"));
        double distance = Double.parseDouble(req.getParameter("distance"));
        double volume = height * width * length;
        String tariffName = req.getParameter("tariff");

        DeliveryCalculatorService deliveryCalculatorService = new DeliveryCalculatorService(new TariffDAOImplementation(), new LanguageDAOImplementation());
        double price = deliveryCalculatorService.getPrice(distance, weight, volume, tariffName);

        User user = (User) req.getSession().getAttribute("user");

        Receipt receipt = new Receipt();
        receipt.setUserId(user.getId());
        receipt.setManagerId(DEFAULT_MANAGER);
        receipt.setPrice(price);
        receipt.setReceiptStatusId(1);
        receipt.setDeliveryOrderId(deliveryOrder.getId());
        try {
            receiptService.add(receipt);
            logger.debug("receipt added!");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private Language getCurrentLanguage(HttpServletRequest req) {
        Language currentLanguage = null;
        String languageName = String.valueOf(req.getSession().getAttribute("language"));
        logger.debug(languageName);
        try {
            currentLanguage = languageService.getByName(languageName);
            logger.debug(currentLanguage);
        } catch (SQLException e) {
            logger.debug(e);
        }
        return currentLanguage;
    }
}
