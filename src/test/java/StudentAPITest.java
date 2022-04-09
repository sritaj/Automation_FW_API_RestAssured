import dto.StudentData;
import enums.StudentSpecs;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import listeners.CustomAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.StudentPojo;
import utilities.JsonPathImpl;
import utilities.PropertiesFileImpl;

import java.util.List;

import static io.restassured.RestAssured.given;

public class StudentAPITest extends BaseTest {

    /*
    Command to Run the Docker Setup for testing below APIS
    docker pull tejasn1/student-app

     */
    Response response;
    JsonPath js;
    String resp;
    String studentEndpoint;
    int studID;
    List<Integer> studentID;
    List<String> studentNames;
    StudentData studentData;
    StudentPojo stdData;

    @BeforeClass
    public void init() {
        studentData = new StudentData();
        RestAssured.baseURI = PropertiesFileImpl.getDataFromPropertyFile(StudentSpecs.STUDENTBASEURI);
        RestAssured.port = Integer.parseInt(PropertiesFileImpl.getDataFromPropertyFile(StudentSpecs.STUDENTPORT));
        studentEndpoint = PropertiesFileImpl.getDataFromPropertyFile(StudentSpecs.STUDENTAPIENDPOINT);
    }

    @CustomAnnotations(testCaseType = "Integration")
    @Test(testName = "Validate fetching of all the Students")
    public void getAllStudent() {

        response = given().when().get(studentEndpoint + "/list");

        response.prettyPrint();
        //response.then().assertThat().statusCode(200);
        Assert.assertEquals(response.getStatusCode(), 200); //TestNG assertion by validating on the response
        resp = response.asString();
        js = JsonPathImpl.rawToJSON(response.asString());

        studentID = js.getList("id");
        studID = studentID.get(0);
        studentNames = js.getList("firstName");
        studentNames.forEach(System.out::println);
    }

    @CustomAnnotations(testCaseType = "Integration")
    @Test(testName = "Validate fetching of Student based on specified query parameters")
    public void getFilteredStudents() {

        int limit = 1;
        response = given()
                .queryParam("programme", "Computer Science")
                .queryParam("limit", limit)
                .when().get(studentEndpoint + "/list")
                .then().extract().response();

        response.prettyPrint();
        js = JsonPathImpl.rawToJSON(response.asString());

        int list_size = js.getList("$").size();
        Assert.assertEquals(list_size, limit);
    }

    @CustomAnnotations(testCaseType = "Integration")
    @Test(testName = "Validate fetching of Student based on the path parameters", dependsOnMethods = {"getAllStudent"})
    public void getSpecifiedStudent() {

        response = given()
                .pathParam("id", studID)
                .when().get(studentEndpoint + "/{id}")
                .then().extract().response();

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @CustomAnnotations(testCaseType = "Integration")
    @Test(testName = "Validate adding of new Student")
    public void createAStudent() {

        stdData = studentData.createStudentData();
        String payload = "{\"firstName\":\"" + stdData.getFirstName() + "\"," +
                "\"lastName\":\"" + stdData.getLastName() + "\"," +
                "\"email\":\"" + stdData.getEmail() + "\"," +
                "\"programme\":\"" + stdData.getProgramme() + "\"," +
                "\"courses\":[\"C++\",\"JAVA\"]}"; //TODO - Passing stdData course Arraylist is giving issues

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when().post(studentEndpoint)
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Student added");
    }

    @CustomAnnotations(testCaseType = "Integration")
    @Test(testName = "Validate adding of new Student using Pojo payload")
    public void createAStudentWithPojoPayload() {

        stdData = studentData.createStudentData();
        response = given()
                .header("Content-Type", "application/json")
                .body(stdData)
                .when().post(studentEndpoint)
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Student added");
    }

    @CustomAnnotations(testCaseType = "Integration")
    @Test(testName = "Update details for the specified student")
    public void updateSpecifiedStudent() {

        stdData = studentData.updateStudentData();
        response = given()
                .header("Content-Type", "application/json")
                .pathParam("id", 104)
                .body(stdData)
                .when().put(studentEndpoint + "/{id}");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);

        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Student Updated");

    }

    @CustomAnnotations(testCaseType = "Integration")
    @Test(testName = "Validate updation of Email ID for the specified student")
    public void updateStudentEmailID() {

        stdData = studentData.updateEmailAddress();
        response = given()
                .header("Content-Type", "application/json")
                .pathParam("id", 104)
                .body(stdData)
                .when().patch(studentEndpoint + "/{id}");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);

        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Updated");
    }

    @CustomAnnotations(testCaseType = "E2E")
    @Test(testName = "Validate deletion of the Specified Student", groups = "Regression")
    public void deleteSpecifiedStudent() {

        stdData = studentData.createStudentData();
        given()
                .header("Content-Type", "application/json")
                .body(stdData)
                .when().post(studentEndpoint);

        response = given().when().get(studentEndpoint + "/list");
        js = JsonPathImpl.rawToJSON(response.asString());
        studentID = js.getList("id");
        studID = studentID.get(studentID.size() - 1);
        System.out.println(studID);

        response = given()
                .pathParam("id", studID)
                .when().delete(studentEndpoint + "/{id}");

        response.prettyPrint();
        response.then().assertThat().statusCode(204);
    }
}
