package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.*;
import com.my.deliverysystem.db.entity.*;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryRequestServiceTest {

    @Test
    void getAllLocationsToRequestShouldReturnCorrectList() throws SQLException {
        LocationDAO locationService = mock(LocationDAO.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        List<Location> locations = List.of(
                new Location("first",1,1),
                new Location("second",2,0),
                new Location("third",2,1));

        when(locationService.getAll()).thenReturn(locations);

        DeliveryRequestService deliveryRequestService = new DeliveryRequestService(locationService);
        deliveryRequestService.getAllLocationsToRequest(req);
        verify(req).setAttribute("locations", locations);
    }

    @Test
    void getAllLocationsToRequestGetAllShouldDropSQLException() throws SQLException {
        LocationDAO locationService = mock(LocationDAO.class);
        HttpServletRequest req = mock(HttpServletRequest.class);

        when(locationService.getAll()).thenThrow(SQLException.class);

        DeliveryRequestService deliveryRequestService = new DeliveryRequestService(locationService);
        deliveryRequestService.getAllLocationsToRequest(req);
        assertThrows(SQLException.class, locationService::getAll);
    }

    @Test
    void getAllDeliveryTypesToRequestShouldReturnCorrectList() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        LanguageDAO languageService = mock(LanguageDAO.class);
        DeliveryTypeDAO deliveryTypeService = mock(DeliveryTypeDAO.class);
        Language language = new Language("new");
        language.setId(1);
        List<DeliveryType> deliveryTypes = List.of(
                new DeliveryType(1,"fist", 1),
                new DeliveryType(1,"second", 1));
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("language")).thenReturn(language.getLanguageName());
        when(languageService.getByName(language.getLanguageName())).thenReturn(language);
        when(deliveryTypeService.getByLanguageId(language.getId())).thenReturn(deliveryTypes);

        DeliveryRequestService deliveryRequestService = new DeliveryRequestService(deliveryTypeService, languageService);
        deliveryRequestService.getAllDeliveryTypesToRequest(req);
        verify(req).setAttribute("deliveryTypes", deliveryTypes);

    }

    @Test
    void getByNameShouldReturnSQLExceptionIfLanguageNotFound() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        LanguageDAO languageService = mock(LanguageDAO.class);
        DeliveryTypeDAO deliveryTypeService = mock(DeliveryTypeDAO.class);
        String languageName = "someName";
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("language")).thenReturn(languageName);
        when(languageService.getByName(languageName)).thenThrow(SQLException.class);

        DeliveryRequestService deliveryRequestService = new DeliveryRequestService(deliveryTypeService, languageService);
        deliveryRequestService.getAllDeliveryTypesToRequest(req);
        assertThrows(SQLException.class, ()->languageService.getByName(languageName));
    }

    @Test
    void locationServiceGetAllShouldReturnSQLExceptionIfSomethingWentWrong() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        LanguageDAO languageService = mock(LanguageDAO.class);
        DeliveryTypeDAO deliveryTypeService = mock(DeliveryTypeDAO.class);
        Language language = new Language("new");
        language.setId(1);
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("language")).thenReturn(language.getLanguageName());
        when(languageService.getByName(language.getLanguageName())).thenReturn(language);
        when(deliveryTypeService.getByLanguageId(language.getId())).thenThrow(SQLException.class);

        DeliveryRequestService deliveryRequestService = new DeliveryRequestService(deliveryTypeService, languageService);
        deliveryRequestService.getAllDeliveryTypesToRequest(req);
        assertThrows(SQLException.class, () -> deliveryTypeService.getByLanguageId(language.getId()));
    }

    @Test
    void getAllTariffsToRequestContainsCorrectValue() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        LanguageDAO languageService = mock(LanguageDAO.class);
        TariffDAO tariffService = mock(TariffDAO.class);
        Language language = new Language("new");
        language.setId(1);
        List<Tariff> tariffList = List.of(
                new Tariff("Name1",10,null, 1),
                new Tariff("Name3",20,null, 1));

        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("language")).thenReturn(language.getLanguageName());
        when(languageService.getByName(language.getLanguageName())).thenReturn(language);
        when(tariffService.getByLanguageId(language.getId())).thenReturn(tariffList);

        DeliveryRequestService deliveryRequestService = new DeliveryRequestService(tariffService, languageService);
        deliveryRequestService.getAllTariffsToRequest(req);

        verify(req).setAttribute("tariffs",tariffList);
    }

    @Test
    void getAllTariffsShouldReturnSQLExceptionIfLanguageNotFoundById() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        LanguageDAO languageService = mock(LanguageDAO.class);
        TariffDAO tariffService = mock(TariffDAO.class);
        Language language = new Language("new");
        language.setId(1);

        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("language")).thenReturn(language.getLanguageName());
        when(languageService.getByName(language.getLanguageName())).thenReturn(language);
        when(tariffService.getByLanguageId(language.getId())).thenThrow(SQLException.class);

        DeliveryRequestService deliveryRequestService = new DeliveryRequestService(tariffService, languageService);
        deliveryRequestService.getAllTariffsToRequest(req);

        assertThrows(SQLException.class, () -> tariffService.getByLanguageId(language.getId()));
    }

    @Test
    void createNewDeliveryRequestShouldReturnCreatedSuccessfullySessionMessage() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        TariffDAO tariffService = mock(TariffDAO.class);
        LanguageDAO languageService = mock(LanguageDAO.class);
        LocationDAO locationService = mock(LocationDAO.class);
        DeliveryTypeDAO deliveryTypeService = mock(DeliveryTypeDAO.class);
        DeliveryOrderDAO deliveryOrderService = mock(DeliveryOrderDAO.class);
        ReceiptDAO receiptService = mock(ReceiptDAO.class);

        List<Location> locations = List.of(
                new Location("firstLocation", 1, 1),
                new Location("SecondLocation", 2, 1));

        List<DeliveryType> deliveryTypes = List.of(
                new DeliveryType(1,"type1",1),
                new DeliveryType(1,"type2",1));

        List<Tariff> tariffs = List.of(
                new Tariff("tariff1Name", 10, null, 1),
                new Tariff("tariff2Name", 20, null, 2));

        DeliveryOrder deliveryOrder = new DeliveryOrder(1,2,
                "cargoName", "cargoDescription", "address", 1, 10, 10, null, 1);

        User user = new User(1L, "name", "mail", "pass");

        when(locationService.getAll()).thenReturn(locations);
        when(req.getParameter("locationFrom")).thenReturn("firstLocation");
        when(req.getParameter("locationTo")).thenReturn("SecondLocation");
        when(req.getParameter("cargoName")).thenReturn(deliveryOrder.getCargoName());
        when(req.getParameter("cargoDescription")).thenReturn(deliveryOrder.getCargoDescription());
        when(req.getParameter("address")).thenReturn(deliveryOrder.getAddress());
        when(deliveryTypeService.getAll()).thenReturn(deliveryTypes);
        when(req.getParameter("deliveryType")).thenReturn("type1");
        when(req.getParameter("weight")).thenReturn(String.valueOf(10));
        when(req.getParameter("height")).thenReturn(String.valueOf(10));
        when(req.getParameter("width")).thenReturn(String.valueOf(10));
        when(req.getParameter("length")).thenReturn(String.valueOf(10));
        when(tariffService.getAll()).thenReturn(tariffs);
        when(req.getParameter("tariff")).thenReturn("tariff1Name");

        when(req.getParameter("distance")).thenReturn(String.valueOf(1000));
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(user);


        DeliveryRequestService deliveryRequestService = new DeliveryRequestService(locationService, deliveryTypeService,
                tariffService, deliveryOrderService, receiptService, languageService);
        deliveryRequestService.createNewDeliveryRequest(req);

        verify(session).setAttribute("message", "Created successfully!");
    }

}