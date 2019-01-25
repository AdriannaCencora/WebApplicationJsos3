/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemzapisowy;

import databaseAccess.ConnectionMeneger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import systemzapisowy.entity.Course;
import systemzapisowy.entity.Student;
import systemzapisowy.entity.StudyGroup;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import systemzapisowy.entity.User;

/**
 *
 * @author adrianna
 */
public class Application {
private static Connection conn = null;
private static Statement stmt = null;
private static ResultSet rs = null;

private static PreparedStatement ps = null;

  private ArrayList<Course> courses;
    private ArrayList<Student> students;

    public Application() {
        courses = new ArrayList();
        students = new ArrayList();
    }

    public String addCourse(String[] data) {
        Factory fabryka = new Factory();
        Course course = fabryka.createCourse(data);
        Course foundCourse = findCourse(course);
        if (foundCourse == null) {
            courses.add(course);
            String info = course.toString();
            return info;
        }
        return null;
}
    
public String addUser(String[] data) {
        Factory factory = new Factory();
        User user = factory.createUser(data);
        // foundStudent = findStudent(student);
       // if (foundStudent == null) {
         
            addUserToDatabase(user);

           // String info = student.toString();
    //        return null;
      //  }
        return null;
    }
    
    
public String addUserToDatabase (String[] data) {
    
    
}
    
    
    
    
    
public String addStudent(String[] data) {
        Factory factory = new Factory();
        Student student = factory.createStudent(data);
        Student foundStudent = findStudent(student);
        if (foundStudent == null) {
         
            addStudentToDatabase(student);

           // String info = student.toString();
            return null;
        }
        return null;
    }


public void addStudentToDatabase(Student student) {
    
        System.out.println(student.toString());
    
            try {
            ps  = conn.prepareStatement("INSERT INTO Users (firstName, lastName, birthDate)"
                    + " VALUES (?, ?, ?)");
                       
            ps.setString(1, student.getName());
            ps.setString(2, student.getSurname());
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = null;
            try {
                System.out.println(student.getBirthDate());
                   parsed = format.parse(student.getBirthDate());
            }  catch (ParseException e1) {
                        e1.printStackTrace();
            }
            java.sql.Date sql = new java.sql.Date(parsed.getTime());
            ps.setDate(3, sql);
            
            ps.executeUpdate();
            ps.close();

                       
             ps  = conn.prepareStatement("INSERT INTO Students  VALUES (?, ?, ?, ?)");
             ps.setInt(1, Integer.parseInt(student.getIndexNumber()));
             
             String query1 = "SELECT userID from Users where= '" + student. + "' ";
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query1);
             
            if (rs.next()) {
                ps.setString(3, rs.getString("fieldID") );
            }
             
             
             
             String query = "SELECT fieldID from FieldsOfStudy WHERE fieldName= '" + student.getFieldOfStudy() + "' ";
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             
            if (rs.next()) {
                ps.setString(3, rs.getString("fieldID") );
            }
             ps.setInt(4, Integer.parseInt(student.getSemester()));

             ps.executeUpdate();
             ps.close();
             
        } catch (SQLException ex) {

        ex.printStackTrace();

    }
    
    
}

    public String addGroup(String[] data, Course course) {
        StudyGroup studyGroup = course.addGroup(data);
        if (studyGroup == null) {
            course.getGroups().add(studyGroup);
            String info = course.toString();
            return info;
        }
        return null;
    }

    public void removeCourse(String courseCode) {
        // TODO - implement Aplikacja.usunKurs
        throw new UnsupportedOperationException();
    }
    
     public Course findCourse(String courseCode) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseCode().equals(courseCode)) {
                return courses.get(i);
            }
        }
        return null;
    }

    public Course findCourse(Course course) {
        int idx;
        if ((idx = courses.indexOf(course)) != -1) {
            course = courses.get(idx);
            return course;
        }
        return null;
    }

    public Student findStudent(String indexNumber) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getIndexNumber().equals(indexNumber)) {
                return students.get(i);
            }
        }
        return null;
    }
    
     public Student findStudent(Student student) {
        int idx;
        if ((idx = students.indexOf(student)) != -1) {
            student = students.get(idx);
            return student;
        }
        return null;
    }

    public void removeStudent(String indexNumber) {
        // TODO - implement Aplikacja.usunStudenta
        throw new UnsupportedOperationException();
    }
    
    
    public void enrollStudent(String groupCode, String indexNumber, String courseCode) {

        Student student = findStudent(indexNumber);

        if (student == null) {
            System.out.println("Nie znaleziono studenta!");
            return;
        }

        Course course = findCourse(courseCode);

        if (course == null) {
            System.out.println("Nie znaleziono kursu!");
            return;
        }

        StudyGroup group = course.findGroup(groupCode);

        if (group == null) {
            System.out.println("Nie znaleziono grupy zajęciowej!");
            return;
        }

        if (!group.enrollStudent(student)) {
            System.out.println("Nie udało się zapisać na zajęcia!");
            return;
        }
        System.out.println("Dodano studenta do grupy!");
}


   
 
    public static void main(String[] args) {
        
    conn = ConnectionMeneger.getConnection();
    System.out.println("connected");
    
    Application app = new Application();
    Factory factory = new Factory();
    
    
        String[] daneStudenta1 = { "Mariusz", "Pudzianowski", "1997-05-05", "238366", "Informatyka", "4"};
        String[] daneKursu1 = {"HHH234", "Inżynieria Oprogramowania", "Laboratorium", "Informatyka", "5"};
        String[] daneGrupy1 = {"BBB123", "mgr. inż. Adam Małysz", "25", "15", "Środa"};

    //    app.addStudent(daneStudenta1);
//        System.out.println(app.addCourse(daneKursu1));
//        System.out.println(app.addGroup(daneGrupy1, app.courses.get(0)));
//
//        Student student = app.findStudent(daneStudenta1[0]);
//        app.enrollStudent(daneGrupy1[0], daneStudenta1[4], daneKursu1[0]);
    
    ConnectionMeneger.closeConnection();
    
}
}