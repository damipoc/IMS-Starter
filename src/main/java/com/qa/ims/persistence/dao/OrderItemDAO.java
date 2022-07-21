/*
 * @author
 * Damian Poclitar
 */

package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.OrderItem;
import com.qa.ims.utils.DBUtils;

public class OrderItemDAO implements Dao<OrderItem> {

    public static final Logger LOGGER = LogManager.getLogger();

    
    /** 
     * Reads all the OrderItems from the database
     * 
     * @return List<OrderItem>
     */
    @Override
    public List<OrderItem> readAll() {
        try (Connection connection = DBUtils.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orderItems");) {
            List<OrderItem> orderItems = new ArrayList<>();
            while (resultSet.next()) {
                orderItems.add(modelFromResultSet(resultSet));
            }
            return orderItems;
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public OrderItem readLatest() {
        try (Connection connection = DBUtils.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orderItems ORDER BY id DESC LIMIT 1");) {
            resultSet.next();
            return modelFromResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public OrderItem read(Long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM orderItems WHERE id = ?");) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery();) {
                resultSet.next();
                return modelFromResultSet(resultSet);
            }
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());

        }
        return null;
    }

    
    /** 
     * Creates an OrderItem in the database
     * 
     * @param t Object of OrderItem, ID will be ignored
     * @return OrderItem
     */
    @Override
    public OrderItem create(OrderItem t) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO orderItems(fk_order_id, fk_item_id) VALUES (?, ?)");) {
            statement.setLong(1, t.getOrderId());
            statement.setLong(2, t.getItemId());
            statement.executeUpdate();
            return readLatest();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    
    /** 
     * Updates an order item in the database
     * 
     * @param t Takes in an OrderItem object and the ID is used to update the orderItem in the database
     * @return OrderItem
     */
    @Override
    public OrderItem update(OrderItem t) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(
                                "UPDATE orderItems SET fk_order_id = ?, fk_item_id = ? WHERE id = ?");) {
            statement.setLong(1, t.getOrderId());
            statement.setLong(2, t.getItemId());
            statement.setLong(3, t.getId());
            statement.executeUpdate();
            return read(t.getId());
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public int delete(long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM orderItems WHERE id = ?");) {
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public OrderItem modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long orderId = resultSet.getLong("fk_order_id");
        Long itemId = resultSet.getLong("fk_item_id");
        return new OrderItem(id, orderId, itemId);
    }

    
    /** 
     * Takes in the OrderID and then uses all the itemIDs in OrderItem to sum the total value in 2 decimal form.
     * 
     * @param orderId 
     * @return double
     */
    public double calculate(Long orderId) {
        Double sumValue = 0.00;

        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT SUM(i.value) FROM items i INNER JOIN orderItems oi ON oi.fk_item_id = i.id INNER JOIN orders o ON o.id = oi.fk_order_id WHERE oi.fk_order_id = ?");) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery();) {

                while (resultSet.next()) {
                    sumValue = resultSet.getDouble("SUM(i.value)");
                }
                return sumValue;
            }
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());

        }
        return sumValue;
    }

    
    /** 
     * Takes in the Order ID and Item ID of the orderItem that needs to be deleted
     * 
     * @param orderId
     * @param itemId
     * @return int
     */
    public int deleteItem(Long orderId, Long itemId) {

        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("DELETE FROM orderItems WHERE fk_order_id = ? AND fk_item_id = ?");) {
            statement.setLong(1, orderId);
            statement.setLong(2, itemId);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }

        return 0;
    }

}
