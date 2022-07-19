package com.qa.ims.controller;

import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {

    public static final Logger LOGGER = LogManager.getLogger();

    private OrderDAO orderDAO;
    private Utils utils;

    public OrderController(OrderDAO orderDAO, Utils utils) {
        this.orderDAO = orderDAO;
        this.utils = utils;
    }

    @Override
    public List<Order> readAll() {
        List<Order> orders = orderDAO.readAll();
        for (Order order : orders) {
            LOGGER.info(order);
        }
        return orders;
    }

    @Override
    public Order create() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String orderDate = dtf.format(now);
        LOGGER.info("Please enter a customer ID to insert in the order");
        Long customerID = utils.getLong();
        LOGGER.info("Please enter an item ID to insert in the order");
        Long itemID = utils.getLong();
        Order order = orderDAO.create(new Order(orderDate, customerID, itemID));
        LOGGER.info("Order created");
        return order;
    }

    @Override
    public Order update() {
        LOGGER.info("You can not update orders, please delete the order and remake.");
        Order order = orderDAO.readLatest();
        return order;
    }

    @Override
    public int delete() {
        LOGGER.info("Please enter the id of the order you would like to delete");
        Long id = utils.getLong();
        return orderDAO.delete(id);
    }

}
