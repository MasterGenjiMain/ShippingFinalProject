package com.my.deliverysystem.service;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TariffCalcutatorService {
    private static final Logger logger = Logger.getLogger(TariffCalcutatorService.class);

    private static final int MINIMAL_PRICE = 75;
    private static final int VOLUME_DIVIDER = 1000;
    private static final int BASIC_PRICE = 10;

    public static void calculate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.debug("Entered calculate() TariffService");
        int height;
        int width;
        int length;
        double distance;
        double weight;

        double volume;
        double price;

        double DISTANCE_MULTIPLAYER;
        double WEIGHT_MULTIPLAYER;
        try {
            height = Integer.parseInt(req.getParameter("height"));
            width = Integer.parseInt(req.getParameter("width"));
            length = Integer.parseInt(req.getParameter("length"));

            distance = Double.parseDouble(req.getParameter("distance"));
            weight = Double.parseDouble(req.getParameter("weight"));
        } catch (NumberFormatException e) {
            logger.error(e);
            req.setAttribute("message", "All fields must be filled");
            req.getRequestDispatcher("/tariff.jsp").forward(req, resp);
            return;
        }

        logger.debug("height -> " + height);
        logger.debug("width -> " + width);
        logger.debug("length -> " + length);
        logger.debug("distance -> " + distance);
        logger.debug("weight -> " + weight);

        volume = height * width * length;
        logger.debug("volume -> " + volume);

        if (distance <= 100) {
            DISTANCE_MULTIPLAYER = 1;
        } else if (distance <= 500) {
            DISTANCE_MULTIPLAYER = 1.2;
        } else if (distance <= 1000) {
            DISTANCE_MULTIPLAYER = 1.5;
        } else {
            DISTANCE_MULTIPLAYER = 2;
        }

        if (weight <= 5) {
            WEIGHT_MULTIPLAYER = 1;
        } else if (weight <= 20) {
            WEIGHT_MULTIPLAYER = 1.2;
        } else if (weight <= 100) {
            WEIGHT_MULTIPLAYER = 1.5;
        } else {
            WEIGHT_MULTIPLAYER = 2;
        }

        logger.debug("DISTANCE_MULTIPLAYER -> " + DISTANCE_MULTIPLAYER);
        logger.debug("WEIGHT_MULTIPLAYER -> " + WEIGHT_MULTIPLAYER);

        price = (MINIMAL_PRICE + ((volume / VOLUME_DIVIDER) * BASIC_PRICE)) * DISTANCE_MULTIPLAYER * WEIGHT_MULTIPLAYER;
        logger.debug(price);

        req.setAttribute("price", price);

        req.getRequestDispatcher("/tariff.jsp").forward(req, resp);
    }
}
