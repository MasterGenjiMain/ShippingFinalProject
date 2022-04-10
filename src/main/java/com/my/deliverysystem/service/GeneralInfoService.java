package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.LanguageDAOImplementation;
import com.my.deliverysystem.dao.implementation.TariffDAOImplementation;
import com.my.deliverysystem.dao.implementation.beanImpl.LocationBeanDAOImpl;
import com.my.deliverysystem.db.entity.Language;
import com.my.deliverysystem.db.entity.Tariff;
import com.my.deliverysystem.db.entity.bean.LocationBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class GeneralInfoService {

    private static final Logger logger = Logger.getLogger(GeneralInfoService.class);

    public static void showGeneralInfo(HttpServletRequest req) {
        createLocationsTable(req);
        createTariffsTable(req);
    }

    private static void createTariffsTable(HttpServletRequest req) {
        logger.debug("Entered createTariffsTable() !!!!!!!!!!!!!!!!!!!!!!!!!!!" + GeneralInfoService.class.getName());

        GeneralInfoService generalInfoService = new GeneralInfoService();
        Language currentLanguage = generalInfoService.getCurrentLanguage(req);

        TariffDAOImplementation tariffService = new TariffDAOImplementation();
        List<Tariff> tariffs = null;
        try {
            tariffs = tariffService.getByLanguageId(currentLanguage != null ? currentLanguage.getId() : 1);
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("tariffs", tariffs);
    }

    public Language getCurrentLanguage(HttpServletRequest req) {
        Language currentLanguage = null;
        LanguageDAOImplementation languageService = new LanguageDAOImplementation();
        String languageName = String.valueOf(req.getSession().getAttribute("language"));
        logger.debug(languageName);
        try {
            currentLanguage = languageService.getByName(languageName);
            logger.debug(currentLanguage);
        } catch (SQLException e) {
            logger.debug(e);
        }
        return currentLanguage;
    }

    private static void createLocationsTable(HttpServletRequest req) {
        logger.debug("Entered createLocationsTable()" + GeneralInfoService.class.getName());
        LocationBeanDAOImpl locationService = new LocationBeanDAOImpl();
        List<LocationBean> locations = null;
        try {
            locations = locationService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("locations", locations);
    }
}
