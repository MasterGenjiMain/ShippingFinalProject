package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.UserDAOImplementation;
import com.my.deliverysystem.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrationService {

    final static Logger logger = Logger.getLogger(RegistrationService.class.getName());

    public static void userRegistration(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.debug("Entered userRegistration() RegistrationService");
        String login = req.getParameter("username");
        String email = req.getParameter("email").toLowerCase();
        String password = req.getParameter("password");

        logger.debug("login --> " + login);
        logger.debug("email --> " + email);
        logger.debug("password --> " + password);

        User newUser = new User(login, email, password);
        UserDAOImplementation service = new UserDAOImplementation();

        if (login.equals("") || email.equals("") || password.equals("")) {
            if (login.equals("")) {
                req.setAttribute("message", "Login can't be empty!");
            } else if (email.equals("")) {
                req.setAttribute("message", "Email can't be empty!");
            } else {
                req.setAttribute("message", "Password can't be empty!");
            }
            logger.debug("Bad input!");
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return;
        }

        logger.debug(newUser);
        try {
            service.add(newUser);
        } catch (SQLException e) {
            req.setAttribute("message", "Username or email is already taken");
            logger.error("Exception at registration: " + e);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return;
        }
        logger.info("Registration successful!");
        resp.sendRedirect("login.jsp");
    }

}
