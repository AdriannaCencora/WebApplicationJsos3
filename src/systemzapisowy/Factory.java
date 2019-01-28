/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemzapisowy;

import java.util.IllegalFormatCodePointException;
import systemzapisowy.entity.Course;
import systemzapisowy.entity.Enrollment;
import systemzapisowy.entity.Student;
import systemzapisowy.entity.StudyGroup;
import systemzapisowy.entity.User;

/**
 *
 * @author adrianna
 */
public class Factory {

    
    public Student createStudent(String[] data) throws IllegalFormatCodePointException {
        if (data == null) {
            return null;
        }
        Student student = new Student();
        student.setID(data[0]);
        student.setName(data[1]);
        student.setSurname(data[2]);

        student.setBirthDate(data[3]);
        student.setIndexNumber(data[4]);
        student.setFieldOfStudy(data[5]);
        student.setSemester(data[6]);

        return student;

    }
    
    public User  createUser(String[] data) {
        User user = new User();
        user.setID(data[0]);
        user.setName(data[1]);
        user.setSurname(data[2]);
        user.setBirthDate(data[3]);

        return user;
    }
    
    
    public Course createCourse(String[] data) {
        Course course = new Course();
        course.setCourseCode(data[0]);
        course.setName(data[1]);
        course.setType(data[2]);
        course.setFieldOfStudy(data[3]);
        course.setSemester(Integer.parseInt(data[4]));
        return course;
    }

    public StudyGroup createStudyGroup(String[] data) {
        StudyGroup group = new StudyGroup();
        group.setGroupCode(data[0]);
        group.setLecturer(data[1]);
        group.setMaxNumberOfPlaces(Integer.parseInt(data[2]));
        group.setHour(data[3]);
        group.setDay(data[4]);
        return group;
    }

    public Enrollment createEnrollment(Student student, StudyGroup group) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setStudyGroup(group);
        student.addEnrollmentToList(enrollment);
        group.addEnrollmentToList(enrollment);
        return enrollment;
    }
}
