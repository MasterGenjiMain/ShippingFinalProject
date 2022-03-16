package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.UserDAOImplementation;
import com.my.deliverysystem.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrationService {

    final static Logger logger = Logger.getLogger(RegistrationService.class.getName());

    public static void userRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Entered userRegistration() RegistrationService");
        String login = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        logger.debug("login --> " + login);
        logger.debug("email --> " + email);
        logger.debug("password --> " + password);

        User newUser = new User(login, email, password);

        UserDAOImplementation service = new UserDAOImplementation();
        try {
            service.add(newUser);
        } catch (SQLException e) {
            req.getSession().setAttribute("message", "Username or email already taken");
            logger.error("Exception at registration: " + e);
            resp.sendRedirect("registration.html");
            return;
        }
        logger.info("Registration successful!");
        resp.sendRedirect("login.html");
    }

}
