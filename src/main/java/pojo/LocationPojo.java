package pojo;

import lombok.Getter;
import lombok.Setter;
import models.Location;

import java.util.List;

@Getter
@Setter
public class LocationPojo {

    private int accuracy;
    private String name;
    private String phone_number;
    private String address;
    private String website;
    private String language;
    private Location location;
    private List<String> types;


}
