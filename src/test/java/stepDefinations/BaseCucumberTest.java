package stepDefinations;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class BaseCucumberTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
}
