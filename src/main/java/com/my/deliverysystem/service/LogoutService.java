package com.my.deliverysystem.service;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutService {
    final static Logger logger = Logger.getLogger(LogoutService.class.getName());

    public static void logoutUser(HttpServletRequest req){
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        session.invalidate();
        logger.debug("Logout successful!");
    }
}
