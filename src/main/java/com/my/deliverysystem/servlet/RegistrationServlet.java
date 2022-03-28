package com.my.deliverysystem.servlet;

import com.my.deliverysystem.service.RegistrationService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered registration doGet --> redirecting...");
        resp.sendRedirect("registration.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered registration doPost");

        RegistrationService.userRegistration(req,resp);
    }
}
