package stepDefinations;

import dto.UserData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import pojo.UserPojo;
import utilities.JsonPathImpl;

import static io.restassured.RestAssured.given;

public class ReqresAPITest {

    UserData udata;
    UserPojo upojo;
    Response response;
    String resp;
    RequestSpecification spec;

    @Given("As an user")
    public void as_an_user() {
        udata = new UserData();
        upojo = udata.createUser();
        spec = given().contentType(ContentType.JSON).body(upojo);

    }

    @When("I add a resource by providing name and job")
    public void i_add_a_resource_by_providing_and() {
        response = spec
                .when().post("/users");
        response.prettyPrint();
    }

    @Then("I should get {string} response with id")
    public void i_should_get_response_with_id(String string) {

        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(string));
        resp = response.asString();
        String id = JsonPathImpl.extractValueFromResponse(resp, "id");
        System.out.println(id);
        Assert.assertNotNull(id);
    }

    @Given("A list of users are available")
    public void a_list_of_users_are_available() {
        spec = given();
    }

    @Given("I specify the pagecount as {string}")
    public void i_specify_the_pagecount_as(String string) {
        spec.queryParam("page", string);
    }

    @When("I fetch the users")
    public void i_fetch_the_users() {
        response = spec.when().get("/users");
    }

    @Then("I should get {string} response and page value as {string} in response body")
    public void i_should_get_response_and_page_value_as_in_response_body(String string, String string2) {
        response.prettyPrint();
        resp = response.asString();
        String pageCount = JsonPathImpl.extractValueFromResponse(resp, "page");

        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(string));
        Assert.assertEquals(pageCount, string2);

    }
}
