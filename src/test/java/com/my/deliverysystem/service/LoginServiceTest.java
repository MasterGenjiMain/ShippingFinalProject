package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.UserDAO;
import com.my.deliverysystem.db.entity.User;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

class LoginServiceTest {

    @Test
    void testLoginService() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        UserDAO service = mock(UserDAO.class);
        HttpSession session = mock(HttpSession.class);

        User user = new User(1, "Username", "mail", null);
        user.setPassword("5b722b307fce6c944905d132691d5e4a2214b7fe92b738920eb3fce3a90420a19511c3010a0e7712b054daef5b57bad59ecbd93b3280f210578f547f4aed4d25");

        when(req.getParameter("login")).thenReturn("Username");
        when(req.getParameter("password")).thenReturn("pass");
        when(service.getByUsername("Username")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        LoginService loginService = new LoginService(service);
        loginService.userLogin(req);
        verify(session).setAttribute("user", user);
    }

    @Test
    void loginServiceShouldReturnValidMessageIfPasswordIncorrect() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        UserDAO service = mock(UserDAO.class);
        HttpSession session = mock(HttpSession.class);

        User user = new User(1, "Username", "mail", "pass");

        when(req.getParameter("login")).thenReturn("Username");
        when(req.getParameter("password")).thenReturn("incPass");
        when(service.getByUsername("Username")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        LoginService loginService = new LoginService(service);
        loginService.userLogin(req);
        verify(req).setAttribute("message", "Incorrect password!");
    }

    @Test
    void loginServiceShouldReturnValidMessageUserNotFound() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        UserDAO service = mock(UserDAO.class);
        HttpSession session = mock(HttpSession.class);

        User user = new User(1, "Username", "mail", "pass");

        when(req.getParameter("login")).thenReturn("incUsername");
        when(req.getParameter("password")).thenReturn("incPass");
        when(service.getByUsername("Username")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        LoginService loginService = new LoginService(service);
        loginService.userLogin(req);
        verify(req).setAttribute("message", "User not found");
    }

}