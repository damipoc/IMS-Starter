package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
    

    @Mock
	private Utils utils;

	@Mock
	private OrderDAO dao;

    @Mock
    private OrderItemDAO OIdao;

	@InjectMocks
	private OrderController controller;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private final LocalDateTime now = LocalDateTime.now();
    private final String orderDate = dtf.format(now);

	@Test
	public void testCreate() {
		final String DATE = orderDate;
		final Long CUSTID = 1L;
		final Order created = new Order(DATE, CUSTID);
        final OrderItem createdOrderItem = new OrderItem(null, 1L);

		Mockito.when(utils.getLong()).thenReturn(CUSTID, 1L);
		Mockito.when(dao.create(created)).thenReturn(created);
        Mockito.when(OIdao.create(createdOrderItem)).thenReturn(createdOrderItem);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(dao, Mockito.times(1)).create(created);
        Mockito.verify(OIdao, Mockito.times(1)).create(createdOrderItem);

	}

	@Test
	public void testReadAll() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1L, orderDate, 1L));

		Mockito.when(dao.readAll()).thenReturn(orders);

		assertEquals(orders, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}

	@Test
	public void testUpdate() {
		Order updated = new Order(null, null);

		assertEquals(null, this.controller.update());

		Mockito.verify(this.utils, Mockito.never()).getLong();
		Mockito.verify(this.utils, Mockito.never()).getString();
		Mockito.verify(this.utils, Mockito.never()).getDouble();
		Mockito.verify(this.dao, Mockito.never()).update(updated);
	}

	@Test
	public void testDelete() {
		final long ID = 1L;

		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(dao.delete(ID)).thenReturn(1);

		assertEquals(1L, this.controller.delete());

		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(ID);
	}

}
