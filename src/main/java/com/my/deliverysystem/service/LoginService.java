package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.UserDAOImplementation;
import com.my.deliverysystem.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * A class works with LoginServlet and realizes all it's main functions
 * -userLogin
 */

public class LoginService {
    final Logger logger = Logger.getLogger(LoginService.class.getName());

    private final UserDAOImplementation service;

    public LoginService(UserDAOImplementation service) {
        this.service = service;
    }

    public boolean userLogin(HttpServletRequest req) {
        logger.debug("Entered userLogin() loginService");
        String login = req.getParameter("login");
        String userPassword = req.getParameter("password");

        logger.debug("Entered login ---> " + login);
        logger.debug("Entered password ---> " + userPassword);

        LogoutService.logoutUser(req);

        User userFromDb = null;
        try {
            userFromDb = service.getByUsername(login);
        } catch (SQLException e) {
            logger.error(e);
        }
        if (userFromDb != null) {
            if (userFromDb.getUsername().equals(login) && userFromDb.getPassword().equals(userPassword)) {
                logger.info("Logging successful!");
                req.getSession().setAttribute("user", userFromDb);

                return true;
            } else {
                logger.info("Incorrect password");
                req.setAttribute("message", "Incorrect password!");
            }
        } else {
            logger.info("Login failed. User not found");
            req.setAttribute("message", "User not found");
        }
        return false;
    }
}
