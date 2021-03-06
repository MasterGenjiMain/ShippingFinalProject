package com.my.deliverysystem.servlet;

import com.my.deliverysystem.dao.implementation.UserDAOImplementation;
import com.my.deliverysystem.service.LoginService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered login doGet --> redirecting...");
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Entered doPost");
        boolean successStatus;
        LoginService loginService = new LoginService(new UserDAOImplementation());
        successStatus = loginService.userLogin(req);

        if (successStatus) {
            resp.sendRedirect("index.jsp");
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }

    }

}
