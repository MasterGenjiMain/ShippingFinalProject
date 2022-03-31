package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.UserDAOImplementation;
import com.my.deliverysystem.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginService {
    final static Logger logger = Logger.getLogger(LoginService.class.getName());

    public static void userLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.debug("Entered userLogin() loginService");
        String login = req.getParameter("login");
        String userPassword = req.getParameter("password");

        logger.debug("Entered login ---> " + login);
        logger.debug("Entered password ---> " + userPassword);

        LogoutService.logoutUser(req);

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
                return;
            } else {
                logger.info("Incorrect password");
                req.setAttribute("message", "Incorrect password!");
            }
        } else {
            logger.info("Login failed. User not found");
            req.setAttribute("message", "User not found");
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
