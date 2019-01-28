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
public class Course {
    private String courseCode;
    private String name;
    private String type;
    private String fieldOfStudy;
    private int semester;
    private ArrayList<StudyGroup> groups;

    public Course() {
        groups = new ArrayList();
}

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public ArrayList<StudyGroup> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<StudyGroup> groups) {
        this.groups = groups;
    }
    
    
//     public void przegladajGrupy() {
//        // TODO - implement Kurs.przegladajGrupy
//        throw new UnsupportedOperationException();
//    }

    public StudyGroup findGroup(String groupCode) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupCode() == groupCode) {
                return groups.get(i);
            }
        }
        return null;
    }

    public StudyGroup findGroup(StudyGroup group) {
        int idx;
        if ((idx = groups.indexOf(group )) != -1) {
            group = (StudyGroup) groups.get(idx);
            return group;
        }
        return null;
    }

    
        public StudyGroup addGroup(String data[]) {
                Factory factory = new Factory();
        StudyGroup newGroup;
        newGroup = factory.createStudyGroup(data);
        if (findGroup(newGroup) == null) {
            groups.add(newGroup);
            newGroup.setCourse(this);
            return newGroup;
        }
        return null;
    }

    public void removeGroup(String groupCode) {
        StudyGroup searchedGroup = findGroup(groupCode);

        if (searchedGroup == null) {
            System.out.println("Taka grupa nie istnieje!");
            return;
        }

        searchedGroup.writeOutStudent();
        searchedGroup = null;
        System.out.println("Usunięto grupę!");
}
    
    
      @Override
    public String toString(){
        return "Nazwa: " + name +
                ", ID: " + courseCode +
                ", Semester: " + semester + 
                ", Kierunek: " + fieldOfStudy;
    
}
}
