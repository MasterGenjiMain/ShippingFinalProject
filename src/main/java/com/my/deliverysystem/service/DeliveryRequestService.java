package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.DeliveryTypeDAOImplementation;
import com.my.deliverysystem.dao.implementation.LocationDAOImplementation;
import com.my.deliverysystem.dao.implementation.TariffDAOImplementation;
import com.my.deliverysystem.db.entity.DeliveryType;
import com.my.deliverysystem.db.entity.Location;
import com.my.deliverysystem.db.entity.Tariff;
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
}
