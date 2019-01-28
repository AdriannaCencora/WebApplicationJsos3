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
import java.text.DateFormat;

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
//        Course foundCourse = findCourseByCode(course);
//        if (foundCourse == null) {
//            courses.add(course);
//            String info = course.toString();
//            return info;
//        }
//        return null;

   
    
public void addStudent(String[] data) {
        Factory factory = new Factory();
        Student student = factory.createStudent(data);
        
        if(findStudentByIndex(student.getIndexNumber()).getIndexNumber() != null) {
            System.out.println("Student already exists in base!");
        }
           
        else { 
            if(addStudentToDatabase(student)) {
                 System.out.println("Adding student succed.");
        }
             else 
                 System.out.println("Adding student failed.");
        
        } 
    
    }

public boolean addStudentToDatabase(Student student) { 
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
        
        if(findStudentByIndex(String.valueOf(indexNumber)).getIndexNumber() == null) {
            System.out.println("Student doesn't exist in base!");
        }
           
        else { 
        
        if(removeStudentFromDatabase(indexNumber)) {
            System.out.println("Removing succed.");
        }
        else 
            System.out.println("Removing failed.");

    }
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
            
    
public Course findCourseByCode(String courseCode) {
    Course course = new Course();
    try {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM Courses WHERE courseID =  '" + courseCode + "' ");

        if(rs.next()) {
            course.setCourseCode(rs.getString("courseID"));
            course.setFieldOfStudy(rs.getString("fieldsOfStudy"));
            course.setName(rs.getString("courseName"));
            course.setSemester(rs.getInt("semester"));
           
            return course;
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
         
    return null;
}
    
public StudyGroup findGroupByCode(String groupCode) {
    StudyGroup studyGroup = new StudyGroup();
  
    try {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM Groups WHERE groupID =  '" + groupCode + "' ");

        if(rs.next()) {
            studyGroup.setGroupCode(rs.getString("groupID"));
            studyGroup.setCurrentNumberOfOccupiedPlaces(rs.getInt("takenSeats"));
            studyGroup.setMaxNumberOfPlaces(rs.getInt("maxSeats"));
            studyGroup.setTypeOfClass(rs.getString("typeOfClass"));
            studyGroup.setDay(rs.getString("dayID"));
            studyGroup.setLecturer(rs.getString("teacher"));
            
            java.sql.Time dbSqlTime = rs.getTime("startHour");
            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            String strDate = dateFormat.format(dbSqlTime);
 
            studyGroup.setHour(strDate);
            
            String courseID = rs.getString("course");
            studyGroup.setCourse(findCourseByCode(courseID));

;
           
            return studyGroup;
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
         
    return null;
}

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
                      
            if(resultSetStudent.next()) {
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
    
 
    
    public void enrollStudent(String groupCode, String indexNumber, String courseCode) {

        Student student = findStudentByIndex(indexNumber);

        if (student == null) {
            System.out.println("Student does not exist in base!");
            return;
        }

        Course course = findCourseByCode(courseCode);

        if (course == null) {
            System.out.println("Course not found!");
            return;
        }
        
        StudyGroup group = findGroupByCode(groupCode);

        if (group == null) {
            System.out.println("Group not found!");
            return;
        }

        if (!addEnrollmentToDatabase(student, group)) {
            System.out.println("Nie udało się zapisać na zajęcia!");
            return;
        }
        System.out.println("Dodano studenta do grupy!");
}
    
    
 public boolean addEnrollmentToDatabase (Student student, StudyGroup group) {
     try { 
         ps  = conn.prepareStatement("INSERT INTO GroupRecords (studentIndex, groupID) "
                    + " VALUES (?, ?)");
         
         
         String query = "SELECT indexNum from Students WHERE indexNum= '" + student.getIndexNumber() + "' ";
         stmt = conn.createStatement();
         rs = stmt.executeQuery(query);
         
         if (rs.next()) {
                ps.setInt(1, rs.getInt("indexNum"));
         }
                        
         
         query = "SELECT groupID from Groups WHERE groupID= '" + group.getGroupCode() + "' ";
         stmt = conn.createStatement();
         rs = stmt.executeQuery(query);
         
         if (rs.next()) {
                ps.setString(2, rs.getString("groupID"));
         }

         ps.executeUpdate();
            return true;
            
     } catch (SQLException ex) {
            ex.printStackTrace();
        }
     return false;
 }
 
    public static void main (String[] args) {
        
    conn = ConnectionMeneger.getConnection();
    System.out.println("connected");
    
    Application app = new Application();
    Factory factory = new Factory();

        //String[] daneUsera1 = {"111226", "Gal", "Anonim", "2001-05-05"}; 
        String[] daneStudenta1 = {"560606", "Ewa", "Golebiewska", "1995-07-05", "232988", "Informatyka", "5"};
        String[] daneKursu1 = {"HHH234", "Inżynieria Oprogramowania",  "Informatyka", "5"};
        String[] daneGrupy1 = {"BBB123", "mgr. inż. Adam Małysz", "25", "15", "Środa"};

     //app.addStudent(daneStudenta1);
   //Course course = app.findCourseByCode("A12345");
     //   System.out.println(course.toString());
        
          StudyGroup group = app.findGroupByCode("E12345");
        System.out.println(group.toString());
            app.enrollStudent("E12345", "218403", "A22223");
  //  Student student = app.findStudentByIndex("232988");
    
   // System.out.println(student.toString());
    //  app.removeStudent(218400);
//        System.out.println(app.addCourse(daneKursu1));
//        System.out.println(app.addGroup(daneGrupy1, app.courses.get(0)));
//
//        Student student = app.findStudentByIndex(daneStudenta1[0]);
//        app.enrollStudent(daneGrupy1[0], daneStudenta1[4], daneKursu1[0]);
    
    ConnectionMeneger.closeConnection();
    
}
}