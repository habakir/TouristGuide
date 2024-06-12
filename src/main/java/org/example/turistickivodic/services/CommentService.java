package org.example.turistickivodic.services;

import org.example.turistickivodic.models.Comment;
import org.example.turistickivodic.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentService {
    public synchronized List<Comment> getCommentsByArticleId(int articleId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM comments WHERE article_id = ?")) {
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    comments.add(new Comment(
                            rs.getInt("id"),
                            rs.getInt("article_id"),
                            rs.getString("author"),
                            rs.getString("text"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public synchronized void createComment(Comment comment) {
        System.out.println("Creating comment: " + comment);
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO comments (article_id, author, text, created_at) VALUES (?, ?, ?, ?)")) {

            if (comment == null) {
                throw new NullPointerException("Comment object is null");
            }

            if (comment.getArticleId() == 0) {
                throw new NullPointerException("Article ID is not set");
            }

            if (comment.getAuthor() == null) {
                throw new NullPointerException("Author is not set");
            }

            if (comment.getText() == null) {
                throw new NullPointerException("Text is not set");
            }

            stmt.setInt(1, comment.getArticleId());
            stmt.setString(2, comment.getAuthor());
            stmt.setString(3, comment.getText());
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public synchronized void updateComment(int id, Comment comment) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE comments SET article_id = ?, author = ?, text = ?, created_at = ? WHERE id = ?")) {

            stmt.setInt(1, comment.getArticleId());
            stmt.setString(2, comment.getAuthor());
            stmt.setString(3, comment.getText());
            stmt.setTimestamp(4, new java.sql.Timestamp(comment.getCreatedAt().getTime()));
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteComment(int id) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM comments WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
