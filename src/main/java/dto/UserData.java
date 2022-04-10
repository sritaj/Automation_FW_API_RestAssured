package dto;

import com.github.javafaker.Faker;
import pojo.UserPojo;

public class UserData {

    UserPojo up;
    Faker fs;

    public UserData() {
        this.up = new UserPojo();
        this.fs = new Faker();
    }

    public UserPojo createUser() {
        up.setName(fs.name().fullName());
        up.setJob(fs.job().title());
        return up;
    }
}
