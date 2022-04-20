package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.DeliveryTypeDAO;
import com.my.deliverysystem.dao.daoInterface.LanguageDAO;
import com.my.deliverysystem.dao.daoInterface.LocationDAO;
import com.my.deliverysystem.dao.daoInterface.TariffDAO;
import com.my.deliverysystem.db.entity.DeliveryType;
import com.my.deliverysystem.db.entity.Language;
import com.my.deliverysystem.db.entity.Location;
import com.my.deliverysystem.db.entity.Tariff;
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
                new DeliveryType("fist", 1),
                new DeliveryType("second", 1));
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

}