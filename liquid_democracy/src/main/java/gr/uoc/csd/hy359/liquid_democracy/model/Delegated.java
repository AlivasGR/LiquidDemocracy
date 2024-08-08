/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoc.csd.hy359.liquid_democracy.model;

/**
 *
 * @author papadako
 */
public class Delegated {
    private String userName;    // (unique)
    private int ID;
    private int initiativeID;
    private String delegatorID;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ratingID) {
        this.ID = ratingID;
    }

    public int getInitiativeID() {
        return initiativeID;
    }

    public void setInitiativeID(int initiativeID) {
        this.initiativeID = initiativeID;
    }

    public String getDelegator() {
        return delegatorID;
    }

    public void setDelegator(String delegatorID) {
        this.delegatorID = delegatorID;
    }

    /**
     * Method that checks that all mandatory fields are set
     *
     * @throws Exception
     */
    public void checkFields() throws Exception {
        // Check that everything is ok
        if ((userName == null || userName.trim().isEmpty())
                || (delegatorID == null || delegatorID.trim().isEmpty())
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
                .append("Delegator ID: ").append(this.delegatorID).append("\n");

        return sb.toString();

    }

}
