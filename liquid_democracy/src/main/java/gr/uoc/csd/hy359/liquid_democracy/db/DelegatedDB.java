/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoc.csd.hy359.liquid_democracy.db;

import gr.uoc.csd.hy359.liquid_democracy.model.Delegated;
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
public class DelegatedDB {

    /**
     * Get all delegations
     *
     * @param
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Delegated> getDelegations() throws ClassNotFoundException {
        List<Delegated> delegated = new ArrayList<>();

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM delegated;");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                while (res.next() == true) {
                    Delegated delegate = new Delegated();
                    delegate.setID(res.getInt("ID"));
                    delegate.setUserName(res.getString("userID"));
                    delegate.setInitiativeID(res.getInt("initiativeID"));
                    delegate.setDelegator(res.getString("delegatorID"));
                    delegated.add(delegate);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(DelegatedDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return delegated;
    }

    /**
     * Get delegated for specific initiative
     *
     * @param initiativeID
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Delegated> getDelegated(int initiativeID) throws ClassNotFoundException {
        List<Delegated> delegated = new ArrayList<>();

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM delegated WHERE ")
                        .append(" initiativeID = ").append("'").append(initiativeID).append("';");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                while (res.next() == true) {
                    Delegated delegate = new Delegated();
                    delegate.setID(res.getInt("ID"));
                    delegate.setUserName(res.getString("userID"));
                    delegate.setInitiativeID(res.getInt("initiativeID"));
                    delegate.setDelegator(res.getString("delegatorID"));
                    delegated.add(delegate);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(DelegatedDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return delegated;
    }

    /**
     * Get delegated for specific initiative
     *
     * @param initiativeID
     * @param delegatorID
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Delegated> getDelegated(int initiativeID, String delegatorID) throws ClassNotFoundException {
        List<Delegated> delegated = new ArrayList<>();

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("SELECT * FROM delegated WHERE ")
                        .append(" initiativeID = ").append("'").append(initiativeID).append("'")
                        .append(" AND delegatorID = ").append("'").append(delegatorID).append("';");

                stmt.execute(insQuery.toString());

                ResultSet res = stmt.getResultSet();

                while (res.next() == true) {
                    Delegated delegate = new Delegated();
                    delegate.setID(res.getInt("ID"));
                    delegate.setUserName(res.getString("userID"));
                    delegate.setInitiativeID(res.getInt("initiativeID"));
                    delegate.setDelegator(res.getString("delegatorID"));
                    delegated.add(delegate);
                }

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(DelegatedDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return delegated;
    }


    /**
     * Establish a database connection and add the delegate into the database.
     *
     * @param delegate
     * @throws ClassNotFoundException
     */
    public static void addDelegated(Delegated delegate) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            delegate.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(DelegatedDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("INSERT INTO ")
                        .append(" delegated (initiativeID, userID, delegatorID) ")
                        .append(" VALUES (")
                        .append("'").append(delegate.getInitiativeID()).append("',")
                        .append("'").append(delegate.getUserName()).append("',")
                        .append("'").append(delegate.getDelegator()).append("');");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The delegate was successfully added in the database.");

                // Close connection
                stmt.close();
                con.close();

            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(DelegatedDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates information for specific delegate
     *
     * @param delegate
     * @throws ClassNotFoundException
     */
    public static void updateDelegated(Delegated delegate) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            delegate.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(DelegatedDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("UPDATE delegated ")
                        .append(" SET ")
                        .append(" DELEGATORID = ").append("'").append(delegate.getDelegator()).append("'")
                        .append(" WHERE initiativeID = ").append("'").append(delegate.getInitiativeID()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The delegate was successfully updated in the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(DelegatedDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete specific delegate
     *
     * @param delegate
     * @throws ClassNotFoundException
     */
    public static void deleteDelegated(Delegated delegate) throws ClassNotFoundException {

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM delegated ")
                        .append(" WHERE ")
                        .append(" ID = ").append("'").append(delegate.getID()).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The delegate was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(DelegatedDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete specific delegate
     *
     * @param id
     * @throws ClassNotFoundException
     */
    public static void deleteDelegated(int id) throws ClassNotFoundException {

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM delegated ")
                        .append(" WHERE ")
                        .append(" ID = ").append("'").append(id).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The delegate was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(DelegatedDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
