package com.my.deliverysystem.service;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TariffCalculatorService {
    private static final Logger logger = Logger.getLogger(TariffCalculatorService.class);

    private static final int MINIMAL_PRICE = 50;
    private static final int VOLUME_DIVIDER = 1000;
    private static final int VOLUME_PRICE = 2;
    private static final int WEIGHT_PRICE = 5;

    public static boolean calculate(HttpServletRequest req) throws IOException, ServletException {
        logger.debug("Entered calculate() TariffService");
        int height, width, length;
        double distance ,weight;

        double volume, price;
        String finalPrice;

        try {
            height = Integer.parseInt(req.getParameter("height"));
            width = Integer.parseInt(req.getParameter("width"));
            length = Integer.parseInt(req.getParameter("length"));

            distance = Double.parseDouble(req.getParameter("distance"));
            weight = Double.parseDouble(req.getParameter("weight"));
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

        volume = height * width * length;
        logger.debug("volume -> " + volume);

        price = getPrice(distance, weight, volume);

        logger.debug("before min -> " + price);
        if (price < MINIMAL_PRICE) {
            price = MINIMAL_PRICE;
        }

        logger.debug("after min -> " + price);

        finalPrice = String.format("%.2f", price);
        req.getSession().setAttribute("price", finalPrice);

        return true;
    }

    public static double getPrice(double distance, double weight, double volume) {
        double volumePrice;
        double weightPrice;
        double DISTANCE_MULTIPLAYER;
        double price;
        DISTANCE_MULTIPLAYER = getDistanceMultiplayer(distance);

        logger.debug("DISTANCE_MULTIPLAYER -> " + DISTANCE_MULTIPLAYER);

        volumePrice = ((volume / VOLUME_DIVIDER) * VOLUME_PRICE) * DISTANCE_MULTIPLAYER;
        weightPrice = (weight * WEIGHT_PRICE) * DISTANCE_MULTIPLAYER;
        logger.debug("volumePrice -> " + volumePrice);
        logger.debug("weightPrice -> " + weightPrice);

        price = Math.max(volumePrice, weightPrice);
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
