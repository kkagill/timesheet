package ca.bcit.comp3910.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import ca.bcit.comp3910.model.Timesheet;
import ca.bcit.comp3910.model.TimesheetRow;

@ApplicationScoped
@Named ("timesheetManager")
/**
 * 
 * @author sejong
 * @version 1.0
 *
 */
public class TimesheetDBmanager {
	/** dataSource for connection pool on JBoss AS 7 or higher. */
    @Resource(mappedName = "java:/jboss/datasources/inventory")    
    private DataSource dataSource;
    
    /** Timesheet injection **/
    @Inject private Timesheet timesheet;
    /** EmployeeManager injection **/
    @Inject private EmployeeDBmanager employeeManager;    
    /** Timesheetrow arraylist **/
    private ArrayList<TimesheetRow> rowList = new ArrayList<TimesheetRow>();
    /** TimesheetMap map **/
    private Map<String, ArrayList<TimesheetRow>> timesheetMap = 
    		new TreeMap<String, ArrayList<TimesheetRow>>();
    
    /**
     * Adding rows initially.
     * @param empID the employee number that is suppose to be on that timesheet
     */
    public void onCreate(int empID) {
        employeeManager.setEmployee(empID);
        find();
        if (timesheetMap.containsKey(timesheet.getWeekEnd()) == false) {
            ArrayList<TimesheetRow> perRow = new ArrayList<TimesheetRow>();
            for(int i = 0; i < 5; i++) {
            	perRow.add(new TimesheetRow());
            }
            timesheetMap.put(timesheet.getWeekEnd(), perRow);
        }
        else {
        	for(int i = 0; i < 5; i++) {
        		timesheetMap.get(timesheet.getWeekEnd()).add(new TimesheetRow());
            }
        }
    }
    
    /**
     * add timesheet row.
     * @param r the row to be added
     * @return null
     */
    public String addTimesheetRow(TimesheetRow r) {
    	rowList.add(r);
        return null;
    }

    /**
     * delete timesheet row.
     * @param r the row to be deleted
     * @return null
     */
    public boolean deleteTimesheetRow(TimesheetRow r) {
    	rowList.remove(r);
        return true;
    }

    /**
     * the record to be persisted.
     * @param list
     */
    public void persist(ArrayList<TimesheetRow> list) {
        int empID = employeeManager.getEmployee().getEmpID();
        PreparedStatement statement = null;
        Connection connection = null;
        try {
        	connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM timesheet WHERE EmpID = ? AND WeekDate = ?"); 
            statement.setInt(1, empID);
            statement.setString(2, timesheet.getWeekEnd());
            statement.executeUpdate();
            try {
                for (TimesheetRow tempList : list) {
                	if (tempList.getProjectID() == ""|| tempList.getWorkPackage() == "") {
                		continue;
                    }
                    statement = connection.prepareStatement("INSERT INTO timesheet VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    statement.setInt(1, empID);
                    statement.setString(2, timesheet.getWeekEnd());
                    statement.setString(3, tempList.getProjectID());
                    statement.setString(4, tempList.getWorkPackage());
                    statement.setDouble(5, tempList.getSat());
                    statement.setDouble(6, tempList.getSun());
                    statement.setDouble(7, tempList.getMon());
                    statement.setDouble(8, tempList.getTue());
                    statement.setDouble(9, tempList.getWed());
                    statement.setDouble(10, tempList.getThu());
                    statement.setDouble(11, tempList.getFri());
                    statement.setString(12, tempList.getNotes());
                    statement.executeUpdate();
                    /*
                    tempList.setProjectID(null);
                    tempList.setWorkPackage(null);
                    tempList.setSat(0.0);
                    tempList.setSun(0.0);
                    tempList.setMon(0.0);
                    tempList.setTue(0.0);
                    tempList.setWed(0.0);
                    tempList.setThu(0.0);
                    tempList.setFri(0.0);
                    tempList.setNotes(null);
                    */
                }
            } finally {
                if (statement != null) {
                	statement.close();
                }
            }
        } catch (SQLException e) {
                        e.printStackTrace();
        } finally {
        	if (connection != null) {
        		try {
        			connection.close();
                } catch (SQLException e) {
                	e.printStackTrace();
                }
        	}
        }
    }
    
    /**
     * find inventory record from database.
     */
    public void find() {
        timesheetMap.clear();
        int empID = employeeManager.getEmployee().getEmpID();
        PreparedStatement statement = null;
        Connection connection = null;
        try {
        	connection = dataSource.getConnection();
        	statement = connection.prepareStatement("SELECT * FROM timesheet WHERE EmpID = ?");
        	statement.setInt(1, empID);
            try {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                	String weekDate = result.getString("WeekDate");
                    if (!timesheetMap.containsKey(weekDate)) {
                    	timesheetMap.put(weekDate, new ArrayList<TimesheetRow>());
                    }
                    String projectName = result.getString("Project");
                    String wP = result.getString("WP");
                    double sat = result.getDouble("Sat");
                    double sun = result.getDouble("Sun");
                    double mon = result.getDouble("Mon");
                    double tue = result.getDouble("Tue");
                    double wed = result.getDouble("Wed");
                    double thu = result.getDouble("Thu");
                    double fri = result.getDouble("Fri");
                    String notes = result.getString("Notes");
                    TimesheetRow tempRow = new TimesheetRow(projectName, wP, sat, sun, mon, tue, wed, thu, fri, notes);
                    timesheetMap.get(weekDate).add(tempRow);
                }
            } finally {
                if(statement != null) {
                	statement.close();
                }
            }
        } catch(SQLException e) {
        	e.printStackTrace();
        } finally {
        	if (connection != null) {
        		try {
                	connection.close();
                } catch (SQLException e) {
                	e.printStackTrace();
                }
        	}
        }
    }
    
    /**
     * Returns the rows in an ArrayList, of a timesheet
     * @return ArrayList of rows stored in a certain timesheet
     */
    public ArrayList<TimesheetRow> getRowList() {        
    	rowList = timesheetMap.get(timesheet.getWeekEnd());
        return rowList;
    }

    /**
     * Returning all the timesheets in an ArrayList
     * @return ArrayList of Weeks stored.
     */
    public ArrayList<String> getTimesheetWeeks() {
        ArrayList<String> temp = new ArrayList<String>();
        for (Entry<String, ArrayList<TimesheetRow>> entry : timesheetMap.entrySet()) {
            temp.add(entry.getKey());
        }
        return temp;
    } 
}