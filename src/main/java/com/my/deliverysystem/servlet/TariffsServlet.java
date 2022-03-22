package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.TariffCalculatorService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tariffs")
public class TariffsServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(TariffsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet() TariffsServlet");
        req.getRequestDispatcher("/tariff.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TariffCalculatorService.calculate(req, resp);
    }
}