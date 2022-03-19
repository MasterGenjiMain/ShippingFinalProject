package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.UserDAOImplementation;
import com.my.deliverysystem.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class loginService {
    final static Logger logger = Logger.getLogger(loginService.class.getName());

    public static void userLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Entered userLogin() loginService");
        String login = req.getParameter("login");
        String userPassword = req.getParameter("password");

        logger.debug("Entered login ---> " + login);
        logger.debug("Entered password ---> " + userPassword);

        User userFromDb = null;
        UserDAOImplementation service = new UserDAOImplementation();
        try {
            userFromDb = service.getByUsername(login);
        } catch (SQLException e) {
            logger.error(e);
        }
        if (userFromDb != null) {
            if (userFromDb.getUsername().equals(login) && userFromDb.getPassword().equals(userPassword)) {
                logger.info("Logging successful!");
                req.getSession().setAttribute("user", userFromDb);
                resp.sendRedirect("index.jsp");
            } else {
                logger.info("Incorrect password");
                req.getSession().setAttribute("message", "Incorrect password!");
                resp.sendRedirect(req.getRequestURI());
                return;
            }
        } else {
            logger.info("Login failed. User not found");
            req.getSession().setAttribute("message", "User not found");
            resp.sendRedirect(req.getRequestURI());
            return;
        }
        req.getSession().removeAttribute("message");
    }
}
