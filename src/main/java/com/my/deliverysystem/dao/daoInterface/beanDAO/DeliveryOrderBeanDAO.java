package com.my.deliverysystem.dao.daoInterface.beanDAO;

import com.my.deliverysystem.db.entity.bean.DeliveryOrderBean;

import java.sql.SQLException;
import java.util.List;

public interface DeliveryOrderBeanDAO {
    List<DeliveryOrderBean> getAll() throws SQLException;
}
