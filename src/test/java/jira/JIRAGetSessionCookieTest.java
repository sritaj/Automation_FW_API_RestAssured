package jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class JIRAGetSessionCookieTest {

    String creds = "{ \"username\": \"sritajpatel\", \"password\": \"sritajpatel\" }";

    //ToDo - Below code only for reference, working as expected from POSTMAN but not while using RestAssured
    @Test(testName = "Validate getting Session Cookie from JIRA")
    public void getJIRASessionCookie() {

        SessionFilter sess = new SessionFilter();

        RestAssured.baseURI = "http://localhost:8080";
        Response response = given().relaxedHTTPSValidation().header("Content-Type", "application/json")
                .body(creds)
                .when().post("/rest/auth/1/session");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(response);
        System.out.println(response.getCookies());
    }
}
