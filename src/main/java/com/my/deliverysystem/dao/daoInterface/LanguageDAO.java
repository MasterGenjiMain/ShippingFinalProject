package com.my.deliverysystem.dao.daoInterface;

import com.my.deliverysystem.db.entity.Language;

import java.sql.SQLException;

public interface LanguageDAO {

    Language getByName(String pattern) throws SQLException;
}
