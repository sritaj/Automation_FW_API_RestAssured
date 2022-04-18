package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * StudentPojo class to create Pojo payload for Student
 */
@Getter
@Setter
public class StudentPojo {

    private String firstName;
    private String lastName;
    private String email;
    private String programme;
    private List<String> courses;
}
