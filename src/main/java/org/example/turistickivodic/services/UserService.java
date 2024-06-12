package org.example.turistickivodic.services;

import org.example.turistickivodic.models.User;
import org.example.turistickivodic.utils.DatabaseUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.example.turistickivodic.utils.JwtUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    public synchronized void createUser(User user) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO users (email, first_name, last_name, password, user_type, is_active) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, user.getEmail());
                stmt.setString(2, user.getFirstName());
                stmt.setString(3, user.getLastName());
                stmt.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
                stmt.setString(5, user.getUserType());
                stmt.setBoolean(6, user.isActive());
                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized User getUserByEmail(String email) {
        User user = null;
        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        user = new User(
                                rs.getInt("id"),
                                rs.getString("email"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("password"),
                                rs.getString("user_type"),
                                rs.getBoolean("is_active")
                        );
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static synchronized User getUserById(int id) {
        User user = null;
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        user = new User(
                                rs.getInt("id"),
                                rs.getString("email"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("password"),
                                rs.getString("user_type"),
                                rs.getBoolean("is_active")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public synchronized List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM users";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("password"),
                            rs.getString("user_type"),
                            rs.getBoolean("is_active")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public synchronized void updateUser(String id, User user) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "UPDATE users SET email = ?, first_name = ?, last_name = ?, password = ?, user_type = ?, is_active = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, user.getEmail());
                stmt.setString(2, user.getFirstName());
                stmt.setString(3, user.getLastName());
                stmt.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
                stmt.setString(5, user.getUserType());
                stmt.setBoolean(6, user.isActive());
                stmt.setString(7, id);
                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteUser(int id) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            String deleteComments = "DELETE FROM comments WHERE article_id IN (SELECT id FROM articles WHERE author_id = ?)";
            try (PreparedStatement stmtComments = conn.prepareStatement(deleteComments)) {
                stmtComments.setInt(1, id);
                stmtComments.executeUpdate();
            }
            String deleteArticles = "DELETE FROM articles WHERE author_id = ?";
            try (PreparedStatement stmtArticles = conn.prepareStatement(deleteArticles)) {
                stmtArticles.setInt(1, id);
                stmtArticles.executeUpdate();
            }
            String sql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized User getUserByToken(String token) {
        String email = JwtUtil.decodeToken(token);
        System.out.println("Decoded email: " + email);
        return getUserByEmail(email);
    }
}
