import io.restassured.RestAssured;
import org.testng.Assert;
import utilities.JsonPathImpl;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics {

    public static void main(String[] args) {

        String place = "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Frontline house\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \"29, side layout, cohen 09\",\n" +
                "  \"types\": [\n" +
                "    \"shoe park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}\n";

        //Validation of Add Place API
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .log().all()
                .body(place)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();

        System.out.println(response);

//        JsonPath jsonpath = ResponseExtraction.rawToJSON(response);
//        String placeID = jsonpath.get("place_id");
        String placeID = JsonPathImpl.extractValueFromResponse(response, "place_id");
        System.out.println(placeID);

        //Validation of Updated Place API -> Get the place ID to update the address and validate it in the Response
        String updatedAddress = "At Ramji Pada, Near Sankirtan Mandap";
        String updatedBody = "{\"place_id\" :\"" + placeID + "\", \"address\": \"" + updatedAddress + "\", \"key\" : \"qaclick123\" }";

        given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(updatedBody)
                .when().put("/maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200);

        //Validation of Get Place API -> Get the updated address and validate it in the Response
        String getUpdatedAddress = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeID)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

//        JsonPath jpath = ResponseExtraction.rawToJSON(getUpdatedAddress);;
//        String newAddress = jpath.get("address");
        String newAddress = JsonPathImpl.extractValueFromResponse(getUpdatedAddress, "address");
        System.out.println(newAddress);

        Assert.assertEquals(newAddress, updatedAddress);

    }

}
