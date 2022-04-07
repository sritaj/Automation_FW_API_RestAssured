package dto;

import com.github.javafaker.Faker;
import pojo.StudentPojo;

import java.util.ArrayList;
import java.util.Arrays;

public class StudentData {

    private StudentPojo stdPojo;
    private Faker fs;

    public StudentData(){
        this.stdPojo = new StudentPojo();
        this.fs = new Faker();
    }

    private ArrayList<String> courses(){
        return new ArrayList<>(Arrays.asList("C++", "Java", "JavaScript"));
    }

    public StudentPojo createStudentData(){
        stdPojo.setFirstName(fs.name().firstName());
        stdPojo.setLastName(fs.name().lastName());
        stdPojo.setEmail(fs.name().firstName() + "@gmail.com");
        stdPojo.setProgramme(fs.book().title());
        stdPojo.setCourses(courses());
        return stdPojo;
    }

    public StudentPojo updateStudentData(){
        stdPojo.setFirstName(fs.name().firstName());
        stdPojo.setLastName(fs.name().lastName());
        stdPojo.setEmail(fs.name().firstName() + "@gmail.com");
        stdPojo.setProgramme(fs.book().title());
        return stdPojo;
    }

    public StudentPojo updateEmailAddress(){
        stdPojo.setEmail(fs.name().firstName() + "@gmail.com");
        return stdPojo;
    }

}
