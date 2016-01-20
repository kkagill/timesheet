package ca.bcit.comp3910.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * A class representing a single Employee.
 *
 * @author Bruce Link edited by Anthony Ng
 *
 */
@Named("employee")
@SessionScoped
public class Employee implements Serializable {
    
    /** Version number. */
    private static final long serialVersionUID = 1L;
    /** The employee's name. */
    private String name;
    /** The employee's employee number. */
    private int id;
    /** The employee's password. */
    private String password;   
    /** boolean for editing. */
    private boolean editable;
    /** boolean for admin. */
    private boolean admin;

    /**
     * Empty constructor
     */
    public Employee() {
    }

    /**
     * The argument-containing constructor. Used to create the initial employees
     * who have access as well as the administrator.
     *
     * @param id - employee id
     * @param password - password
     * @param name - employee name
     * @param admin - admin
     */   
    public Employee(int id, String password, String name, boolean admin) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.admin = admin;
    }
  
    /**
     * @return the id
     */
    public int getEmpID() { 
        return id; 
    }
    
    /**
     * @param id the empID to set
     */
    public void setEmpID(int id) { 
        this.id = id; 
    }
    
    /**
     * @return the password
     */
    public String getPassword() { 
        return password; 
    }
    
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password; 
    }
    
    /**
     * @return the name
     */
    public String getEmpName() { 
        return name; 
    }
    
    /**
     * @param name the empName to set
     */
    public void setEmpName(String name) { 
        this.name = name; 
    }
    
    /**     
     * @return admin
     */
    public boolean getAdmin() { 
        return admin; 
    }
    
    /**     
     * @param admin
     */
    public void setAdmin(boolean admin) { 
        this.admin = admin; 
    }
    
    /**     
     * @return editable
     */
    public boolean getEditable() {
        return editable; 
    }
    
    /**     
     * @param editable
     */
    public void setEditable(boolean editable) { 
        this.editable = editable; 
    } 
     
}