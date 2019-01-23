/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemzapisowy.entity;

import java.util.ArrayList;
import systemzapisowy.Factory;

/**
 *
 * @author adrianna
 */
public class Student extends User {
    private String indexNumber;
    private String fieldOfStudy;
    private String password;
    private ArrayList<Enrollment> enrollments;

    public Student() {
        enrollments = new ArrayList();
}

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(ArrayList<Enrollment> enrollment) {
        this.enrollments = enrollment;
    }
    
    
    public boolean checkTerm(String day, int hour) {
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getStudyGroup().getHour() == hour && enrollments.get(i).getStudyGroup().getDay().equals(day)) {
                return false;
            }
        }
        return true;
    }

    void removeGroup(String groupCode) {
        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getStudyGroup().getGroupCode() == groupCode) {
                enrollments.remove(i);
            }
        }
    }

    public boolean weryfikujDane(String numerIndeksu, String haslo) {
        // TODO - implement Student.weryfikujDane
        throw new UnsupportedOperationException();
    }
    
    public void addEnrollmentToList(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    
    public boolean checkRightToEnroll(StudyGroup group) {
        String courseCode = group.getCourse().getCourseCode();

        for (int i = 0; i < enrollments.size(); i++) {
            if (enrollments.get(i).getStudyGroup().getCourse().getCourseCode().equals(courseCode)) {
                return false;
            }
        }
        return true;
    }

    boolean enrollToGroup(StudyGroup group) {
        if(checkRightToEnroll(group) && checkTerm(group.getDay(), group.getHour())){
            Factory factory = new Factory();
            Enrollment enrollment = factory.createEnrollment(this, group);
            addEnrollmentToList(enrollment);
            return true;
        }
        return false;
}
    
      public boolean writeOutStudent(Student student){
        //TODO
        return false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(((Student)obj).getIndexNumber().equals(getIndexNumber()))
            return true;
        return false;        
}
}
