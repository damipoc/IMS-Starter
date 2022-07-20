package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.Utils;

public class OrderItemController implements CrudController<OrderItem> {

    public static final Logger LOGGER = LogManager.getLogger();

    private OrderItemDAO orderItemDAO;
    private Utils utils;

    public OrderItemController(OrderItemDAO orderItemDAO, Utils utils) {
        this.orderItemDAO = orderItemDAO;
        this.utils = utils;
    }

    @Override
    public List<OrderItem> readAll() {
        List<OrderItem> orderItems = orderItemDAO.readAll();
        for (OrderItem orderItem : orderItems) {
            LOGGER.info(orderItem);
        }
        LOGGER.info("Would you like to calculate the total cost? Y/N");
        String calc = utils.getString();
        if (calc.equalsIgnoreCase("Y")) {
            calculate();
        }
        return orderItems;
    }

    @Override
    public OrderItem create() {

        LOGGER.info("To which order ID would you like to add items?");
        Long orderId = utils.getLong();
        LOGGER.info("Please enter item id");
        Long itemId = utils.getLong();
        OrderItem orderItem = orderItemDAO.create(new OrderItem(orderId, itemId));
        LOGGER.info("Item added");
        return orderItem;
    }

    @Override
    public OrderItem update() {
		LOGGER.info("Please enter the id of the orderItem you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter order ID");
		Long orderId = utils.getLong();
		LOGGER.info("Please enter item ID");
		Long itemId = utils.getLong();
		OrderItem orderItem = orderItemDAO.update(new OrderItem(id, orderId, itemId));
		LOGGER.info("OrderItem Updated");
		return orderItem;
    }

    @Override
    public int delete() {
		LOGGER.info("Please enter the id of your order");
		Long orderId = utils.getLong();
        LOGGER.info("Please enter the id of the item you would like to remove");
        Long itemId = utils.getLong();
		return orderItemDAO.deleteItem(orderId, itemId);
    }

    public double calculate() {

        LOGGER.info("Which order ID would you like to calculate?");
        Long orderId = utils.getLong();

        double value = orderItemDAO.calculate(orderId);
        LOGGER.info(value);
        

        return value;
    }

}
