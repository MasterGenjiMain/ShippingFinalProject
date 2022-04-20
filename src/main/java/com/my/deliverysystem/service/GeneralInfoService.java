package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.LanguageDAO;
import com.my.deliverysystem.dao.daoInterface.TariffDAO;
import com.my.deliverysystem.dao.daoInterface.beanDAO.LocationBeanDAO;
import com.my.deliverysystem.db.entity.Language;
import com.my.deliverysystem.db.entity.Tariff;
import com.my.deliverysystem.db.entity.bean.LocationBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * A class works with GeneralInfoServlet and realizes all it's main functions
 * -showGeneralInfo
 * -createTariffsTable (private)
 * -getCurrentLanguage
 * -createLocationsTable (private)
 */

public class GeneralInfoService {

    private final Logger logger = Logger.getLogger(GeneralInfoService.class);

    TariffDAO tariffService;
    LanguageDAO languageService ;
    LocationBeanDAO locationService;

    public GeneralInfoService(TariffDAO tariffService, LanguageDAO languageService, LocationBeanDAO locationService) {
        this.tariffService = tariffService;
        this.languageService = languageService;
        this.locationService = locationService;
    }

    public void showGeneralInfo(HttpServletRequest req) {
        createLocationsTable(req);
        createTariffsTable(req);
    }

    private void createTariffsTable(HttpServletRequest req) {
        logger.debug("Entered createTariffsTable() " + GeneralInfoService.class.getName());

        Language currentLanguage = getCurrentLanguage(req);

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

    private void createLocationsTable(HttpServletRequest req) {
        logger.debug("Entered createLocationsTable()" + GeneralInfoService.class.getName());
        List<LocationBean> locations = null;
        try {
            locations = locationService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("locations", locations);
    }
}
