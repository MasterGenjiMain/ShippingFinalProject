package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.LanguageDAO;
import com.my.deliverysystem.dao.daoInterface.TariffDAO;
import com.my.deliverysystem.dao.daoInterface.beanDAO.LocationBeanDAO;
import com.my.deliverysystem.db.entity.Language;
import com.my.deliverysystem.db.entity.Tariff;
import com.my.deliverysystem.db.entity.bean.LocationBean;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class GeneralInfoServiceTest {

    @Test
    void showGeneralInfoShouldReturnCorrectList() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        LanguageDAO languageService = mock(LanguageDAO.class);
        TariffDAO tariffService = mock(TariffDAO.class);
        LocationBeanDAO locationService = mock(LocationBeanDAO.class);
        Language language = new Language("new");
        language.setId(1);

        List<Tariff> tariffs = List.of(
                new Tariff("name1", 10, null, 1),
                new Tariff("name2", 20, null, 1));

        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("language")).thenReturn(language.getLanguageName());
        when(languageService.getByName(anyString())).thenReturn(language);
        when(tariffService.getByLanguageId(language.getId())).thenReturn(tariffs);

        List<LocationBean>  locations = List.of(
                new LocationBean(1, "locName", "cityName", "status"),
                new LocationBean(2, "locName2", "cityName2", "status2")
        );

        when(locationService.getAll()).thenReturn(locations);

        GeneralInfoService generalInfoService = new GeneralInfoService(tariffService, languageService, locationService);
        generalInfoService.showGeneralInfo(req);

        verify(req).setAttribute("tariffs", tariffs);
        verify(req).setAttribute("locations", locations);
    }

    @Test
    void createTariffsTableShouldReturnSQLExceptionIfGetByLanguageIdReturnsIt() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        LanguageDAO languageService = mock(LanguageDAO.class);
        TariffDAO tariffService = mock(TariffDAO.class);
        LocationBeanDAO locationService = mock(LocationBeanDAO.class);
        Language language = new Language("new");
        language.setId(1);

        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("language")).thenReturn(language.getLanguageName());
        when(languageService.getByName(anyString())).thenReturn(language);
        when(tariffService.getByLanguageId(language.getId())).thenThrow(SQLException.class);

        GeneralInfoService generalInfoService = new GeneralInfoService(tariffService, languageService, locationService);
        generalInfoService.showGeneralInfo(req);

        assertThrows(SQLException.class, () ->tariffService.getByLanguageId(language.getId()));
    }

    @Test
    void showGeneralInfoShouldReturnSQLExceptionIfLocationServiceReturnsIt() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        TariffDAO tariffService = mock(TariffDAO.class);
        HttpSession session = mock(HttpSession.class);
        LanguageDAO languageService = mock(LanguageDAO.class);
        LocationBeanDAO locationService = mock(LocationBeanDAO.class);
        Language language = new Language("new");
        language.setId(1);

        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("language")).thenReturn(language.getLanguageName());
        when(languageService.getByName(anyString())).thenThrow(SQLException.class);

        GeneralInfoService generalInfoService = new GeneralInfoService(tariffService, languageService, locationService);
        generalInfoService.showGeneralInfo(req);

        assertThrows(SQLException.class, () -> languageService.getByName(anyString()));
    }

    @Test
    void createLocationsTableShouldReturnSQLExceptionIfLocationServiceReturnsIt() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        LanguageDAO languageService = mock(LanguageDAO.class);
        TariffDAO tariffService = mock(TariffDAO.class);
        LocationBeanDAO locationService = mock(LocationBeanDAO.class);
        Language language = new Language("new");
        language.setId(1);

        List<Tariff> tariffs = List.of(
                new Tariff("name1", 10, null, 1),
                new Tariff("name2", 20, null, 1));

        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("language")).thenReturn(language.getLanguageName());
        when(languageService.getByName(anyString())).thenReturn(language);
        when(tariffService.getByLanguageId(language.getId())).thenReturn(tariffs);

        when(locationService.getAll()).thenThrow(SQLException.class);

        GeneralInfoService generalInfoService = new GeneralInfoService(tariffService, languageService, locationService);
        generalInfoService.showGeneralInfo(req);

        assertThrows(SQLException.class, locationService::getAll);
    }

}