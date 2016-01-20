package ca.bcit.comp3910.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Login Credentials.
 * @author Bruce Link edited by anthony ng
 */

@Named("credit")
@SessionScoped
public class Credentials implements Serializable {
    /** Version number. */
    private static final long serialVersionUID = 1L;
    /** The login ID. */
    private int userID;
    /** The password. */
    private String password;
    /** boolean for editing. */
	private boolean editable;
    
    /**
     * Empty constructor.
     */
    public Credentials() {
    	
    }
    
    /**
     * Credentials constructor.
     * 
     * @param userName the userName.
     * @param password the password.
     */
    public Credentials(final int userID, final String password) {
    	this.userID = userID;
    	this.password = password;
    }
    
    /**
     * @return the loginID
     */
    public int getUserID() {
        return userID;
    }
    
    /**
     * @param id the loginID to set
     */
    public void setUserID(final int id) {
    	userID = id;
    }
    
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * @param pw the password to set
     */
    public void setPassword(final String pw) {
        password = pw;
    }

    /**
     * @return getter for editing
     */
    public boolean getEditable() {
		return editable;
	}
    
    /**
     * @return setter for editing
     */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}

