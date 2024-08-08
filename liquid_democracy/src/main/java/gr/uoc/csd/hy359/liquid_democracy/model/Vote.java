package gr.uoc.csd.hy359.liquid_democracy.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author papadako
 */
public class Vote implements Serializable {

    private int id;             // unique, set by db
    private String user;        // foreign key to username
    private String delegator;   // foreign key to username
    private boolean vote;       // 0 for user downvote, 1 for user upvote
    private boolean votedBy;    // 0 by delegator, 1 by user
    private int initiativeID;   // initiativeID
    private Date created;       // set by db
    private Date modified;      // set by db

    /**
     * Default Constructor
     *
     */
    public Vote() {
        this.id = -1;
        this.initiativeID = -1;
        this.user = "";
        this.delegator = "";
        this.vote = false;
        this.votedBy = false;

    }

    public Vote(String user, String delegator, int initiativeID, boolean vote, boolean votedBy) {
        this.id = -1;
        this.initiativeID = initiativeID;
        this.user = user;
        this.delegator = delegator;
        this.vote = vote;
        this.votedBy = votedBy;

    }

    /**
     * Method that checks that all mandatory fields are set
     *
     * @throws Exception
     */
    public void checkFields() throws Exception {
        // Check that everything is ok
        if ((user == null || user.trim().isEmpty())
                || (initiativeID == -1)) {
            throw new Exception("Missing fields!");  // Something went wrong with the fields
        }
    }

    /**
     * return true if voted by user, false if voted by delegator
     *
     * @return
     */
    public boolean isVotedBy() {
        return votedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInitiativeID() {
        return initiativeID;
    }

    public void setInitiativeID(int initiativeID) {
        this.initiativeID = initiativeID;
    }

    public String getCreatedAsString() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdfDate.format(created);
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public String getModifiedAsString() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdfDate.format(modified);
    }

    public void setModified(Date modified) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.modified = modified;
        sdfDate.format(modified);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDelegator() {
        return delegator;
    }

    public void setDelegator(String delegator) {
        this.delegator = delegator;
    }

    public int getVote() {
        if (vote) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     *
     * @param vote false for downvote, true for upvote
     * @param votedBy true for user, false for delegator
     */
    public void setVote(boolean vote, boolean votedBy) {
        // Do not allow delegators override user votes
        if (this.votedBy == true && votedBy == false) {
        } else {
            this.vote = vote;
            this.votedBy = votedBy;
        }
    }

    public String getVoteAsString() {
        if (vote) {
            return "UpVote";
        } else {
            return "DownVote";
        }
    }

    public int getVotedBy() {
        if (votedBy) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getVotedByAsString() {
        if (votedBy) {
            return "User";
        } else {
            return "Delegator";
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
        sb.append("ID: ").append(id).append("\n")
                .append("User: ").append(user).append("\n")
                .append("Delegator: ").append(delegator).append("\n")
                .append("InitiativeID: ").append(initiativeID).append("\n")
                .append("Vote: ").append(getVote()).append("\n")
                .append("Voted By: ").append(getVotedByAsString()).append("\n");
        if (created != null)
            sb.append("Created: ").append(this.getCreatedAsString()).append("\n");
        if (modified != null) {
            sb.append("Last Modified: ").append(this.getModifiedAsString()).append("\n");
        }

        return sb.toString();

    }

}
