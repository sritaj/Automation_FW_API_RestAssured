package dto;

import com.github.javafaker.Faker;
import pojo.UserPojo;

/**
 * UserData class to create data for User
 */
public class UserData {

    UserPojo up;
    Faker fs;

    public UserData() {
        this.up = new UserPojo();
        this.fs = new Faker();
    }

    /**
     * Method to create User
     *
     * @return UserPojo - Returns Object of User type
     */
    public UserPojo createUser() {
        up.setName(fs.name().fullName());
        up.setJob(fs.job().title());
        return up;
    }
}
