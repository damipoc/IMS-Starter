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

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<Order> readAll() {
        try (Connection connection = DBUtils.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");) {
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(modelFromResultSet(resultSet));
            }
            return orders;
        } catch (SQLException e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Order readLatest() {
        try (Connection connection = DBUtils.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1");) {
            resultSet.next();
            return modelFromResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Order read(Long id) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE id = ?");) {
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

    @Override
    public Order create(Order t) {
        System.out.println("Creating");
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO orders(order_date, fk_customer_id) VALUES (?, ?)");) {
            statement.setString(1, t.getDate());
            statement.setLong(2, t.getCustomerId());
            statement.executeUpdate();
            return readLatest();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Order update(Order t) {
        try (Connection connection = DBUtils.getInstance().getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(
                                "UPDATE orders SET order_date = ?, fk_customer_id = ? WHERE id = ?");) {
            statement.setString(1, t.getDate());
            statement.setLong(2, t.getCustomerId());
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
                PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?");) {
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String date = resultSet.getString("order_date");
        Long customerId = resultSet.getLong("fk_customer_id");
        return new Order(id, date, customerId);
    }

}
