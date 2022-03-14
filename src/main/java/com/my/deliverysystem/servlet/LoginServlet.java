package com.my.deliverysystem.servlet;

import com.my.deliverysystem.dao.implementation.UserDAOImplementation;
import com.my.deliverysystem.db.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doGet --> redirecting...");
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doPost");
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
                req.getSession().setAttribute("user", userFromDb);
                logger.info("Logging successful!");
                return;
            }
        }
        logger.info("Login failed");
        resp.sendRedirect("login.html");
    }
}
