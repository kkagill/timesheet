package ca.bcit.comp3910.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.comp3910.access.TimesheetDBmanager;
/**
 * Timesheet storage.
 * 
 * @author She Jong Shon
 * @author Bruce Link 
 */
@SuppressWarnings("serial")
@ApplicationScoped
@Named("timesheet")
@FacesConverter(value = "timesheet")
public class Timesheet implements Serializable {
    
    /**
     * ArrayList that saves rows
     */
    private ArrayList<TimesheetRow> list = new ArrayList<TimesheetRow>();
    /** 
     * Map that saves week number as a key and timesheet as a value 
     */
    static private Map<Integer, Timesheet> map = new TreeMap<Integer, Timesheet>();
    /** 
     * Date that determines end week. 
     */
    private Date endWeek;
    /** 
     * Inject timesheetDBmanager bean  
     */
    @Inject TimesheetDBmanager tm;
    /**
     * The selection of weeks to be viewed
     */
    private String weektoView;
    /**
     * Week Ending of that Timesheet.
     */
    private String weekDate;
    /**
     * View or not to view Timesheet.
     */
    private boolean timesheetView;
    
    /** 
     * Constructor which initializes calendar variables.
     * It also adds 5 rows initially. 
     */
    public Timesheet() {
        list = new ArrayList<TimesheetRow>();
        for(int i = 0; i < 5; i++) {
        	list.add(new TimesheetRow());
        }
        
        Calendar c = new GregorianCalendar();
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        int leftDays = Calendar.FRIDAY - currentDay;
        c.add(Calendar.DATE, leftDays);
        endWeek = c.getTime();
    }
    
    /**
     * Get total for a week.
     * @return total the total of a week row.
     */
    public double getTotal() {
        double total = 0;
        for (TimesheetRow data : list) {
            total += data.getTotal();
        }
        
        return total;
    }
   
    /**
     * Get saturday totals .
     * @return total the total of saturday column.
     */
    public double getSatTotal() {        
        double total = 0;
        for (TimesheetRow data : list) {
            total += data.getSat();
        }
        
        return total;
    } 

    /**
     * Get sunday totals .
     * @return total the total of sunday column.
     */
    public double getSunTotal() {
        double total = 0;
        for (TimesheetRow data : list) {
            total += data.getSun();
        }
        
        return total;
    }
    
    /**
     * Get monday totals .
     * @return total the total of monday column.
     */
    public double getMonTotal() {
        double total = 0;
        for (TimesheetRow data : list) {
            total += data.getMon();
        }
        
        return total;
    }
    
    /**
     * Get tuesday totals .
     * @return total the total of tuesday column.
     */
    public double getTueTotal() {
        double total = 0;
        for (TimesheetRow data : list) {
            total += data.getTue();
        }
        
        return total;
    }
    
    /**
     * Get wednesday totals .
     * @return total the total of wednesday column.
     */
    public double getWedTotal() {
        double total = 0;
        for (TimesheetRow data : list) {
            total += data.getWed();
        }
        
        return total;
    }
    
    /**
     * Get thursday totals .
     * @return total the total of thursday column.
     */
    public double getThuTotal() {
        double total = 0;
        for (TimesheetRow data : list) {
            total += data.getThu();
        }
        
        return total;
    }
    
    /**
     * Get friday totals .
     * @return total the total of friday column.
     */
    public double getFriTotal() {
        double total = 0;
        for (TimesheetRow data : list) {
            total += data.getFri();
        }
        
        return total;
    }
    
    /**
     * @param end the endWeek. Must be a Friday
     */
    private void checkFriday(Date end) {
        Calendar c = new GregorianCalendar();
        c.setTime(end);
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        if (currentDay != Calendar.FRIDAY) {
            throw new IllegalArgumentException("EndWeek must be a Friday");
        }
    }
    
    /**
     * @return the endWeek
     */
    public Date getEndWeek() {
        return endWeek;
    }

    /**
     * @param end the endWeek to set. Must be a Friday
     */
    public void setEndWeek(Date end) {
        checkFriday(end);
        endWeek = end;
    }

    /**
     * @return the weeknumber of the timesheet
     */
    public int getWeekNumber() {
        Calendar c = new GregorianCalendar();
        c.setTime(endWeek);
        c.setFirstDayOfWeek(Calendar.SATURDAY);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Sets the end of week based on the week number.
     * @param weekNo the week number of the timesheet week
     * @param weekYear the year of the timesheet
     */
    public void setWeekNumber(int weekNo, int weekYear) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SATURDAY);
        c.setTime(endWeek);
        c.setWeekDate(weekYear, weekNo, Calendar.FRIDAY);
        endWeek = c.getTime();
    }

    /**
     * @return the endWeek as string
     */
    public String getWeekEnding() {
        Calendar c = new GregorianCalendar();
        c.setTime(endWeek);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        month += 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }
    
    //---------------------------------------------------
    
    /**
     * Gets rows from timesheetrow array, to generate the dataTable
     * 
     * @return timesheetrow object
     */
    public ArrayList<TimesheetRow> getList() {
        list = tm.getRowList();
        return list;
    }
    
    /**
     * Set rows
     * 
     * @param rows
     */
    public void setList(ArrayList<TimesheetRow> rows) {
        this.list = rows;
    }
    
    /**
     * Getter for map.
     * @return map the key and value of this map.
     */
    public static Map<Integer, Timesheet> getMap() { 
        return map; 
    }
    
    /**
     * Setter for map.    
     */
    public static void setMap(Map<Integer, Timesheet> map) { 
        Timesheet.map = map; 
    }
    
    /**
     * Boolean for viewing timesheet.
     * @return timesheetView
     */
    public boolean timesheetView() { 
        return timesheetView; 
    }
    
    /**
     * Setter for viewing timesheet.
     * @param timesheetView
     */
    public void setTimesheetView(boolean timesheetView) {
        this.timesheetView = timesheetView; 
    }
    
    /**
     * Setter for viewing week in the select menu.
     * @param weekNum
     */
    public void setViewWeek(String weekNum){ 
        this.weektoView = weekNum; 
    }
    
    /**
     * Getter for viewing week in the select menu.
     * @return weektoView
     */
    public String getViewWeek() { 
        return weektoView; 
    }
    
    /**
     * Directs to the selected week's timesheet table.
     */
    public void viewTimesheet() {
    	setTimesheetView(true);
    }
    /**
     * week ending date for the current timetable.
     *      
     * @return weekDate
     */
    public String getWeekEnd() {
        if (timesheetView() == false) {           
            weekDate = getWeekEnding();
        } else {
            weekDate = getViewWeek();
        }
        return weekDate;
    }

    //------------------------------------------------------------------------------------------
    
    /**
     * Adds row in the datatable
     */
    public void addRow() {
        TimesheetRow row = new TimesheetRow();
        list.add(row);
    }     
    
    /**
     * user can save the timesheet.  
     * @return null to stay at the same page.
     */
    public String save() {  
        tm.persist(list);
        list.clear();
        return null;
    }    

    /**
     * Removes the selected row in the array.
     * @return null to stay at the same page.
     */
    public String removeRow(TimesheetRow timesheetRowToDelete) {
        list.remove(timesheetRowToDelete);
        return null;
    }
}