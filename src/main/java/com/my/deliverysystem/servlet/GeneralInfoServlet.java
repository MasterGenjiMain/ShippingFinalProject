package com.my.deliverysystem.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.deliverysystem.service.LocationTableService.showGeneralInfo;


@WebServlet("/general-info")
public class GeneralInfoServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(GeneralInfoServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet DeliveryRotesServlet");

        showGeneralInfo(req,resp);
    }

}
