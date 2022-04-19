package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.UserDAOImplementation;
import com.my.deliverysystem.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class RegistrationService {

    final Logger logger = Logger.getLogger(RegistrationService.class.getName());

    UserDAOImplementation service;

    public RegistrationService(UserDAOImplementation service) {
        this.service = service;
    }

    public boolean userRegistration(HttpServletRequest req) {
        logger.debug("Entered userRegistration() RegistrationService");
        String login = req.getParameter("username");
        String email = req.getParameter("email").toLowerCase();
        String password = req.getParameter("password");
        String rePassword = req.getParameter("repeat-password");

        logger.debug("login --> " + login);
        logger.debug("email --> " + email);
        logger.debug("password --> " + password);

        User newUser = new User(login, email, password);

        if (login.equals("") || email.equals("") || password.equals("") || rePassword.equals("")) {
            if (login.equals("")) {
                req.setAttribute("message", "Login can't be empty!");
            } else if (email.equals("")) {
                req.setAttribute("message", "Email can't be empty!");
            } else if (password.equals("")){
                req.setAttribute("message", "Password can't be empty!");
            } else {
                req.setAttribute("message", "Password confirmation can't be empty!");
            }
            logger.debug("Bad input!");
            return false;
        }

        if (!password.equals(rePassword)) {
            req.setAttribute("message", "Passwords didn't match");
            return false;
        }

        logger.debug(newUser);
        try {
            service.add(newUser);
        } catch (SQLException e) {
            req.setAttribute("message", "Username or email is already taken");
            logger.error("Exception at registration: " + e);
            return false;
        }
        logger.info("Registration successful!");
        return true;
    }

}
