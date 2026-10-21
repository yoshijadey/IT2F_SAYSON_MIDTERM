package com.scc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:scenario.db";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
        return conn;
    }

    public static void initializeSchema() {
        String sql = "CREATE TABLE IF NOT EXISTS laundry_orders ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "customer_name TEXT NOT NULL, "
                + "weight_kg REAL NOT NULL, "
                + "total_amount REAL NOT NULL, "
                + "status TEXT NOT NULL "
                + "CHECK (status IN ('RECEIVED', 'WASHING', 'READY'))"
                + ")";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database: " + e.getMessage(), e);
        }
    }
}