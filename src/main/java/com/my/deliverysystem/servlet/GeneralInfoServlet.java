package com.my.deliverysystem.servlet;

import com.my.deliverysystem.dao.implementation.LanguageDAOImplementation;
import com.my.deliverysystem.dao.implementation.TariffDAOImplementation;
import com.my.deliverysystem.dao.implementation.beanImpl.LocationBeanDAOImpl;
import com.my.deliverysystem.service.GeneralInfoService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/general-info")
public class GeneralInfoServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(GeneralInfoServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet DeliveryRotesServlet");
        GeneralInfoService generalInfoService = new GeneralInfoService(new TariffDAOImplementation(), new LanguageDAOImplementation(), new LocationBeanDAOImpl());
        generalInfoService.showGeneralInfo(req);

        req.getRequestDispatcher("/generalInfo.jsp").forward(req, resp);
    }

}
