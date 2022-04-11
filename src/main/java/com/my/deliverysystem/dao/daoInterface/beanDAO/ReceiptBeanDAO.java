package com.my.deliverysystem.dao.daoInterface.beanDAO;

import com.my.deliverysystem.db.entity.bean.ReceiptBean;

import java.sql.SQLException;
import java.util.List;

public interface ReceiptBeanDAO {
     List<ReceiptBean> getAll() throws SQLException;
     List<ReceiptBean> getByUserId(Long id) throws SQLException;
}
