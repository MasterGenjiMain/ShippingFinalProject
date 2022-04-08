package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.TariffDAOImplementation;
import com.my.deliverysystem.db.entity.Tariff;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class TariffCalculatorService {
    private static final Logger logger = Logger.getLogger(TariffCalculatorService.class);

    private static final int VOLUME_DIVIDER = 1000;
    private static final int VOLUME_PRICE = 2;
    private static final int WEIGHT_PRICE = 5;

    public static boolean calculate(HttpServletRequest req) {
        logger.debug("Entered calculate() TariffService");
        int height, width, length;
        double distance ,weight;
        String tariffName;

        double volume, price;
        String finalPrice;

        try {
            height = Integer.parseInt(req.getParameter("height"));
            width = Integer.parseInt(req.getParameter("width"));
            length = Integer.parseInt(req.getParameter("length"));

            distance = Double.parseDouble(req.getParameter("distance"));
            weight = Double.parseDouble(req.getParameter("weight"));
            tariffName = req.getParameter("tariff");
        } catch (NumberFormatException e) {
            logger.error(e);
            req.getSession().removeAttribute("price");
            req.setAttribute("message", "All fields must be filled");

            return false;
        }

        logger.debug("height -> " + height);
        logger.debug("width -> " + width);
        logger.debug("length -> " + length);
        logger.debug("distance -> " + distance);
        logger.debug("weight -> " + weight);
        logger.debug("tariff -> " + tariffName);

        volume = height * width * length;
        logger.debug("volume -> " + volume);

        price = getPrice(distance, weight, volume, tariffName);



        finalPrice = String.format("%.2f", price);
        req.getSession().setAttribute("price", finalPrice);

        return true;
    }

    public static double getPrice(double distance, double weight, double volume, String tariffName) {
        double volumePrice;
        double weightPrice;
        double DISTANCE_MULTIPLAYER;
        double price;
        double MINIMAL_PRICE = 0;
        DISTANCE_MULTIPLAYER = getDistanceMultiplayer(distance);

        logger.debug("DISTANCE_MULTIPLAYER -> " + DISTANCE_MULTIPLAYER);

        volumePrice = ((volume / VOLUME_DIVIDER) * VOLUME_PRICE) * DISTANCE_MULTIPLAYER;
        weightPrice = (weight * WEIGHT_PRICE) * DISTANCE_MULTIPLAYER;
        logger.debug("volumePrice -> " + volumePrice);
        logger.debug("weightPrice -> " + weightPrice);

        price = Math.max(volumePrice, weightPrice);

        //--------------
        Tariff tariff = null;
        TariffDAOImplementation tariffService = new TariffDAOImplementation();
        try {
            tariff = tariffService.getByName(tariffName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (tariff != null) {
            MINIMAL_PRICE = tariff.getTariffPrice();
        }
        //-------------

        logger.debug("before min -> " + price);
        if (price < MINIMAL_PRICE) {
            price = MINIMAL_PRICE;
        }

        logger.debug("after min -> " + price);

        return price;
    }

    private static double getDistanceMultiplayer(double distance) {
        double DISTANCE_MULTIPLAYER;
        if (distance <= 100) {
            DISTANCE_MULTIPLAYER = 1;
        } else if (distance <= 500) {
            DISTANCE_MULTIPLAYER = 1.1;
        } else if (distance <= 1000) {
            DISTANCE_MULTIPLAYER = 1.3;
        } else {
            DISTANCE_MULTIPLAYER = 1.5;
        }
        return DISTANCE_MULTIPLAYER;
    }
}
