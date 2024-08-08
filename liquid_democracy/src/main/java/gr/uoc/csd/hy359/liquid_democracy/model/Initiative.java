package gr.uoc.csd.hy359.liquid_democracy.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author papadako
 */
public class Initiative implements Serializable {

    private int id;             // unique, set by db
    private String creator;     // foreign key to username
    private String category;    // 
    private String title;
    private String description;
    private int status;         // 0 for non-active, 1 active, 2 ended
    private Date created;       // set by db
    private Date modified;      // set by db
    private Date expires;      // set by db

    /**
     * Default Constructor
     *
     */
    public Initiative() {
        this.id = -1;
        this.creator = "";
        this.category = "";
        this.title = "";
        this.description = "";
        this.status = 0;    // default value is inactive

    }

    public Initiative(String creator, String category, String title, String description, int status) {
        this.id = -1;
        this.creator = creator;
        this.category = category;
        this.title = title;
        this.description = description;
        if (status == 0 || status == 1 || status == 2)
            this.status = status;
        else            // default value is inactive
            this.status = 0;

    }

    /**
     * Method that checks that all mandatory fields are set
     *
     * @throws Exception
     */
    public void checkFields() throws Exception {
        // Check that everything is ok
        if ((creator == null || creator.trim().isEmpty())
                || (category == null || category.trim().isEmpty())
                || (title == null || title.trim().isEmpty())
                || (description == null || description.trim().isEmpty())) {
            throw new Exception("Missing fields!");  // Something went wrong with the fields
        }

        // Something went wrong with status
        if (status != 0 && status != 1 && status != 2) {
            throw new Exception("Wrong status. Should be either 0, 1 or 2");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.modified = modified;
    }

    public Date getExpires() {
        return expires;
    }

    public String getExpiresAsString() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdfDate.format(expires);
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        if (status == 0 || status == 1 || status == 2) {
            this.status = status;
        }
    }

    public String getStatusAsString() {
        switch (status) {
            case 0:
                return "Not-active";
            case 1:
                return "Active";
            case 2:
                return "Ended";
            default:
                return "Invalid???";
        }
    }

    /**
     * Returns a string representation of this object
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n")
                .append("Creator: ").append(creator).append("\n")
                .append("Title: ").append(title).append("\n")
                .append("Category: ").append(category).append("\n")
                .append("Description: ").append(description).append("\n")
                .append("Status: ").append(getStatusAsString()).append("\n");

        if (created != null) {
            sb.append("Created: ").append(this.getCreatedAsString()).append("\n");
        }
        if (modified != null) {
            sb.append("Last Modified: ").append(this.getModifiedAsString()).append("\n");
        }
        if (expires != null) {
            switch (status) {
                case 0:
                    sb.append("Set to expire at: ").append(this.getExpiresAsString()).append("\n");
                    break;
                case 1:
                    sb.append("Will expire at: ").append(this.getExpiresAsString()).append("\n");
                    break;
                default:
                    sb.append("Expired at: ").append(this.getExpiresAsString()).append("\n");
                    break;
            }

        }
        return sb.toString();

    }

}
