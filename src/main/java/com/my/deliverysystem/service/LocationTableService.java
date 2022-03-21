package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.LocationDAOImplementation;
import com.my.deliverysystem.db.entity.Location;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LocationTableService {

    private static final Logger logger = Logger.getLogger(LocationTableService.class);

    public static void showTable(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.debug("Entered showTable() LocationTableService");
        LocationDAOImplementation service = new LocationDAOImplementation();
        List<Location> locations = null;
        try {
            locations = service.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("locations", locations);
        req.getRequestDispatcher("/location.jsp").forward(req, resp);
    }
}
