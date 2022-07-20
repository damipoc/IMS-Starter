package com.qa.ims.controller;

import java.text.DecimalFormat;
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

        LOGGER.info("In which order ID would you like to add items?");
        Long orderId = utils.getLong();
        LOGGER.info("Please enter item ID you would like to add.");
        Long itemId = utils.getLong();
        OrderItem orderItem = orderItemDAO.create(new OrderItem(orderId, itemId));
        LOGGER.info("Item added to the order.");
        return orderItem;
    }

    @Override
    public OrderItem update() {
        LOGGER.info("Please enter the ID of the OrderItem you would like to update.");
        Long id = utils.getLong();
        LOGGER.info("Please enter an order ID.");
        Long orderId = utils.getLong();
        LOGGER.info("Please enter an item ID.");
        Long itemId = utils.getLong();
        OrderItem orderItem = orderItemDAO.update(new OrderItem(id, orderId, itemId));
        LOGGER.info("OrderItem Updated.");
        return orderItem;
    }

    @Override
    public int delete() {
        LOGGER.info("Please enter the ID of your order.");
        Long orderId = utils.getLong();
        LOGGER.info("Please enter the ID of the item you would like to remove.");
        Long itemId = utils.getLong();
        return orderItemDAO.deleteItem(orderId, itemId);
    }

    public double calculate() {

        DecimalFormat df = new DecimalFormat("0.00");
        
        LOGGER.info("Please enter the order ID that you would like to calculate.");
        Long orderId = utils.getLong();

        double value = orderItemDAO.calculate(orderId);
        LOGGER.info(df.format(value) + " Pounds\n");

        return value;
    }

}
