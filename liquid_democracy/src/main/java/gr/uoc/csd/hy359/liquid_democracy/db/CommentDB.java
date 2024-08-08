/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoc.csd.hy359.liquid_democracy.db;

import gr.uoc.csd.hy359.liquid_democracy.model.Comment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author papadako
 */
public class CommentDB {

    /**
     * Get all photos
     *
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Comment> getComments() throws ClassNotFoundException {
        List<Comment> comments = new ArrayList<>();

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM comment;");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                while (res.next() == true) {
                    Comment comment = new Comment();
                    comment.setID(res.getInt("commentID"));
                    comment.setUserName(res.getString("userID"));
                    comment.setInitiativeID(res.getInt("initiativeID"));
                    comment.setCreated(res.getTimestamp("created"));
                    comment.setModified(res.getTimestamp("modified"));
                    comment.setComment(res.getString("comment"));
                    comments.add(comment);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CommentDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return comments;
    }

    /**
     * Get comment for specific photo
     *
     * @param initiativeID
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Comment> getComments(int initiativeID) throws ClassNotFoundException {
        List<Comment> comments = new ArrayList<>();

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM comment WHERE ")
                        .append(" initiativeID = ").append("'").append(initiativeID).append("';");;

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                while (res.next() == true) {
                    Comment comment = new Comment();
                    comment.setID(res.getInt("commentID"));
                    comment.setUserName(res.getString("userID"));
                    comment.setInitiativeID(res.getInt("initiativeID"));
                    comment.setCreated(res.getTimestamp("created"));
                    comment.setModified(res.getTimestamp("modified"));
                    comment.setComment(res.getString("comment"));
                    comments.add(comment);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CommentDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return comments;
    }

    /**
     * Get comment
     *
     * @param commentID
     * @return
     * @throws ClassNotFoundException
     */
    public static Comment getComment(int commentID) throws ClassNotFoundException {
        Comment comment = new Comment();
        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM comment ")
                        .append(" WHERE ")
                        .append(" commentID = ").append("'").append(commentID).append("';");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                if (res.next() == true) {
                    comment.setID(res.getInt("commentID"));
                    comment.setUserName(res.getString("userID"));
                    comment.setInitiativeID(res.getInt("initiativeID"));
                    comment.setCreated(res.getTimestamp("created"));
                    comment.setModified(res.getTimestamp("modified"));
                    comment.setComment(res.getString("comment"));
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CommentDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return comment;
    }

    /**
     * Establish a database connection and add the comment into the database.
     *
     * @param comment
     * @throws ClassNotFoundException
     */
    public static void addComment(Comment comment) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            comment.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(CommentDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("INSERT INTO ")
                        .append(" comment (userID, initiativeID, comment) ")
                        .append(" VALUES (")
                        //.append("'").append(comment.getID()).append("',")
                        .append("'").append(comment.getUserName()).append("',")
                        .append("'").append(comment.getInitiativeID()).append("',")
                        .append("'").append(comment.getComment()).append("');");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The comment was successfully added in the database.");

                // Close connection
                stmt.close();
                con.close();

            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CommentDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates information for specific comment
     *
     * @param comment
     * @throws ClassNotFoundException
     */
    public static void updateComment(Comment comment) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            comment.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(CommentDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("UPDATE comment ")
                        .append(" SET ")
                        .append(" COMMENT = ").append("'").append(comment.getComment()).append("'")
                        .append(" WHERE commentID = ").append("'").append(comment.getID()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The comment was successfully updated in the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CommentDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete specific comment
     *
     * @param comment
     * @throws ClassNotFoundException
     */
    public static void deleteComment(Comment comment) throws ClassNotFoundException {

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM comment ")
                        .append(" WHERE ")
                        .append(" COMMENTID = ").append("'").append(comment.getID()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The comment was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CommentDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete specific comment
     *
     * @param id
     * @throws ClassNotFoundException
     */
    public static void deleteComment(int id) throws ClassNotFoundException {

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM comment ")
                        .append(" WHERE ")
                        .append(" commentID = ").append("'").append(id).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The comment was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(CommentDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
