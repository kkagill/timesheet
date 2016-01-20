package ca.bcit.comp3910.access;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import ca.bcit.comp3910.model.Employee;

@SuppressWarnings("serial")
@Named("employeeManager")
@SessionScoped
public class EmployeeDBmanager implements Serializable {
	/**
	 * Link to the SQL location.
	 */
	@Resource (mappedName = "java:/jboss/datasources/inventory")
	/**
	 * The source of the database.
	 */
	private DataSource source;
	/**
	 * Injection of an employee.
	 */
	@Inject Employee employee;
	/**
	 * An instance of the current employee logged into the
	 * system.
	 */
	Employee current;
	/**
	 * A map that is used to store all the credentials.
	 */
	Map<Integer, String> passwordAuthentication = new TreeMap<Integer, String>();
	/**
	 * A map the is used to store all of the employees
	 * according to their employee number, in order.
	 */
	Map<Integer, Employee> storage = new TreeMap<Integer, Employee>();

	/**
	 * This function starts up when creation, which gets a connection
	 * and creates a statement ready to be sent to mySQL to get the list
	 * of information about employees.
	 */
	@PostConstruct
	public void onCreate() {
		//Clears the map in case of duplicates
		passwordAuthentication.clear();
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = source.getConnection();
			statement = connection.prepareStatement("SELECT * FROM users");
			try {
				ResultSet result = statement.executeQuery();
				while(result.next()) {
					int employeeID = result.getInt("EmpID");
					String employeePassword = result.getString("EmpPwd");
                    String employeeName = result.getString("EmpName");
                    boolean admin = result.getBoolean("Admin");
                    Employee employee = new Employee(employeeID, employeePassword, employeeName, admin);
                    //Retrieves from the SQL database and puts a credential into
                    //the credential container
                    passwordAuthentication.put(employeeID, employeePassword);
                    //Retrieves from the SQL database and puts an employee into
                    //the employee storage container.
                    storage.put(employeeID, employee);
				}
			} finally {
				if(statement != null) {
					statement.close();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * From the employee in the parameter the function takes those 
	 * information and adds a new employee in the mySQL database,
	 * after updating the credential containers and the employee
	 * containers, when there are no corresponding credentials and
	 * employee in those containers.
	 * @param employee
	 * @return
	 */
	public boolean createEmployee(Employee employee) {
		String password = employee.getPassword();
		int ID = employee.getEmpID();
		String name = employee.getEmpName();
		boolean admin = employee.getAdmin();
		if(passwordAuthentication.containsKey(ID) == false) {
			//Creates a credential in the credential storage container
			passwordAuthentication.put(ID, password);
			//Creates an employee in the employee storage container
			storage.put(ID, employee);
			PreparedStatement statement = null;
			Connection connection = null;
			try {
				try {
					connection = source.getConnection();
					try {
						statement = connection.prepareStatement("INSERT " +
								"INTO users VALUES (?,?,?,?)");
						statement.setInt(1, ID);
						statement.setString(2, password);
						statement.setString(3, name);
						statement.setBoolean(4, admin);
						statement.executeUpdate();
					} finally {
						if(statement != null) {
							statement.close();
						}
					}
				} finally {
					if(connection != null) {
						connection.close();
					}
				}
			} catch(SQLException e2) {
				System.out.println("Error in EmployeeDBmanager.createEmployee();");
				System.out.println(statement.toString() + " was the statment being sent.");
				e2.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	/**
	 * This function updates the database of employees and
	 * also at the same moment updates the containers of
	 * the credentials storage and the employees storage.
	 * @param employee
	 * @return
	 */
	public boolean updateEmployee(Employee employee) {
		String password = employee.getPassword();
		int ID = employee.getEmpID();
		String name = employee.getEmpName();
		boolean admin = employee.getAdmin();
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			try {
				connection = source.getConnection();
				try {
					statement = connection.prepareStatement("UPDATE " +
							"users SET EmpID = ?, EmpPwd = ?, EmpName = ?, " + 
							"Admin = ? " + "WHERE EmpID = ?");
					statement.setInt(1, ID);
					statement.setString(2, password);
					statement.setString(3, name);
					statement.setBoolean(4, admin);
					statement.setInt(5, ID);
					statement.executeUpdate();
					//The credential storage container.
					passwordAuthentication.put(ID, password);
					//The employee storage container.
					storage.put(ID, employee);
				} finally {
					if(statement != null) {
						statement.close();
					}
				}
			} finally {
				if(connection != null) {
					connection.close();
				}
			}
		} catch(SQLException e3) {
			System.out.println("Error in EmployeeDBmanager.updateEmployee();");
			System.out.println(statement.toString() + " was the statment being sent.");
			e3.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Deletes the employee from the parameter from the database and also
	 * deletes the corresponding employee from the credential storage and
	 * employee storage containers.
	 * @param employee the employee to be deleted
	 * @return true if success in deleting, false when delete fails.
	 */
	public boolean deleteEmployee(Employee employee) {
		int ID = employee.getEmpID();
		PreparedStatement statement = null;
		Connection connection = null;
		//Admin account cannot be deleted
		if(employee.getAdmin()) {
			return false;
		}
		try {
			try {
				connection = source.getConnection();
				try {
					statement = connection.prepareStatement("DELETE FROM " +
							"users WHERE EmpID = ?");
					statement.setInt(1, ID);
					statement.executeUpdate();
					//The credentials storage container
					passwordAuthentication.remove(ID);
					//The employee storage container
					storage.remove(ID);
					return true;
				} finally {
					if(statement != null) {
						statement.close();
					}
				}
			} finally {
				if(connection != null) {
					connection.close();
				}
			}
		} catch(SQLException e4) {
			System.out.println("Error in EmployeeDBmanager.deleteEmployee();");
			System.out.println(statement.toString() + " was the statment being sent.");
			e4.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This function is used to authenticate the password with an employee
	 * ID.
	 * @param employeeID the employee's ID
	 * @param password the password begin checked
	 * @return whether the password has a corresponding user ID
	 */
	public boolean passwordAuthentication(int employeeID, String password) {
		if(passwordAuthentication.containsKey(employeeID)) {
			String correct = passwordAuthentication.get(employeeID);
			if(password.equals(correct)) {
				//setEmployee(employeeID);
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * The map of the credentials where the key is the employee ID.
	 * @return a map of the credentials
	 */
	public Map<Integer, String> getPasswordStorage() {
		return passwordAuthentication;
	}
	
	/**
	 * The map of all of the employees, with the employee ID as the
	 * key.
	 * @return a map of the employees
	 */
	public Map<Integer, Employee> getEmployeeStorage() {
		return storage;
	}
	
	/**
	 * Creates a list of employees from the map of employees.
	 * (Should be created on the onCreate, have time please change)
	 * @return an ArrayList of all of the employees
	 */
	public ArrayList<Employee> getListOfEmployee() {
		ArrayList<Employee> list = new ArrayList<Employee>();
		for(Entry<Integer, Employee> storage : storage.entrySet()) {
			list.add(storage.getValue());
		}
		return list;
	}
	
	/**
	 * Gets the current employee, that is logged in at the moment.
	 * @return the current employee 
	 */
	public Employee getEmployee() {
        return employee;
    }
	
	/**
	 * From current employee ID, gets the current employee's data
	 * and stores that employee as the current user.
	 * @param empID the employee ID of the current user.
	 */
	public void setEmployee(int employeeID) {
        employee = storage.get(employeeID);
        current = employee;
    }
	
	/**
	 * Check for whether an employee ID is an administrator
	 * @param employeeID
	 * @return true or false
	 */
	public boolean isAdmin(int employeeID) {
		Employee employee = storage.get(employeeID);
		if(employee.getAdmin()) {
			return true;
		}
		return false;
	}
}
