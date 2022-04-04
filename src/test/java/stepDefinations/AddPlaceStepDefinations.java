package stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Location;
import org.testng.Assert;
import pojo.LocationPojo;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class AddPlaceStepDefinations {

    Response response;

    @Given("I have Add Place Payload")
    public void i_have_add_place_payload() {

        LocationPojo loc = new LocationPojo();
        loc.setAccuracy(50);
        loc.setAddress("At Badmal");
        loc.setLanguage("English");
        loc.setName("Patel Nivas");
        loc.setPhone_number("8494844443");
        loc.setWebsite("www.pat9403@gmail.com");
        List<String> locType = new ArrayList<>();
        locType.add("home");
        locType.add("shade");
        loc.setTypes(locType);
        Location location = new Location();
        location.setLatitude(110.00);
        location.setLongitude(290.02);
        loc.setLocation(location);

//        req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
//        req.given().spec(req).body(loc);
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        given().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(loc);

    }

    @When("I call {string} with Post HTTP Request")
    public void i_call_with_post_http_request(String string) {
        response = when().post("/maps/api/place/add/json")
                .then().extract().response();

    }

    @Then("I should get the Success Response with Status Code {int}")
    public void i_should_get_the_success_response_with_status_code(Integer int1) {
        Assert.assertEquals(response.getStatusCode(), int1);
    }
}
