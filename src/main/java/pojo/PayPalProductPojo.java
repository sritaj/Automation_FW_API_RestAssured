package pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * PayPalProductPojo class to create Pojo payload for PayPal
 */
@Getter
@Setter
public class PayPalProductPojo {

    private String name;
    private String description;
    private String type;
    private String category;
    private String image_url;
    private String home_url;

}
