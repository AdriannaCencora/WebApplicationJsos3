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

//    public String addCourse(String[] data) {
//        Factory fabryka = new Factory();
//        Course course = fabryka.createCourse(data);
//        Course foundCourse = findCourse(course);
//        if (foundCourse == null) {
//            courses.add(course);
//            String info = course.toString();
//            return info;
//        }
//        return null;
//}
//    
//public String addUser(String[] data) {
//        Factory factory = new Factory();
//        User user = factory.createUser(data);
//        // foundStudent = findStudentByIndex(student);
//       // if (foundStudent == null) {
//         
//            addUserToDatabase(user);
//
//           // String info = student.toString();
//    //        return null;
//      //  }
//        return null;
//    }
//    
    
//public void addUserToDatabase (User user) {
//      System.out.println(user.toString());
//    
//            try {
//            ps  = conn.prepareStatement("INSERT INTO Users"
//                    + " VALUES (?, ?, ?, ?)");
//            ps.setInt(1, Integer.parseInt(user.getID()));
//            ps.setString(2, user.getName());
//            ps.setString(3, user.getSurname());
//            
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            Date parsed = null;
//            try {
//                System.out.println(user.getBirthDate());
//                   parsed = format.parse(user.getBirthDate());
//            }  catch (ParseException e1) {
//                        e1.printStackTrace();
//            }
//            java.sql.Date sql = new java.sql.Date(parsed.getTime());
//            ps.setDate(4, sql);
//            
//            ps.executeUpdate();
//            ps.close();
//            }
//            catch (SQLException ex) {
//
//        ex.printStackTrace();
//
//    }
//
//    
//}

    
    
    
    
    
public void addStudent(String[] data) {
        Factory factory = new Factory();
        Student student = factory.createStudent(data);
        
        if(addStudentToDatabase(student)) {
            System.out.println("Adding student succed.");
        }
        else 
            System.out.println("Adding student failed.");
        
    
    }


public boolean addStudentToDatabase(Student student) {
    
        System.out.println(student.toString());
    
            try {
            ps  = conn.prepareStatement("INSERT INTO Users "
                    + " VALUES (?, ?, ?, ?)");
            ps.setInt(1, Integer.parseInt(student.getID()));
            ps.setString(2, student.getName());
            ps.setString(3, student.getSurname());
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = null;
            try {
                System.out.println(student.getBirthDate());
                   parsed = format.parse(student.getBirthDate());
            }  catch (ParseException e1) {
                        e1.printStackTrace();
            }
            java.sql.Date sql = new java.sql.Date(parsed.getTime());
            ps.setDate(4, sql);
            
            ps.executeUpdate();
            ps.close();

                       
             ps  = conn.prepareStatement("INSERT INTO Students  VALUES (?, ?, ?, ?)");
             ps.setInt(1, Integer.parseInt(student.getIndexNumber()));
             
             String query1 = "SELECT userID from Users where userID = '" + student.getID() + "' ";
             stmt = null;
             stmt = conn.createStatement();
             
             rs = null;
             rs = stmt.executeQuery(query1);
             
            if (rs.next()) {
                ps.setInt(2, Integer.parseInt(rs.getString("userID") ));
            }
             
             
             
             String query = "SELECT fieldID from FieldsOfStudy WHERE fieldName= '" + student.getFieldOfStudy() + "' ";
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             
            if (rs.next()) {
                ps.setString(3, rs.getString("fieldID") );
            }
             ps.setInt(4, Integer.parseInt(student.getSemester()));

             
             int succeed = ps.executeUpdate();
             if (succeed == 1) {
                 return true;
             }
             
        } catch (SQLException ex) {

        ex.printStackTrace();

    }
            return false;
}
    

    public void removeStudent(int indexNumber) {
        if(removeStudentFromDatabase(indexNumber)) {
            System.out.println("Removing succed.");
        }
        else 
            System.out.println("Removing failed.");

    }

            
           
    
    public boolean removeStudentFromDatabase(int studentIndex) {
       
        try {
            stmt = conn.createStatement();
            Statement stmt1 = conn.createStatement();
            String queryToSelect = "SELECT userID FROM Students WHERE indexNum = " + studentIndex;
            rs = stmt1.executeQuery(queryToSelect);

            //Removing student from groupRecords table.
            int k = stmt.executeUpdate("DELETE FROM GroupRecords WHERE studentIndex = " + studentIndex );
                
            int i = stmt.executeUpdate("DELETE FROM Students WHERE indexNum = " + studentIndex);
            if(i == 1 && k == 1) {
                try {
                if (rs.next()) {
           
                int j = stmt1.executeUpdate("DELETE FROM Users WHERE userID = " + rs.getInt("userID"));
                
                        if (j == 1) 
                            return true; 

                }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
              
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            return false;
      }
            
            

            

//    public String addGroup(String[] data, Course course) {
//        StudyGroup studyGroup = course.addGroup(data);
//        if (studyGroup == null) {
//            course.getGroups().add(studyGroup);
//            String info = course.toString();
//            return info;
//        }
//        return null;
//    }

//    public void removeCourse(String courseCode) {
//        // TODO - implement Aplikacja.usunKurs
//        throw new UnsupportedOperationException();
//    }
    
     public Course findCourse(String courseCode) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseCode().equals(courseCode)) {
                return courses.get(i);
            }
        }
        return null;
    }

//    public Course findCourse(Course course) {
//        int idx;
//        if ((idx = courses.indexOf(course)) != -1) {
//            course = courses.get(idx);
//            return course;
//        }
//        return null;
//    }

     
     
    public Student findStudentByIndex(String indexNumber) {
        Student student = new Student();
        
        try {

            ResultSet resultSetUser;
            ResultSet resultSetStudent;
            
             Statement stmtUser = conn.createStatement();

             Statement stmtStudent = conn.createStatement();

            
            stmt = conn.createStatement();
            String queryToSelectID = "SELECT userID FROM Students WHERE indexNum = " + indexNumber;
            rs = stmt.executeQuery(queryToSelectID);
            
            
            if (rs.next()) {
           
                resultSetUser = stmtUser.executeQuery("SELECT * FROM Users WHERE userID = " + rs.getInt("userID"));
                
                if (resultSetUser.next()) {
                    student.setID(resultSetUser.getString("userID"));
                    student.setName(resultSetUser.getString("firstName"));
                    student.setSurname(resultSetUser.getString("lastName"));
                    student.setBirthDate(resultSetUser.getString("birthDate"));

                }
             }
            
            resultSetStudent = stmtStudent.executeQuery("SELECT *  FROM Students WHERE indexNum  = " + indexNumber );
            
            
            
            if(resultSetStudent.next())

            {

                student.setIndexNumber(resultSetStudent.getString("indexNum") );
                student.setFieldOfStudy(resultSetStudent.getString("fieldOfStudy") );
                student.setSemester(resultSetStudent.getString("semester") );


            }

                return student;

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

    return null;
    }
    
//     public Student findStudentByIndex(Student student) {
//        int idx;
//        if ((idx = students.indexOf(student)) != -1) {
//            student = students.get(idx);
//            return student;
//        }
//        return null;
//    }
//

    
    
    public void enrollStudent(String groupCode, String indexNumber, String courseCode) {

        Student student = findStudentByIndex(indexNumber);

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

        //String[] daneUsera1 = {"111226", "Gal", "Anonim", "2001-05-05"}; 
        String[] daneStudenta1 = {"50000", "Adrianna", "Cencora", "1995-05-05", "218403", "Informatyka", "5"};
        String[] daneKursu1 = {"HHH234", "Inżynieria Oprogramowania", "Laboratorium", "Informatyka", "5"};
        String[] daneGrupy1 = {"BBB123", "mgr. inż. Adam Małysz", "25", "15", "Środa"};

     //app.addStudent(daneStudenta1);
    
    Student student = app.findStudentByIndex("218403");
    
    System.out.println(student.toString());
    //  app.removeStudent(218400);
//        System.out.println(app.addCourse(daneKursu1));
//        System.out.println(app.addGroup(daneGrupy1, app.courses.get(0)));
//
//        Student student = app.findStudentByIndex(daneStudenta1[0]);
//        app.enrollStudent(daneGrupy1[0], daneStudenta1[4], daneKursu1[0]);
    
    ConnectionMeneger.closeConnection();
    
}
}