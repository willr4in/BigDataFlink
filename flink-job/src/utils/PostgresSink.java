package org.example.utils;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import java.sql.*;

public class PostgresSink extends RichSinkFunction<SalesData> {
    private Connection connection;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = DriverManager.getConnection(
            "jdbc:postgresql://postgres:5432/sales",
            "postgres",
            "postgres"
        );
        connection.setAutoCommit(false);
    }

    @Override
    public void invoke(SalesData data, Context context) throws Exception {
        try (PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO dim_customer (customer_id, first_name, last_name, age, email) " +
            "VALUES (?, ?, ?, ?, ?) ON CONFLICT (customer_id) DO NOTHING")) {
            ps.setInt(1, data.customerId);
            ps.setString(2, data.customerFirstName);
            ps.setString(3, data.customerLastName);
            ps.setInt(4, data.customerAge);
            ps.setString(5, data.customerEmail);
            ps.execute();
        }

        try (PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO dim_product (product_id, name, category, price) " +
            "VALUES (?, ?, ?, ?) ON CONFLICT (product_id) DO NOTHING")) {
            ps.setInt(1, data.productId);
            ps.setString(2, data.productName);
            ps.setString(3, data.productCategory);
            ps.setDouble(4, data.productPrice);
            ps.execute();
        }

        try (PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO dim_time (sale_date, day, month, year, quarter) " +
            "VALUES (?, ?, ?, ?, ?) ON CONFLICT (sale_date) DO NOTHING")) {
            ps.setDate(1, Date.valueOf(data.saleDate));
            ps.setInt(2, data.saleDate.getDayOfMonth());
            ps.setInt(3, data.saleDate.getMonthValue());
            ps.setInt(4, data.saleDate.getYear());
            ps.setInt(5, (data.saleDate.getMonthValue()-1)/3 + 1);
            ps.execute();
        }

        try (PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO fact_sales (customer_id, product_id, time_id, quantity, total_price) " +
            "SELECT ?, ?, t.time_id, ?, ? FROM dim_time t WHERE t.sale_date = ?")) {
            ps.setInt(1, data.customerId);
            ps.setInt(2, data.productId);
            ps.setInt(3, data.quantity);
            ps.setDouble(4, data.totalPrice);
            ps.setDate(5, Date.valueOf(data.saleDate));
            ps.execute();
        }
        
        connection.commit();
    }

    @Override
    public void close() throws Exception {
        if (connection != null) connection.close();
        super.close();
    }
}