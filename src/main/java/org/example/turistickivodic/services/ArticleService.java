package org.example.turistickivodic.services;

import org.example.turistickivodic.models.Article;
import org.example.turistickivodic.models.Destination;
import org.example.turistickivodic.models.User;
import org.example.turistickivodic.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticleService {

    public synchronized List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM articles")) {

            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getInt("destination_id"),
                        rs.getInt("author_id"),
                        Arrays.asList(rs.getString("activities").split(","))
                );
                article.setCreatedAt(rs.getTimestamp("created_at"));

                // Fetch destination
                int destinationId = rs.getInt("destination_id");
                Destination destination = getDestinationById(destinationId);
                article.setDestination(destination);

                // Fetch author
                int authorId = rs.getInt("author_id");
                User author = UserService.getUserById(authorId);
                article.setAuthor(author);

                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public synchronized Destination getDestinationById(int id) {
        Destination destination = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM destinations WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    destination = new Destination(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destination;
    }

    public synchronized List<Article> getMostReadArticles() {
        List<Article> articles = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM articles WHERE created_at >= NOW() - INTERVAL 30 DAY ORDER BY views DESC LIMIT 10"
             );
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getInt("destination_id"),
                        rs.getInt("author_id"),
                        Arrays.asList(rs.getString("activities").split(","))
                );
                article.setCreatedAt(rs.getTimestamp("created_at"));

                // Fetch destination
                int destinationId = rs.getInt("destination_id");
                Destination destination = getDestinationById(destinationId);
                article.setDestination(destination);

                // Fetch author
                int authorId = rs.getInt("author_id");
                User author = UserService.getUserById(authorId);
                article.setAuthor(author);

                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }


    public synchronized List<Article> getArticlesByActivity(String activity) {
        List<Article> articles = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM articles WHERE FIND_IN_SET(?, activities)")) {
            stmt.setString(1, activity);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getInt("destination_id"),
                        rs.getInt("author_id"),
                        Arrays.asList(rs.getString("activities").split(","))
                );
                article.setCreatedAt(rs.getTimestamp("created_at"));

                // Fetch destination
                int destinationId = rs.getInt("destination_id");
                Destination destination = getDestinationById(destinationId);
                article.setDestination(destination);

                // Fetch author
                int authorId = rs.getInt("author_id");
                User author = UserService.getUserById(authorId);
                article.setAuthor(author);

                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public synchronized void createArticle(Article article) {
        System.out.println("Creating article: " + article);
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO articles (title, text, destination_id, author_id, activities) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, article.getTitle());
            stmt.setString(2, article.getText());
            stmt.setInt(3, article.getDestination_id());
            stmt.setInt(4, article.getAuthor_id());
            stmt.setString(5, String.join(",", article.getActivities()));
            int rowsInserted = stmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateArticle(int id, Article article) {
        System.out.println("Updating article: " + article);
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE articles SET title = ?, text = ?, destination_id = ?, author_id = ?, activities = ? WHERE id = ?")) {

            stmt.setString(1, article.getTitle());
            stmt.setString(2, article.getText());
            stmt.setInt(3, article.getDestination_id()); // Ensure destination_id is an integer
            stmt.setInt(4, article.getAuthor_id()); // Ensure author_id is an integer
            stmt.setString(5, String.join(",", article.getActivities()));
            stmt.setInt(6, id);
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteArticle(int id) {
        System.out.println("Deleting article with id: " + id);
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM articles WHERE id = ?")) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized Article getArticleById(int id) {
        Article article = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM articles WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    article = new Article(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("text"),
                            rs.getInt("destination_id"),
                            rs.getInt("author_id"),
                            Arrays.asList(rs.getString("activities").split(","))
                    );
                    article.setCreatedAt(rs.getTimestamp("created_at"));

                    // Fetch destination
                    int destinationId = rs.getInt("destination_id");
                    Destination destination = getDestinationById(destinationId);
                    article.setDestination(destination);

                    // Fetch author
                    int authorId = rs.getInt("author_id");
                    User author = UserService.getUserById(authorId);
                    article.setAuthor(author);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;
    }

    public synchronized void incrementArticleViews(int id) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE articles SET views = views + 1 WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
