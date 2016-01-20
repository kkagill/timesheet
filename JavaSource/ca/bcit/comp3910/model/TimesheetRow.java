package ca.bcit.comp3910.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * A class representing a single row of a Timesheet.
 *
 * @author She Jong Shon
 */
@SessionScoped
@Named("timesheetRow")
public class TimesheetRow implements Serializable {      
    /** boolean for editing. */
    private boolean editable; 
    /** project ID column. */
    private String projectID;
    /** work package column. */
    private String workPackage;
    /** Saturday column. */
    private double sat;
    /** Sunday column. */
    private double sun;
    /** Monday column. */
    private double mon;
    /** Tuesday column. */
    private double tue;
    /** Wednesday column. */
    private double wed;
    /** Thursday column. */
    private double thu;
    /** Friday column. */
    private double fri;  
    /** notes column. */
    private String notes;
    /** date that determines end week. */    
      
    /** Version number. */
    private static final long serialVersionUID = 2L;

    /**
     * Constructor.
     */
    public TimesheetRow() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates a TimesheetDetails object.
     * 
     * @param projectID the projectID
     * @param workPackage the workPackage
     * @param sat Saturday
     * @param sun Sunday
     * @param mon Monday
     * @param tues Tuesday
     * @param wed Wednesday
     * @param thu Thursday
     * @param fri Friday
     * @param notes notes
     */
     public TimesheetRow(String projectID, String workPackage, 
                         double sat, double sun, double mon, 
                         double tue, double wed, double thu, 
                         double fri, String notes) {
         this.projectID = projectID;
         this.workPackage = workPackage;       
         this.sat = sat;
         this.sun = sun;
         this.mon = mon;
         this.tue = tue;
         this.wed = wed;
         this.thu = thu;
         this.fri = fri;
         this.notes = notes;       
     }

     /**
      * getter boolean for editing.
      * 
      * @return editable 
      */
     public boolean getEditable() {
         return editable;
     }

     /**
      * setter boolean for editing.
      * @param editable
      */
     public void setEditable(boolean editable) {
         this.editable = editable;
     }

     /**
      * @return the projectID
      */
     public String getProjectID() {
         return projectID;
     }

     /**
      * @param id the projectID to set
      */
     public void setProjectID(String id) {
         this.projectID = id;
     }

     /**
      * @return the workPackage
      */
     public String getWorkPackage() {
         return workPackage;
     }

     /**
      * @param wp the workPackage to set
      */
     public void setWorkPackage(String wp) {
         this.workPackage = wp;
     }
     
     /**
      * @return the notes
      */
     public String getNotes() {
         return notes;
     }

     /**
      * @param comments the notes to set
      */
     public void setNotes(String comments) {
         this.notes = comments;
     }   

     /**
      * getter for saturday column.
      * @return sat
      */
     public double getSat() {
         return sat;
     }
     
     /**
      * setter for saturday column. 
      * @param sat    
      */
     public void setSat(double sat) {
         this.sat = sat;
     }
     
     /**
      * getter for sunday column.
      * @return sun
      */
     public double getSun() {
         return sun;
     }

     /**
      * setter for sunday column. 
      * @param sun    
      */
     public void setSun(double sun) {
         this.sun = sun;
     }

     /**
      * getter for monday column.
      * @return mon
      */
     public double getMon() {
         return mon;
     }

     public void setMon(double mon) {
         this.mon = mon;
     }

     /**
      * getter for thursday column.
      * @return thu
      */
     public double getThu() {
         return thu;
     }

     public void setThu(double thu) {
         this.thu = thu;
     }

     /**
      * getter for wednesday column.
      * @return wed
      */
     public double getWed() {
         return wed;
     }

     public void setWed(double wed) {
         this.wed = wed;
     }

     /**
      * getter for tuesday column.
      * @return tues
      */
     public double getTue() {
         return tue;
     }

     public void setTue(double tue) {
         this.tue = tue;
     }

     /**
      * getter for friday column.
      * @return fri
      */
     public double getFri() {
         return fri;
     }

     public void setFri(double fri) {
         this.fri = fri;
     }
     
     /**
      * getter for total column.
      * @return total hours for the days in the week.
      */
     public double getTotal() {
         return sat + sun + mon + tue + wed + thu + fri;
     }  
}