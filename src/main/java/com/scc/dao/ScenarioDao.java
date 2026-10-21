package com.scc.dao;

import com.scc.model.ScenarioEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public ScenarioDao() {
        DatabaseConnection.initializeSchema();
    }

    public boolean insert(ScenarioEntity entity) {
        String sql = "INSERT INTO laundry_orders (customer_name, weight_kg, total_amount, status) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.customerName());
            ps.setDouble(2, entity.weightKg());
            ps.setDouble(3, entity.totalAmount());
            ps.setString(4, entity.status());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    public List<ScenarioEntity> findAll() {
        List<ScenarioEntity> list = new ArrayList<>();
        String sql = "SELECT id, customer_name, weight_kg, total_amount, status "
                + "FROM laundry_orders ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getDouble("weight_kg"),
                        rs.getDouble("total_amount"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return list;
    }
}