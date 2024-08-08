/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoc.csd.hy359.liquid_democracy.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author papadako
 */
public class Comment {

    private String userName;    // (unique)
    private int initiativeID;
    private String comment;
    private Date created;
    private Date modified;
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int commentID) {
        this.ID = commentID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getInitiativeID() {
        return initiativeID;
    }

    public void setInitiativeID(int initiativeID) {
        this.initiativeID = initiativeID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreatedAsString() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdfDate.format(created);
    }

    public void setCreated(Date timestamp) {
        this.created = timestamp;
    }

    public Date getModified() {
        return modified;
    }

    public String getModifiedAsString() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdfDate.format(modified);
    }

    public void setModified(Date timestamp) {
        this.modified = timestamp;
    }

    /**
     * Method that checks that all mandatory fields are set
     *
     * @throws Exception
     */
    public void checkFields() throws Exception {
        // Check that everything is ok
        if ((userName == null || userName.trim().isEmpty())
                || (comment == null || comment.trim().isEmpty())
                || (initiativeID < 0)) {
            throw new Exception("Missing fields!");  // Something went wrong with the fields
        }
    }

    /**
     * Returns a string representation of this object (use it only
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(this.ID).append("\n")
                .append("User: ").append(this.userName).append("\n")
                .append("InitiativeID: ").append(this.initiativeID).append("\n")
                .append("Comment: ").append(this.comment).append("\n");
        if (created != null) {
            sb.append("Created: ").append(this.getCreatedAsString()).append("\n");
        }
        if (modified != null) {
            sb.append("Last Modified: ").append(this.getModifiedAsString()).append("\n");
        }

        return sb.toString();

    }


}
