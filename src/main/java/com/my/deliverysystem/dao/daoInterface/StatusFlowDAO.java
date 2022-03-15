package com.my.deliverysystem.dao.daoInterface;


import com.my.deliverysystem.db.entity.StatusFlow;

import java.util.List;

public interface StatusFlowDAO {
    //create
    void add(StatusFlow statusFlow);

    //read
    List<StatusFlow> getAll();
    StatusFlow getById(Long id);

    //update
    void update(StatusFlow statusFlow);

    //delete
    void remove(StatusFlow statusFlow);
}