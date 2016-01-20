package ca.bcit.comp3910;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.comp3910.access.EmployeeDBmanager;
import ca.bcit.comp3910.access.TimesheetDBmanager;
import ca.bcit.comp3910.model.Employee;

/**
 * 
 * @author kwokchung
 *
 */
@Named("storage")
@SessionScoped
public class EmployeeStorage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Injection of a timesheet.
	 */
	//@Inject private Timesheet sheet;
	/**
	 * Injection of a employeeDBmanager.
	 */
	@Inject private EmployeeDBmanager emManager;
	/**
	 * Injection of a timesheetDBmanager.
	 */
	@Inject private TimesheetDBmanager tsManager;
	/**
	 * Storage for employee.
	 */
	//private static Map<Integer, Employee> employeeStorage;
	/**
	 * Storage for credentials.
	 */
	//private static Map<Integer, String> credentialStorage;
	/**
	 * Input password.
	 */
	private String inPassword;
	/**
	 * Input user-name.
	 */
	private int inUserID;
	/**
	 * Input name field from user.
	 */
	private String inName;
	/**
	 * Input for whether is super user.
	 */
	private boolean inSuper;
	/**
	 * The constructor of this class.
	 */
	public EmployeeStorage() {
	}
	
	/**
	 * Getter for the input password.
	 * @return input password.
	 */
	public String getInPassword() {
    	return inPassword;
    }
	
	/**
	 * Setter for the input password.
	 * @param pw
	 */
    public void setInPassword(String pw) {
    	inPassword = pw;
    }
    
    /**
     * Getter for the input user-name.
     * @return input user-name.
     */
    public int getInUserID() {
		return inUserID;
	}
    
    /**
     * Setter for input user-name.
     * @param s
     */
    public void setInUserID(int num) {
    	inUserID = num;
    }
	
    /**
     * Getter for input name.
     * @return input name.
     */
    public String getInName() {
		return inName;
	}
    
    /**
     * Setter for the input name.
     * @param in
     */
    public void setInName(String in) {
    	inName = in;
    }
    
    /**
     * Getter for input super.
     * @return input name.
     */
    public boolean getInSuper() {
		return inSuper;
	}
    
    /**
     * Setter for the input super.
     * @param in
     */
    public void setInSuper(boolean in) {
    	inSuper = in;
    }
    
    /**
     * Getter for the employeeStorage.
     * @return array of employee storage.
     */
    public Map<Integer, Employee> getEmployeeStorage() {
		Map<Integer, Employee> employeeStorage 
			= emManager.getEmployeeStorage();
    	return employeeStorage;
	}
    
    /**
     * Getter for the credential storage.
     * @return array of the credential storage.
     */
    public Map<Integer, String> getCredentialStorage() {
    	Map<Integer, String> credentialStorage 
    		= emManager.getPasswordStorage();
    	return credentialStorage;
	}
	
	/**
	 * This method is used in the login page to verify if the 
	 * input user-name matches an existing credential.
	 * 
	 * @return the page of where to redirect.
	 */
	public String checkValidCredential() {
		if (emManager.passwordAuthentication(inUserID, inPassword)) {
            if (emManager.isAdmin(inUserID)) {
            	tsManager.onCreate(inUserID);
            	emManager.setEmployee(inUserID);
            	//sheet.setViewTS(false);
                return "admin";
            } else
            	tsManager.onCreate(inUserID);
            	//sheet.setViewTS(false);
            	emManager.setEmployee(inUserID);
            	return "user";
		} else
    	return null;
	
	/*
		String returnValue = null;
		Credentials temp;
		
		for (int i = 0; i < credentialStorage.size(); i++) {
			//for each loop compare the userName first, if there
			//is a same userName then compare password
			
			//If there is a userName but not the same password 
			//return a message saying wrong password
			
			//If there is no such userName found then return 
			//message saying wrong userName
			temp = credentialStorage.get(i);
			if (temp.getUserName().equals(loginCredit.getUserName())) {
				if (temp.getPassword().equals(loginCredit.getPassword())) {
					//success result, depending on the user type, the 
					//following code leads to the corresponding page
					if (temp.getUserName() == "admin") {
						currentEmpNum();
						returnValue = "adminLanding";
						break;
					}
					currentEmpNum();
					returnValue = "userLanding";
					break;
				} else {
					returnValue = "login";
					break;
				}
			}
		}
		return returnValue;
		*/
	}
	
    /**
     * Deletes the employee from the database in
     * the parameter.
     * 
     * @param d The employee to be deleted.
     * @return null.
     */
    public String delete(Employee e) {
    	if (emManager.deleteEmployee(e)) {
            return null;
        }
		return null;
	}
    
    /**
	 * Function to create a new user by the administrator.
	 * 
	 * @return the page of redirection.
	 */
    public String createUser() {
    	if (inUserID != 1 && inUserID != 0) {
    		emManager.createEmployee(new Employee(inUserID, inPassword, inName,inSuper));
    		inUserID = 0;
    		inPassword = null;
    		inName = null;
    		inSuper = false;
            return "viewUsers";
        }
    	inUserID = 0;
		inPassword = null;
		inName = null;
		inSuper = false;
        return null;
    	/*	
    	Employee updateE = new Employee();
		updateE.setAdmin(false);
		updateE.setEmpNumber(employeeStorage.size() + 1);
		updateE.setName(getInName());
		updateE.setUserName(getInUserName());
		employeeStorage.add(updateE);
		Credentials updateC = new Credentials();
		updateC.setUserName(getInUserName());
		updateC.setPassword(getInPassword());
		credentialStorage.add(updateC);
		
		inName = null;
		inUserName = null;
		inPassword = null;
		
		return "confirmCreate";
	*/
	}
	
    /**
     * Saves the employee in the database.
     * 
     * @return null.
     */
    public String saveEmployee(Employee e) {
    	if (emManager.updateEmployee(
    			new Employee(
    					e.getEmpID(), 
    					e.getPassword(), 
    					e.getEmpName(), 
    					false))) {
            return null;
        }
        return null;
    	/*
    	for (Employee e : employeeStorage) {
    		e.setEditable(false);
    	}
    	for (int i = 0; i < credentialStorage.size(); i++) {
    		Employee temp = new Employee();
    		temp = employeeStorage.get(i);
    		String a = temp.getUserID();
    		Credentials ctemp = new Credentials();
    		ctemp = credentialStorage.get(i);
    		ctemp.setUserName(a);
    	}
	    return null;
	    */
    }
    
    /**
     * Gets a list of all of the employees currently in the
     * database.
     * @return ArrayList of employees in the database.
     */
    public ArrayList<Employee> getList() {
    	ArrayList<Employee> list = emManager.getListOfEmployee();
    	return list;
    }
}
