package com.my.deliverysystem.service;

import com.my.deliverysystem.dao.daoInterface.beanDAO.ReceiptBeanDAO;
import com.my.deliverysystem.db.entity.User;
import com.my.deliverysystem.db.entity.bean.ReceiptBean;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Test
    void showReceiptsTest() throws SQLException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        ReceiptBeanDAO receiptBeanDAO = mock(ReceiptBeanDAO.class);
        HttpSession session = mock(HttpSession.class);
        User user = new User(1, null, null, null);

        when(session.getAttribute("user")).thenReturn(user);
        when(req.getSession()).thenReturn(session);

        final HashMap<Object, Object> requestInnerMap = new HashMap<>();
        doAnswer(invocationOnMock -> {
            requestInnerMap.put(invocationOnMock.getArgument(0), invocationOnMock.getArgument(1));
            return null;
        }).when(req).setAttribute(anyString(), any());

        when(receiptBeanDAO.getByUserId(user.getId())).thenReturn(List.of(new ReceiptBean(1, 1, null, 0, null, 0)));

        AccountService.showReceipts(req, receiptBeanDAO);

        assertEquals(1, ((List<ReceiptBean>) requestInnerMap.get("receipts")).get(0).getUserId());
    }

}