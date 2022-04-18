package dto;

import com.github.javafaker.Faker;
import pojo.StudentPojo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * UserData class to create/update data for Student
 */
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

    /**
     * Method to create Student
     *
     * @return StudentPojo - Returns Object of Student type
     */
    public StudentPojo createStudentData(){
        stdPojo.setFirstName(fs.name().firstName());
        stdPojo.setLastName(fs.name().lastName());
        stdPojo.setEmail(fs.name().firstName() + "@gmail.com");
        stdPojo.setProgramme(fs.book().title());
        stdPojo.setCourses(courses());
        return stdPojo;
    }

    /**
     * Method to update Student
     *
     * @return StudentPojo - Returns Object of Student type
     */
    public StudentPojo updateStudentData(){
        stdPojo.setFirstName(fs.name().firstName());
        stdPojo.setLastName(fs.name().lastName());
        stdPojo.setEmail(fs.name().firstName() + "@gmail.com");
        stdPojo.setProgramme(fs.book().title());
        return stdPojo;
    }

    /**
     * Method to update Student email address
     *
     * @return StudentPojo - Returns Object of Student type
     */
    public StudentPojo updateEmailAddress(){
        stdPojo.setEmail(fs.name().firstName() + "@gmail.com");
        return stdPojo;
    }

}
