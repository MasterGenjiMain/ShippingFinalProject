package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.LocationDAOImplementation;
import com.my.deliverysystem.dao.implementation.TariffDAOImplementation;
import com.my.deliverysystem.db.entity.Location;
import com.my.deliverysystem.db.entity.Tariff;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LocationTableService {

    private static final Logger logger = Logger.getLogger(LocationTableService.class);

    public static void showGeneralInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        createLocationsTable(req);
        createTariffsTable(req);

        req.getRequestDispatcher("/generalInfo.jsp").forward(req, resp);
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
