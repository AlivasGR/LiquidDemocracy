/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoc.csd.hy359.liquid_democracy.db;

import gr.uoc.csd.hy359.liquid_democracy.model.Initiative;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author papadako
 */
public class InitiativeDB {

    /**
     * Get all initiatives
     *
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Initiative> getAllInitiatives() throws ClassNotFoundException {
        List<Initiative> initiatives = new ArrayList<>();

        try {
            Connection con = CS359DB.getConnection();
            Statement stmt = con.createStatement();

            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM initiatives;");

            stmt.execute(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            while (res.next() == true) {
                Initiative initiative = new Initiative();
                initiative.setId(res.getInt("ID"));
                initiative.setCreator(res.getString("creatorID"));
                initiative.setTitle(res.getString("title"));
                initiative.setCategory(res.getString("category"));
                initiative.setDescription(res.getString("description"));
                initiative.setStatus(res.getInt("status"));
                initiative.setCreated(res.getTimestamp("created"));
                initiative.setModified(res.getTimestamp("modified"));
                initiative.setExpires(res.getTimestamp("expires"));
                initiatives.add(initiative);
            }

            // Close connection
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(InitiativeDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return initiatives;
    }

    /**
     * Get initiatives of user
     *
     * @param user
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Initiative> getInitiatives(String user) throws ClassNotFoundException {
        List<Initiative> initiatives = new ArrayList<>();

        try {
            Connection con = CS359DB.getConnection();
            Statement stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM initiatives ")
                    .append(" WHERE ")
                    .append(" CREATORID = ").append("'").append(user).append("';");

            stmt.execute(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            while (res.next() == true) {
                Initiative initiative = new Initiative();
                initiative.setId(res.getInt("ID"));
                initiative.setCreator(res.getString("creatorID"));
                initiative.setTitle(res.getString("title"));
                initiative.setCategory(res.getString("category"));
                initiative.setDescription(res.getString("description"));
                initiative.setStatus(res.getInt("status"));
                initiative.setCreated(res.getTimestamp("created"));
                initiative.setModified(res.getTimestamp("modified"));
                initiative.setExpires(res.getTimestamp("expires"));
                initiatives.add(initiative);
            }

            // Close connection
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(InitiativeDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return initiatives;
    }

    /**
     * Get initiatives with status
     *
     * @param status
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Initiative> getInitiativesWithStatus(int status) throws ClassNotFoundException {
        List<Initiative> initiatives = new ArrayList<>();

        try {
            Connection con = CS359DB.getConnection();
            Statement stmt = con.createStatement();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM initiatives ")
                    .append(" WHERE ")
                    .append(" STATUS = ").append("'").append(status).append("';");

            stmt.execute(insQuery.toString());

            ResultSet res = stmt.getResultSet();

            while (res.next() == true) {
                Initiative initiative = new Initiative();
                initiative.setId(res.getInt("ID"));
                initiative.setCreator(res.getString("creatorID"));
                initiative.setTitle(res.getString("title"));
                initiative.setCategory(res.getString("category"));
                initiative.setDescription(res.getString("description"));
                initiative.setStatus(res.getInt("status"));
                initiative.setCreated(res.getTimestamp("created"));
                initiative.setModified(res.getTimestamp("modified"));
                initiative.setExpires(res.getTimestamp("expires"));
                initiatives.add(initiative);
            }

            // Close connection
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(InitiativeDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return initiatives;
    }

    /**
     * Establish a database connection and add the member in the database.
     *
     * @param initiative
     * @throws ClassNotFoundException
     */
    public static void addInitiative(Initiative initiative) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            initiative.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(InitiativeDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = CS359DB.getConnection();
            StringBuilder insQuery = new StringBuilder();

            Timestamp expireDate = null;
            if (initiative.getExpires() != null) {
                expireDate = new Timestamp(initiative.getExpires().getTime());
            }
            insQuery.append("INSERT INTO ")
                    .append(" initiatives (CREATORID, TITLE, CATEGORY, DESCRIPTION, ");
            if (expireDate != null) {
                insQuery.append("EXPIRES, ");
            }
            insQuery.append("STATUS) ")
                    .append(" VALUES (")
                    .append("'").append(initiative.getCreator()).append("',")
                    .append("'").append(initiative.getTitle()).append("',")
                    .append("'").append(initiative.getCategory()).append("',")
                    .append("'").append(initiative.getDescription()).append("',");

            // If we have added an expire date
            if (expireDate != null) {
                insQuery.append("'").append(expireDate).append("',");
            }
            insQuery.append("'").append(initiative.getStatus()).append("');");

            String generatedColumns[] = {"ID"};
            PreparedStatement stmtIns = con.prepareStatement(insQuery.toString(), generatedColumns);
            stmtIns.executeUpdate();

            // Get information magically completed from database
            ResultSet rs = stmtIns.getGeneratedKeys();
            if (rs.next()) {
                // Update values based on database
                int id = rs.getInt(1);
                initiative.setId(id);
                Initiative inserted = getInitiative(id);
                initiative.setCreated(inserted.getCreated());
                initiative.setModified(inserted.getModified());
            }

            System.out.println("#DB: The initiative was successfully added in the database.");

            // Close connection
            stmtIns.close();
            con.close();
        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(InitiativeDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Establish a database connection and get initiative with id
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     */
    public static Initiative getInitiative(int id) throws ClassNotFoundException {
        Initiative initiative = new Initiative();
        try {
            Connection con = CS359DB.getConnection();
            StringBuilder insQuery = new StringBuilder();

            insQuery.append("SELECT * FROM initiatives ")
                    .append(" WHERE ")
                    .append(" ID = ").append("'").append(id).append("';");

            String generatedColumns[] = {"ID"};
            PreparedStatement stmtIns = con.prepareStatement(insQuery.toString(), generatedColumns);
            stmtIns.executeUpdate();

            ResultSet res = stmtIns.getResultSet();

            if (res.next() == true) {
                initiative.setId(res.getInt("ID"));
                initiative.setCreator(res.getString("creatorID"));
                initiative.setTitle(res.getString("title"));
                initiative.setCategory(res.getString("category"));
                initiative.setDescription(res.getString("description"));
                initiative.setStatus(res.getInt("status"));
                initiative.setCreated(res.getTimestamp("created"));
                initiative.setModified(res.getTimestamp("modified"));
                initiative.setExpires(res.getTimestamp("expires"));
            }

            System.out.println("#DB: The initiative was successfully retrieved from the database.");

            // Close connection
            stmtIns.close();
            con.close();
        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(InitiativeDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return initiative;
    }

    /**
     * Updates information for specific initiative
     *
     * @param initiative
     * @throws ClassNotFoundException
     */
    public static void updateInitiative(Initiative initiative) throws ClassNotFoundException {
        // Check that we have all we need
        try {
            initiative.checkFields();

        } catch (Exception ex) {
            // Log exception
            Logger.getLogger(InitiativeDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = CS359DB.getConnection();
            StringBuilder insQuery = new StringBuilder();

            Timestamp expireDate = null;
            if (initiative.getExpires() != null) {
                expireDate = new Timestamp(initiative.getExpires().getTime());
            }

            insQuery.append("UPDATE initiatives ")
                    .append(" SET ")
                    .append(" TITLE = ").append("'").append(initiative.getTitle()).append("',")
                    .append(" CATEGORY = ").append("'").append(initiative.getCategory()).append("',")
                    .append(" DESCRIPTION = ").append("'").append(initiative.getDescription()).append("',");
            // If we have added an expire date
            if (expireDate != null) {
                insQuery.append(" EXPIRES = ").append("'").append(expireDate).append("',");
            }
            insQuery.append(" STATUS = ").append("'").append(initiative.getStatus()).append("'")
                    .append(" WHERE ID = ").append("'").append(initiative.getId()).append("';");

            PreparedStatement stmtUpdate = con.prepareStatement(insQuery.toString());

            stmtUpdate.executeUpdate();

            // Update info
            Initiative updated = getInitiative(initiative.getId());
            initiative.setCreated(updated.getCreated());
            initiative.setModified(updated.getModified());

            System.out.println("#DB: The initiative was successfully updated in the database.");

            // Close connection
            stmtUpdate.close();
            con.close();
        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(InitiativeDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete initiative with id
     *
     * @param id
     * @throws ClassNotFoundException
     */
    public static void deleteInitiative(int id) throws ClassNotFoundException {

        try {
            try (Connection con = CS359DB.getConnection();
                    Statement stmt = con.createStatement()) {

                StringBuilder insQuery = new StringBuilder();

                insQuery.append("DELETE FROM initiatives ")
                        .append(" WHERE ")
                        .append(" id = ").append("'").append(id).append("';");

                stmt.executeUpdate(insQuery.toString());
                System.out.println("#DB: The initiative was successfully deleted from the database.");

                // Close connection
                stmt.close();
                con.close();
            }

        } catch (SQLException ex) {
            // Log exception
            Logger.getLogger(InitiativeDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Checks every active initiative if it has expired
     *
     */
    public static void applyExpiration() {
        //get all active
        try {
            List<Initiative> initiatives = InitiativeDB.getInitiativesWithStatus(1);
            Calendar today = Calendar.getInstance();

            for (int i = 0; i < initiatives.size(); i++) {
                Calendar expire = Calendar.getInstance();
                expire.setTime(initiatives.get(i).getExpires());
                //check each active initiave if it has expired
                if (expire.before(today)) {
                    Initiative init = initiatives.get(i);
                    init.setStatus(2); //expire initiative
                    InitiativeDB.updateInitiative(init); //update DB
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
