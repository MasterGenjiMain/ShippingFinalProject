package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.DeliveryCalculatorService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/delivery-calculator")
public class DeliveryCalculatorServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(DeliveryCalculatorServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet() TariffsServlet");
        DeliveryCalculatorService.getAllTariffsToRequest(req);
        req.getRequestDispatcher("/deliveryCalculator.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doPost() TariffsServlet");
        String appPath = File.separator + req.getContextPath()
                .replace("/", "") + File.separator;
        boolean successStatus;
        successStatus = DeliveryCalculatorService.calculate(req);

        if (successStatus) {
            resp.sendRedirect( appPath + "tariffs");
        } else {
            req.getRequestDispatcher("/deliveryCalculator.jsp").forward(req, resp);
        }
    }
}
