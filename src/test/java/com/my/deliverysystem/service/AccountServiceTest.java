package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.ReceiptDAO;
import com.my.deliverysystem.dao.daoInterface.beanDAO.ReceiptBeanDAO;
import com.my.deliverysystem.dao.implementation.ReceiptDAOImplementation;
import com.my.deliverysystem.dao.implementation.beanImpl.DeliveryOrderBeanDAOImpl;
import com.my.deliverysystem.dao.implementation.beanImpl.ReceiptBeanDAOImpl;
import com.my.deliverysystem.db.entity.Receipt;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.db.entity.bean.DeliveryOrderBean;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Test
    void testShowReceiptsWithCorrectValue() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptBeanDAO receiptBeanDAO = mock(ReceiptBeanDAO.class);
        HttpSession session = mock(HttpSession.class);
        User user = new User(1, null, null, null);

        AccountService accountService = new AccountService(receiptBeanDAO, null, null);

        when(session.getAttribute("user")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        final HashMap<Object, Object> requestInnerMap = new HashMap<>();
        doAnswer(invocationOnMock -> {
            requestInnerMap.put(invocationOnMock.getArgument(0), invocationOnMock.getArgument(1));
            return null;
        }).when(req).setAttribute(anyString(), any());

        when(receiptBeanDAO.getByUserId(user.getId())).thenReturn(List.of(new ReceiptBean(1, 1, null, 0, null, 0)));

        accountService.showReceipts(req);

        assertEquals(user.getId(), ((List<ReceiptBean>) requestInnerMap.get("receipts")).get(0).getUserId());
    }

    @Test
    void showReceiptsShouldReturnFalse() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptBeanDAO receiptBeanDAO = mock(ReceiptBeanDAO.class);
        HttpSession session = mock(HttpSession.class);
        User user = new User(2, null, null, null);

        AccountService accountService = new AccountService(receiptBeanDAO, null, null);

        when(session.getAttribute("user")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        final HashMap<Object, Object> requestInnerMap = new HashMap<>();
        doAnswer(invocationOnMock -> {
            requestInnerMap.put(invocationOnMock.getArgument(0), invocationOnMock.getArgument(1));
            return null;
        }).when(req).setAttribute(anyString(), any());

        when(receiptBeanDAO.getByUserId(user.getId())).thenReturn(List.of(new ReceiptBean(1, 1, null, 0, null, 0)));

        accountService.showReceipts(req);

        assertNotEquals(user.getId(), ((List<ReceiptBean>) requestInnerMap.get("receipts")).get(0).getUserId());
    }

    @Test
    void showReceiptsShouldReturnSQLExceptionIfUserIdIncorrect() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptBeanDAO receiptBeanDAO = mock(ReceiptBeanDAO.class);
        HttpSession session = mock(HttpSession.class);
        User user = new User(-2, null, null, null);

        AccountService accountService = new AccountService(receiptBeanDAO, null, null);

        when(session.getAttribute("user")).thenReturn(user);
        when(req.getSession()).thenReturn(session);


        when(receiptBeanDAO.getByUserId(user.getId())).thenThrow(SQLException.class);

        accountService.showReceipts(req);

        assertThrows(SQLException.class, () -> receiptBeanDAO.getByUserId(user.getId()));
    }

    @Test
    void changeStatusSetsCorrectValue() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptDAO service = mock(ReceiptDAO.class);
        AccountService accountService = new AccountService(null, service, null);
        long statusToSet = 1;
        long firstId = 1;
        Receipt receiptFirst = new Receipt(1, 0, 0, 0, 0, 0);

        when(req.getParameter("id")).thenReturn(String.valueOf(firstId));
        when(service.getByReceiptId(firstId)).thenReturn(receiptFirst);
        when(service.update(receiptFirst)).thenReturn(true);

        accountService.changeStatus(req, statusToSet);
        assertEquals(statusToSet, receiptFirst.getReceiptStatusId());
    }

    @Test
    void SQLExceptionThrowsWhenIdIncorrect() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptDAO service = mock(ReceiptDAO.class);
        AccountService accountService = new AccountService(null, service, null);
        long incorrectId = -5;
        long statusToSet = 1;

        when(req.getParameter("id")).thenReturn(String.valueOf(incorrectId));
        when(service.getByReceiptId(incorrectId)).thenThrow(SQLException.class);

        accountService.changeStatus(req, statusToSet);

        assertThrows(SQLException.class, () -> service.getByReceiptId(incorrectId));
    }

//    @Test
//    void showReceiptPDFTest() throws SQLException, IOException {
//        HttpServletRequest req = mock(HttpServletRequest.class);
//        HttpServletResponse resp = mock(HttpServletResponse.class);
//        ReceiptBeanDAOImpl receiptBeanDAOService = mock(ReceiptBeanDAOImpl.class);
//        DeliveryOrderBeanDAOImpl deliveryOrderBeanDAOService = mock(DeliveryOrderBeanDAOImpl.class);
//        long id = 1;
//        ReceiptBean receipt = new ReceiptBean(1, 0, null, 0, "New", 1);
//        DeliveryOrderBean deliveryOrder = new DeliveryOrderBean(1, null, null, null,
//                        null, null, null, 0, 0, null, null);
//
//        when(req.getParameter("id")).thenReturn(String.valueOf(id));
//        when(receiptBeanDAOService.getById(id)).thenReturn(receipt);
//        when(deliveryOrderBeanDAOService.getById(receipt.getDeliveryOrderId())).thenReturn(deliveryOrder);
//
//        when(resp.getOutputStream()).thenReturn();
//
//        AccountService accountService = new AccountService(receiptBeanDAOService, null, deliveryOrderBeanDAOService);
//        accountService.showReceiptPDF(req, resp);
//    }

}