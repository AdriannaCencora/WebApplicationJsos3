/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemzapisowy.entity;

/**
 *
 * @author adrianna
 */
public class Enrollment {
       private Student student;
       private StudyGroup studyGroup;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }
    
        @Override
    public boolean equals(Object obj) {
        if (((Enrollment) obj).studyGroup.equals(getStudyGroup()) && ((Enrollment) obj).student.equals(getStudent())) {
            return true;
        }
        return false;
}
    
}
