package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.DeliveryOrderDAO;
import com.my.deliverysystem.dao.daoInterface.ReceiptDAO;
import com.my.deliverysystem.dao.daoInterface.beanDAO.ReceiptBeanDAO;
import com.my.deliverysystem.db.entity.DeliveryOrder;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ApproveServiceTest {

    @Test
    void showReceiptsShouldReturnCorrectList() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptBeanDAO receiptBeanDAO = mock(ReceiptBeanDAO.class);
        final HashMap<Object, Object> requestInnerMap = new HashMap<>();
        doAnswer(invocationOnMock -> {
            requestInnerMap.put(invocationOnMock.getArgument(0), invocationOnMock.getArgument(1));
            return null;
        }).when(req).setAttribute(anyString(), any());

        when(receiptBeanDAO.getAll()).thenReturn(List.of(
                new ReceiptBean(1, 1, null, 0, null, 1),
                new ReceiptBean(2, 1, null, 0, null, 2)));

        ApproveService approveService = new ApproveService(receiptBeanDAO);
        approveService.showReceipts(req);

        assertEquals(receiptBeanDAO.getAll(), requestInnerMap.get("receipts"));
    }

    @Test
    void showReceiptsShouldReturnSQLException() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptBeanDAO receiptBeanDAO = mock(ReceiptBeanDAO.class);
        final HashMap<Object, Object> requestInnerMap = new HashMap<>();
        doAnswer(invocationOnMock -> {
            requestInnerMap.put(invocationOnMock.getArgument(0), invocationOnMock.getArgument(1));
            return null;
        }).when(req).setAttribute(anyString(), any());

        when(receiptBeanDAO.getAll()).thenThrow(SQLException.class);

        ApproveService approveService = new ApproveService(receiptBeanDAO);
        approveService.showReceipts(req);
        assertThrows(SQLException.class, receiptBeanDAO::getAll);
    }

    @Test
    void changeStatusSetsCorrectValue() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptDAO service = mock(ReceiptDAO.class);
        long statusToSet = 6;
        long firstId = 1;
        Receipt receiptFirst = new Receipt(1, 0, 0, 0, 5, 0);

        when(req.getParameter("id")).thenReturn(String.valueOf(firstId));
        when(service.getByReceiptId(firstId)).thenReturn(receiptFirst);
        when(service.update(receiptFirst)).thenReturn(true);

        ApproveService approveService = new ApproveService(service);
        approveService.changeStatus(req, statusToSet);
        assertEquals(statusToSet, receiptFirst.getReceiptStatusId());
    }

    @Test
    void SQLExceptionThrowsWhenIdIncorrect() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptDAO service = mock(ReceiptDAO.class);
        long incorrectId = -5;
        long statusToSet = 1;

        when(req.getParameter("id")).thenReturn(String.valueOf(incorrectId));
        when(service.getByReceiptId(incorrectId)).thenThrow(SQLException.class);

        ApproveService approveService = new ApproveService(service);
        approveService.changeStatus(req, statusToSet);

        assertThrows(SQLException.class, () -> service.getByReceiptId(incorrectId));
    }

    @Test
    void nextStatusSetsCorrectWhenStatusNotClosedNextStatus() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptDAO service = mock(ReceiptDAO.class);
        DeliveryOrderDAO orderService = mock(DeliveryOrderDAO.class);
        Receipt receiptFirst = new Receipt(1, 0, 0, 0, 2, 0);
        long expectedStatus = receiptFirst.getReceiptStatusId() + 1;


        when(req.getParameter("id")).thenReturn(String.valueOf(receiptFirst.getId()));
        when(service.getByReceiptId(1)).thenReturn(receiptFirst);
        ApproveService approveService = new ApproveService(null, service, orderService);
        approveService.nextStatus(req);

        assertEquals(expectedStatus, receiptFirst.getReceiptStatusId());

    }

    @Test
    void nextStatusSetsReceivingDateWhenStatusClosed() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptDAO service = mock(ReceiptDAO.class);
        DeliveryOrderDAO orderService = mock(DeliveryOrderDAO.class);
        Receipt receiptFirst = new Receipt(1, 0, 0, 0, 6, 1);
        long expectedStatus = receiptFirst.getReceiptStatusId() + 1;
        DeliveryOrder deliveryOrder = new DeliveryOrder(1, 1, null,
                null, null, 1, 0, 0, null, 1);

        when(req.getParameter("id")).thenReturn(String.valueOf(receiptFirst.getId()));
        when(service.getByReceiptId(1)).thenReturn(receiptFirst);
        when(orderService.getById(receiptFirst.getDeliveryOrderId())).thenReturn(deliveryOrder);

        ApproveService approveService = new ApproveService(null, service, orderService);
        approveService.nextStatus(req);

        assertEquals(expectedStatus, receiptFirst.getReceiptStatusId());
        assertNotNull(deliveryOrder.getReceivingDate());
    }

    @Test
    void approveSetsCorrectValueWhenCals() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        ReceiptDAO service = mock(ReceiptDAO.class);
        long id = 1;
        long statusToApprove = 2;
        User user = new User(1, null, null, null);
        Receipt receipt = new Receipt(1,0,0,0,1,1);

        when(req.getParameter("id")).thenReturn(String.valueOf(id));
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("user")).thenReturn(user);
        when(service.getByReceiptId(id)).thenReturn(receipt);

        ApproveService approveService = new ApproveService(service);
        approveService.approve(req, statusToApprove);
        assertEquals(statusToApprove, receipt.getReceiptStatusId());

    }

}