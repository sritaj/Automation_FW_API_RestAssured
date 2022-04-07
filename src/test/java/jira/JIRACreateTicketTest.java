package jira;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class JIRACreateTicketTest {

    Faker fakedata = new Faker();
    String title = fakedata.company().name();
    String desc = fakedata.company().catchPhrase();

    String issuePayload = "{\n" +
            "\"fields\": {\n" +
            "   \"project\":\n" +
            "   { \n" +
            "      \"key\": \"API\"\n" +
            "   },\n" +
            "   \"summary\": \"" + title + "\",\n" +
            "   \"description\": \"" + desc + "\",\n" +
            "   \"issuetype\": {\n" +
            "      \"name\": \"Bug\"\n" +
            "   }\n" +
            "  }\n" +
            "}";

    //ToDo - Below code only for reference, working as expected from POSTMAN but not while using RestAssured
    @Test(testName = "Validate creating JIRA Ticket")
    public void createJIRATicket() {

        RestAssured.baseURI = "http://localhost:8080";
        given().cookie("mytoken", "MzMxMzMyNjc4OTgzOq57uCh7PgHhw4Q8I+HrGk43fxU5")
                .header("Content-Type", "application/json")
                .log().all()
                .body(issuePayload)
                .when().post("/rest/api/2/issue")
                .then().log().all().assertThat().statusCode(201);
    }

    //ToDo - Below code only for reference, working as expected from POSTMAN but not while using RestAssured
    @Test(testName = "Validate getting the specified JIRA Ticket")
    public void getJIRATicket() {

        Cookie myCookie = new Cookie.Builder("JSESSIONID", "56C8CC8B0BA7A263CA074A8CBAC3384D")
                .setSecured(true)
                .setComment("session id cookie")
                .build();

        RestAssured.baseURI = "http://localhost:8080";
        String response = given().cookie(myCookie)
                .pathParam("key", "10001").log().all()
                .when().get("/rest/api/2/issue/{key}")
                .then().extract().response().asString();

        System.out.println(response);
    }
}
