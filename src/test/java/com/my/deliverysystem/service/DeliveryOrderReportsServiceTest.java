package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.beanDAO.DeliveryOrderBeanDAO;
import com.my.deliverysystem.dao.implementation.beanImpl.DeliveryOrderBeanDAOImpl;
import com.my.deliverysystem.dao.implementation.beanImpl.ReceiptBeanDAOImpl;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.db.entity.bean.DeliveryOrderBean;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryOrderReportsServiceTest {

    @Test
    void showDeliveryOrders() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        DeliveryOrderBeanDAO deliveryOrderService = mock(DeliveryOrderBeanDAO.class);

        List<DeliveryOrderBean> deliveryOrderBeanList = List.of(
                new DeliveryOrderBean(1, null, null, null, null, null, null, 0, 0, null, null),
                new DeliveryOrderBean(2, null, null, null, null, null, null, 0, 0, null, null),
                new DeliveryOrderBean(3, null, null, null, null, null, null, 0, 0, null, null));

        when(deliveryOrderService.getAll()).thenReturn(deliveryOrderBeanList);
        DeliveryOrderReportsService deliveryOrderReportsService = new DeliveryOrderReportsService(deliveryOrderService, null);
        deliveryOrderReportsService.showDeliveryOrders(req);
        verify(req).setAttribute("deliveryOrders", deliveryOrderBeanList);
    }

    @Test
    void showDeliveryOrdersShouldReturnSQLExceptionIfGetAllWorkedWrong() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        DeliveryOrderBeanDAO deliveryOrderService = mock(DeliveryOrderBeanDAO.class);

        when(deliveryOrderService.getAll()).thenThrow(SQLException.class);
        DeliveryOrderReportsService deliveryOrderReportsService = new DeliveryOrderReportsService(deliveryOrderService, null);
        deliveryOrderReportsService.showDeliveryOrders(req);
        assertThrows(SQLException.class, deliveryOrderService::getAll);
    }

    @Test
    void showReceiptPDFTest() throws SQLException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        ReceiptBeanDAOImpl receiptBeanDAOService = mock(ReceiptBeanDAOImpl.class);
        DeliveryOrderBeanDAOImpl deliveryOrderBeanDAOService = mock(DeliveryOrderBeanDAOImpl.class);
        ServletOutputStream servletOutputStream = mock(ServletOutputStream.class);
        HttpSession session = mock(HttpSession.class);
        long id = 1;

        User user = new User(1, "username", "mail", "pass");
        ReceiptBean receipt = new ReceiptBean(1, 0, null, 0, "New", 1);
        DeliveryOrderBean deliveryOrder = new DeliveryOrderBean(1, null, null, null,
                null, null, null, 0, 0, null, null);

        when(req.getParameter("id")).thenReturn(String.valueOf(id));
        when(receiptBeanDAOService.getById(id)).thenReturn(receipt);
        when(deliveryOrderBeanDAOService.getById(receipt.getDeliveryOrderId())).thenReturn(deliveryOrder);

        when(resp.getOutputStream()).thenReturn(servletOutputStream);
        when(resp.getOutputStream()).thenReturn(servletOutputStream);
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(user);

        DeliveryOrderReportsService dors = new DeliveryOrderReportsService(deliveryOrderBeanDAOService, receiptBeanDAOService);
        assertDoesNotThrow(() -> dors.showReportPDF(req, resp));
    }

}