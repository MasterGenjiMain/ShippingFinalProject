package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.TariffDAO;
import com.my.deliverysystem.db.entity.Tariff;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryCalculatorServiceTest {

    @Test
    void calculateShouldNotReturnMinimalPrice() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TariffDAO tariffService = mock(TariffDAO.class);

        int height = 50, width = 50, length = 50;
        double distance = 100, weight = 10;
        String tariffName = "Min50PriceTariff";

        when(req.getParameter("height")).thenReturn(String.valueOf(height));
        when(req.getParameter("width")).thenReturn(String.valueOf(width));
        when(req.getParameter("length")).thenReturn(String.valueOf(length));
        when(req.getParameter("distance")).thenReturn(String.valueOf(distance));
        when(req.getParameter("weight")).thenReturn(String.valueOf(weight));
        when(req.getParameter("tariffName")).thenReturn(tariffName);
        when(req.getSession()).thenReturn(session);

        Tariff tariff = new Tariff("Name", 50, null, 1);
        when(tariffService.getByName(tariffName)).thenReturn(tariff);

        DeliveryCalculatorService deliveryCalculatorService = new DeliveryCalculatorService(tariffService);
        deliveryCalculatorService.calculate(req);

        verify(session).setAttribute("price", "250,00");
    }

    @Test
    void calculateShouldReturn275WhenDistance500() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TariffDAO tariffService = mock(TariffDAO.class);

        int height = 50, width = 50, length = 50;
        double distance = 500, weight = 10;
        String tariffName = "Min50PriceTariff";

        when(req.getParameter("height")).thenReturn(String.valueOf(height));
        when(req.getParameter("width")).thenReturn(String.valueOf(width));
        when(req.getParameter("length")).thenReturn(String.valueOf(length));
        when(req.getParameter("distance")).thenReturn(String.valueOf(distance));
        when(req.getParameter("weight")).thenReturn(String.valueOf(weight));
        when(req.getParameter("tariffName")).thenReturn(tariffName);
        when(req.getSession()).thenReturn(session);

        Tariff tariff = new Tariff("Name", 50, null, 1);
        when(tariffService.getByName(tariffName)).thenReturn(tariff);

        DeliveryCalculatorService deliveryCalculatorService = new DeliveryCalculatorService(tariffService);
        deliveryCalculatorService.calculate(req);

        verify(session).setAttribute("price", "275,00");
    }

    @Test
    void calculateShouldReturn325WhenDistance1000() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TariffDAO tariffService = mock(TariffDAO.class);

        int height = 50, width = 50, length = 50;
        double distance = 1000, weight = 10;
        String tariffName = "Min50PriceTariff";

        when(req.getParameter("height")).thenReturn(String.valueOf(height));
        when(req.getParameter("width")).thenReturn(String.valueOf(width));
        when(req.getParameter("length")).thenReturn(String.valueOf(length));
        when(req.getParameter("distance")).thenReturn(String.valueOf(distance));
        when(req.getParameter("weight")).thenReturn(String.valueOf(weight));
        when(req.getParameter("tariffName")).thenReturn(tariffName);
        when(req.getSession()).thenReturn(session);

        Tariff tariff = new Tariff("Name", 50, null, 1);
        when(tariffService.getByName(tariffName)).thenReturn(tariff);

        DeliveryCalculatorService deliveryCalculatorService = new DeliveryCalculatorService(tariffService);
        deliveryCalculatorService.calculate(req);

        verify(session).setAttribute("price", "325,00");
    }

    @Test
    void calculateShouldReturn400WhenDistance1250() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TariffDAO tariffService = mock(TariffDAO.class);

        int height = 50, width = 50, length = 50;
        double distance = 1250, weight = 10;
        String tariffName = "Min50PriceTariff";

        when(req.getParameter("height")).thenReturn(String.valueOf(height));
        when(req.getParameter("width")).thenReturn(String.valueOf(width));
        when(req.getParameter("length")).thenReturn(String.valueOf(length));
        when(req.getParameter("distance")).thenReturn(String.valueOf(distance));
        when(req.getParameter("weight")).thenReturn(String.valueOf(weight));
        when(req.getParameter("tariffName")).thenReturn(tariffName);
        when(req.getSession()).thenReturn(session);

        Tariff tariff = new Tariff("Name", 50, null, 1);
        when(tariffService.getByName(tariffName)).thenReturn(tariff);

        DeliveryCalculatorService deliveryCalculatorService = new DeliveryCalculatorService(tariffService);
        deliveryCalculatorService.calculate(req);

        verify(session).setAttribute("price", "375,00");
    }

    @Test
    void calculateShouldReturnExceptionWhenInputIncorrect() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TariffDAO tariffService = mock(TariffDAO.class);

        int height = 50, width = 50, length = 50;
        double distance = 1250;

        when(req.getParameter("height")).thenReturn(String.valueOf(height));
        when(req.getParameter("width")).thenReturn(String.valueOf(width));
        when(req.getParameter("length")).thenReturn(String.valueOf(length));
        when(req.getParameter("distance")).thenReturn(String.valueOf(distance));
        when(req.getParameter("weight")).thenThrow(NumberFormatException.class);
        when(req.getSession()).thenReturn(session);

        DeliveryCalculatorService deliveryCalculatorService = new DeliveryCalculatorService(tariffService);
        deliveryCalculatorService.calculate(req);

        assertThrows(NumberFormatException.class, () -> req.getParameter("weight"));
    }

}