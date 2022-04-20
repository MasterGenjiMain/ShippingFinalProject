package com.my.deliverysystem.service;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * A class works with LogoutServlet and realizes all it's main functions
 * -logoutUser
 */

public class LogoutService {
    final static Logger logger = Logger.getLogger(LogoutService.class.getName());

    public static void logoutUser(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        session.invalidate();
        logger.debug("Logout successful!");
    }
}
