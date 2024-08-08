/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoc.csd.hy359.liquid_democracy.db;

import gr.uoc.csd.hy359.liquid_democracy.model.Rating;
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
public class RatingDB {

    /**
     * Get all ratings
     *
     * @param
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Rating> getRatings() throws ClassNotFoundException {
        List<Rating> ratings = new ArrayList<>();

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM rating;");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                while (res.next() == true) {
                    Rating rating = new Rating();
                    rating.setID(res.getInt("ratingID"));
                    rating.setUserName(res.getString("userID"));
                    rating.setInitiativeID(res.getInt("initiativeID"));
                    rating.setRate(res.getInt("rate"));
                    ratings.add(rating);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(RatingDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ratings;
    }

    /**
     * Get ratings for specific initiative
     *
     * @param initiativeID
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Rating> getRatings(int initiativeID) throws ClassNotFoundException {
        List<Rating> ratings = new ArrayList<>();

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM rating WHERE ")
                        .append(" initiativeID = ").append("'").append(initiativeID).append("';");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                while (res.next() == true) {
                    Rating rating = new Rating();
                    rating.setID(res.getInt("ratingID"));
                    rating.setUserName(res.getString("userID"));
                    rating.setInitiativeID(res.getInt("initiativeID"));
                    rating.setRate(res.getInt("rate"));
                    ratings.add(rating);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(RatingDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ratings;
    }

    /**
     * Get Rating
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     */
    public static Rating getRating(int id) throws ClassNotFoundException {
        Rating rating = new Rating();
        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM rating ")
                        .append(" WHERE ")
                        .append(" ratingID = ").append("'").append(id).append("';");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                if (res.next() == true) {
                    rating.setID(res.getInt("ratingID"));
                    rating.setUserName(res.getString("userID"));
                    rating.setInitiativeID(res.getInt("initiativeID"));
                    rating.setRate(res.getInt("rate"));
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(RatingDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rating;
    }

    /**
     * Establish a database connection and add the rating into the database.
     *
     * @param rating
     * @throws ClassNotFoundException
     */
    public static void addRating(Rating rating) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            rating.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(RatingDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("INSERT INTO ")
                        .append(" rating (initiativeID, userID, rate) ")
                        .append(" VALUES (")
                        //.append("'").append(rating.getID()).append("',")
                        .append("'").append(rating.getInitiativeID()).append("',")
                        .append("'").append(rating.getUserName()).append("',")
                        .append("'").append(rating.getRate()).append("');");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The rate was successfully added in the database.");

                // Close connection
                stmt.close();
                con.close();

            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(RatingDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates information for specific rating
     *
     * @param rating
     * @throws ClassNotFoundException
     */
    public static void updateRating(Rating rating) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            rating.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(RatingDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("UPDATE rating ")
                        .append(" SET ")
                        .append(" RATE = ").append("'").append(rating.getRate()).append("'")
                        .append(" WHERE initiativeID = ").append("'").append(rating.getInitiativeID()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The rating was successfully updated in the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(RatingDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete specific rating
     *
     * @param rating
     * @throws ClassNotFoundException
     */
    public static void deleteRating(Rating rating) throws ClassNotFoundException {

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM rating ")
                        .append(" WHERE ")
                        .append(" ratingID = ").append("'").append(rating.getID()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The rating was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(RatingDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete specific rating
     *
     * @param id
     * @throws ClassNotFoundException
     */
    public static void deleteRating(int id) throws ClassNotFoundException {

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM rating ")
                        .append(" WHERE ")
                        .append(" ratingID = ").append("'").append(id).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The rating was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(RatingDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
