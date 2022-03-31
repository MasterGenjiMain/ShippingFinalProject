package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.LocationDAOImplementation;
import com.my.deliverysystem.dao.implementation.TariffDAOImplementation;
import com.my.deliverysystem.db.entity.Location;
import com.my.deliverysystem.db.entity.Tariff;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class LocationTableService {

    private static final Logger logger = Logger.getLogger(LocationTableService.class);

    public static void showGeneralInfo(HttpServletRequest req) {
        createLocationsTable(req);
        createTariffsTable(req);
    }

    private static void createTariffsTable(HttpServletRequest req) {
        logger.debug("Entered createTariffsTable()" + LocationTableService.class.getName());
        TariffDAOImplementation tariffService = new TariffDAOImplementation();
        List<Tariff> tariffs = null;
        try {
            tariffs = tariffService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("tariffs", tariffs);
    }

    private static void createLocationsTable(HttpServletRequest req) {
        logger.debug("Entered createLocationsTable()" + LocationTableService.class.getName());
        LocationDAOImplementation locationService = new LocationDAOImplementation();
        List<Location> locations = null;
        try {
            locations = locationService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("locations", locations);
    }
}
