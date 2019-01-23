/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemzapisowy.entity;
import databaseAccess.ConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import systemzapisowy.Application.*;
import systemzapisowy.Factory;
/**
 *
 * @author adrianna
 */
public class StudyGroup {
    
    private String groupCode;
    private int currentNumberOfOccupiedPlaces;
    private String lecturer;
    private int maxNumberOfPlaces;
    private Course course;
    private int hour;
    private String day;
    private ArrayList<Enrollment> enrollment;

    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    
    public StudyGroup() {
        enrollment = new ArrayList();
        con = ConnectionManager.getConnection();
    }
    
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public int getCurrentNumberOfOccupiedPlaces() {
        return currentNumberOfOccupiedPlaces;
    }

    public void setCurrentNumberOfOccupiedPlaces(int currentNumberOfOccupiedPlaces) {
        this.currentNumberOfOccupiedPlaces = currentNumberOfOccupiedPlaces;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public int getMaxNumberOfPlaces() {
        return maxNumberOfPlaces;
    }

    public void setMaxNumberOfPlaces(int maxNumberOfPlaces) {
        this.maxNumberOfPlaces = maxNumberOfPlaces;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<Enrollment> getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(ArrayList<Enrollment> enrollment) {
        this.enrollment = enrollment;
    }
    
      @Override
    public boolean equals(Object obj) {
        if (((StudyGroup) obj).getGroupCode().equals(getGroupCode())) {
            return true;
        }
        return false;
    }

    public void writeOutStudent() {
        for (int i = 0; i < enrollment.size(); i++) {
            enrollment.get(i).getStudent().removeGroup(this.groupCode);
        }
    } 
    
    public boolean checkPlaces() {
        if (currentNumberOfOccupiedPlaces < maxNumberOfPlaces) {
            return true;
        }
        return false;
     }
    
    
    
      public boolean enrollStudent(Student student) {
        if (!checkPlaces() || !student.enrollToGroup(this)) {
            return false;
        }
        Factory factory = new Factory();
        Enrollment enroll = factory.createEnrollment(student, this);
        addEnrollmentToList(enroll);
        extendNumberOfOccupiedPlaces();
        return true;
    }

    public void extendNumberOfOccupiedPlaces() {
        if (currentNumberOfOccupiedPlaces < maxNumberOfPlaces) {
            this.currentNumberOfOccupiedPlaces++;
        }
    }

    public void addEnrollmentToList(Enrollment enroll) {
        int idx;
        if ((idx = enrollment.indexOf(enroll)) == -1) {
            enrollment.add(enroll);
        }
}
   
    
}
