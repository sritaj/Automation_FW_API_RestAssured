import base.BaseTest;
import io.restassured.parsing.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonPathImpl;

import static io.restassured.RestAssured.given;

public class OAuthTest extends BaseTest {

    @Test(testName = "Validate authentication error when there is no access token")
    public void validateAuthenticationError() {

        String response = given().queryParam("access_token", "")
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .then().extract().response().asString();

        System.out.println(response);

        Assert.assertEquals(response.trim(), "AUTHENTICATION FAILED !!!! PLEASE ENTER VALID ACCESS TOKEN");
    }

    //ToDo - The below code is not working and is only meant for reference
    @Test(testName = "Validate getting of Authorization code and Access token to fetch the course list")
    public void validateAuthorizationAndAccessTokenToFetchDetails() {

        String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";

        String partialcode = url.split("code=")[1];
        String code = partialcode.split("&scope")[0];
        System.out.println(code);

        String response =
                given()
                        .urlEncodingEnabled(false)
                        .queryParams("code", code)
                        .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                        .queryParams("grant_type", "authorization_code")
                        .queryParams("state", "verifyfjdss")
                        .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
                        .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                        .when().log().all()
                        .post("https://www.googleapis.com/oauth2/v4/token").asString();


        System.out.println(response);
        String accessToken = JsonPathImpl.extractValueFromResponse(response, "access_token");
        System.out.println(accessToken);

        String r2 = given().contentType("application/json").
                queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .asString();

        System.out.println(r2);
    }
}
