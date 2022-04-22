package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.UserDAO;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @Test
    void registrationTest(){
        HttpServletRequest req = mock(HttpServletRequest.class);
        UserDAO service = mock(UserDAO.class);

        when(req.getParameter("username")).thenReturn("Username");
        when(req.getParameter("email")).thenReturn("email");
        when(req.getParameter("password")).thenReturn("pass");
        when(req.getParameter("repeat-password")).thenReturn("pass");

        RegistrationService registrationService = new RegistrationService(service);
        assertTrue(registrationService.userRegistration(req));
    }

    @Test
    void registrationTestShouldReturnValidMessageIfLoginIsEmpty(){
        HttpServletRequest req = mock(HttpServletRequest.class);
        UserDAO service = mock(UserDAO.class);

        when(req.getParameter("username")).thenReturn("");
        when(req.getParameter("email")).thenReturn("email");
        when(req.getParameter("password")).thenReturn("pass");
        when(req.getParameter("repeat-password")).thenReturn("pass");

        RegistrationService registrationService = new RegistrationService(service);
        registrationService.userRegistration(req);

        verify(req).setAttribute("message", "Login can't be empty!");
    }

    @Test
    void registrationTestShouldReturnValidMessageIfEmailIsEmpty(){
        HttpServletRequest req = mock(HttpServletRequest.class);
        UserDAO service = mock(UserDAO.class);

        when(req.getParameter("username")).thenReturn("Username");
        when(req.getParameter("email")).thenReturn("");
        when(req.getParameter("password")).thenReturn("pass");
        when(req.getParameter("repeat-password")).thenReturn("pass");

        RegistrationService registrationService = new RegistrationService(service);
        registrationService.userRegistration(req);

        verify(req).setAttribute("message", "Email can't be empty!");
    }

    @Test
    void registrationTestShouldReturnValidMessageIfPasswordIsEmpty(){
        HttpServletRequest req = mock(HttpServletRequest.class);
        UserDAO service = mock(UserDAO.class);

        when(req.getParameter("username")).thenReturn("Username");
        when(req.getParameter("email")).thenReturn("email");
        when(req.getParameter("password")).thenReturn("");
        when(req.getParameter("repeat-password")).thenReturn("pass");

        RegistrationService registrationService = new RegistrationService(service);
        registrationService.userRegistration(req);

        verify(req).setAttribute("message", "Password can't be empty!");
    }

    @Test
    void registrationTestShouldReturnValidMessageIfRePasswordIsEmpty(){
        HttpServletRequest req = mock(HttpServletRequest.class);
        UserDAO service = mock(UserDAO.class);

        when(req.getParameter("username")).thenReturn("Username");
        when(req.getParameter("email")).thenReturn("email");
        when(req.getParameter("password")).thenReturn("pass");
        when(req.getParameter("repeat-password")).thenReturn("");

        RegistrationService registrationService = new RegistrationService(service);
        registrationService.userRegistration(req);

        verify(req).setAttribute("message", "Password confirmation can't be empty!");
    }

    @Test
    void registrationTestShouldReturnValidMessageIfPasswordsDidNotMatch(){
        HttpServletRequest req = mock(HttpServletRequest.class);
        UserDAO service = mock(UserDAO.class);

        when(req.getParameter("username")).thenReturn("Username");
        when(req.getParameter("email")).thenReturn("email");
        when(req.getParameter("password")).thenReturn("pass");
        when(req.getParameter("repeat-password")).thenReturn("pa");

        RegistrationService registrationService = new RegistrationService(service);
        registrationService.userRegistration(req);

        verify(req).setAttribute("message", "Passwords didn't match");
    }

}