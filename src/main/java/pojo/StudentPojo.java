package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentPojo {

    private String firstName;
    private String lastName;
    private String email;
    private String programme;
    private List<String> courses;
}
