package org.example.turistickivodic.services;

import org.example.turistickivodic.models.Destination;
import org.example.turistickivodic.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinationService {

    public synchronized List<Destination> getAllDestinations() {
        List<Destination> destinations = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM destinations")) {

            while (rs.next()) {
                destinations.add(new Destination(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinations;
    }

    public synchronized void createDestination(Destination destination) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO destinations (name, description) VALUES (?, ?)")) {

            stmt.setString(1, destination.getName());
            stmt.setString(2, destination.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateDestination(String id, Destination destination) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE destinations SET name = ?, description = ? WHERE id = ?")) {

            stmt.setString(1, destination.getName());
            stmt.setString(2, destination.getDescription());
            stmt.setString(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteDestination(String id) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM destinations WHERE id = ?")) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
