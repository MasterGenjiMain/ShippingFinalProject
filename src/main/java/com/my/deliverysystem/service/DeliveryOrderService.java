package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.implementation.DeliveryOrderDAOImplementation;
import com.my.deliverysystem.db.entity.DeliveryOrder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class DeliveryOrderService {
    final static Logger logger = Logger.getLogger(DeliveryOrderService.class.getName());

    public static void showDeliveryOrders(HttpServletRequest req) {
        DeliveryOrderDAOImplementation deliveryOrderService = new DeliveryOrderDAOImplementation();
        List<DeliveryOrder> deliveryOrders = null;
        try {
            deliveryOrders = deliveryOrderService.getAll();
        } catch (SQLException e) {
            logger.error(e);
        }
        req.setAttribute("deliveryOrders", deliveryOrders);
    }
}
