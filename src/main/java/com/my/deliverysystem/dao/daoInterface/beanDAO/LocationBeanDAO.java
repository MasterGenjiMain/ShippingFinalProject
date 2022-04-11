package com.my.deliverysystem.dao.daoInterface.beanDAO;

import com.my.deliverysystem.db.entity.bean.LocationBean;

import java.sql.SQLException;
import java.util.List;

public interface LocationBeanDAO {
    List<LocationBean> getAll() throws SQLException;
}
