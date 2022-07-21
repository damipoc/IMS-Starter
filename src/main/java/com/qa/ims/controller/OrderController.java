/*
 * @author
 * Damian Poclitar
 */

package com.qa.ims.controller;

import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderItemDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.Utils;

/*
 * Takes in order details for crud functionality
 */

public class OrderController implements CrudController<Order> {

    public static final Logger LOGGER = LogManager.getLogger();

    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private Utils utils;

    public OrderController(OrderDAO orderDAO, OrderItemDAO orderItemDAO, Utils utils) {
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
        this.utils = utils;
    }

    
    /** 
     * 
     * Reads all the orders to the logger
     * @return List<Order>
     */
    @Override
    public List<Order> readAll() {
        List<Order> orders = orderDAO.readAll();
        for (Order order : orders) {
            LOGGER.info(order);
        }
        return orders;
    }

    
    /** 
     * 
     * Creates an order and an orderitem by taking in user input
     * @return Order
     */
    @Override
    public Order create() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String orderDate = dtf.format(now);
        LOGGER.info("Please enter a customer ID to insert in the order.");
        Long customerID = utils.getLong();
        LOGGER.info("Please enter an item ID to insert in the order.");
        Long itemID = utils.getLong();
        Order order = orderDAO.create(new Order(orderDate, customerID));
        orderItemDAO.create(new OrderItem(order.getId(), itemID));
        LOGGER.info("Order created.\nTo add more items please use the Order Item menu.");
        return order;
    }

    
    /** 
     * Not allowed to update the order
     * @return Order
     */
    @Override
    public Order update() {
        LOGGER.info("You can not update orders, to add more items please use the Order Item menu.");
        Order order = orderDAO.readLatest();
        return order;
    }

    
    /** 
     * 
     * Deletes an existing order by taking the ID of the order
     * @return int
     */
    @Override
    public int delete() {
        LOGGER.info("Please enter the ID of the order you would like to delete.");
        Long id = utils.getLong();
        return orderDAO.delete(id);
    }

}
