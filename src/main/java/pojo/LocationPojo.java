package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * LocationPojo class to create Pojo payload for Location
 */
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

@Setter
@Getter
class Location {

    private Double latitude;
    private Double longitude;
}
