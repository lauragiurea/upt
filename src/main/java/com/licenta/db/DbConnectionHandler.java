package com.licenta.db;

import java.sql.*;
import java.util.Properties;

public class DbConnectionHandler {
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/upt";
    private static final String username = "root";
    private static final String password = "UPTlicenta";

    public static Connection getConnection() throws Exception {
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(connectionUrl, connectionProps);
    }
}