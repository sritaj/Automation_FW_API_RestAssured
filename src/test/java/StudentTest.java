import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pojo.StudentPojo;
import reusableMethods.JsonPathImpl;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class StudentTest {

    /*
    Command to Run the Docker Setup for testing below APIS
    docker pull tejasn1/student-app
    docker run -p 8085:8080 -d tejasn1/student-app
     */
    
    @BeforeTest
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8085;
    }

    @Test(testName = "Validate fetching of all the Students")
    public void getAllStudent() {

        Response response = given().when().get("student/list");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        //Assert.assertEquals(response.getStatusCode(), 200); //TestNG assertion by validating on the response

        String resp = response.asString();
        JsonPath js = JsonPathImpl.rawToJSON(resp);

        List<String> studentNames = js.getList("firstName");
        studentNames.forEach(System.out::println);
    }

    @Test(testName = "Validate fetching of Student based on specified query parameters")
    public void getFilteredStudents() {

        Response response = given()
                .queryParam("programme", "Computer Science")
                .queryParam("limit", 1)
                .when().get("/student/list")
                .then().extract().response();

        response.prettyPrint();
    }

    @Test(testName = "Validate fetching of Student based on the path parameters")
    public void getSpecifiedStudent() {

        Response response = given()
                .pathParam("id", 1)
                .when().get("/student/{id}")
                .then().extract().response();

        response.prettyPrint();
    }

    @Test(testName = "Validate adding of new Student")
    public void createAStudent() {

        Faker fs = new Faker();
        String firstName = fs.name().firstName();
        String lastName = fs.name().lastName();
        String course = fs.book().title();
        String email = firstName + "@gmail.com";

        String payload = "{\"firstName\":\"" + firstName + "\"," +
                "\"lastName\":\"" + lastName + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"programme\":\"" + course + "\"," +
                "\"courses\":[\"C++\",\"JAVA\"]}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when().post("/student")
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Student added");

    }

    @Test(testName = "Validate adding of new Student using Pojo payload")
    public void createAStudentWithPojoPayload() {

        StudentPojo student = new StudentPojo();
        List<String> courses = new ArrayList<>();
        courses.add("C++");
        courses.add("Java");
        Faker fs = new Faker();

        student.setFirstName(fs.name().firstName());
        student.setLastName(fs.name().lastName());
        student.setEmail(fs.name().firstName() + "@gmail.com");
        student.setProgramme(fs.book().title());
        student.setCourses(courses);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(student)
                .when().post("/student")
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Student added");
    }

    @Test(testName = "Update details for the specified student")
    public void updateSpecifiedStudent() {
        StudentPojo updateStudent = new StudentPojo();
        Faker fs = new Faker();

        updateStudent.setFirstName(fs.name().firstName());
        updateStudent.setLastName(fs.name().lastName());
        updateStudent.setEmail(fs.name().firstName() + "@gmail.com");
        updateStudent.setProgramme(fs.book().title());

        Response response = given()
                .header("Content-Type", "application/json")
                .pathParam("id", 104)
                .body(updateStudent)
                .when().put("student/{id}");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);

        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Student updated");

    }

    @Test(testName = "Validate updation of Email ID for the specified student")
    public void updateStudentEmailID() {
        StudentPojo updateStudentEmailID = new StudentPojo();
        Faker fs = new Faker();

        updateStudentEmailID.setEmail(fs.name().firstName() + "@gmail.com");

        Response response = given()
                .header("Content-Type", "application/json")
                .pathParam("id", 104)
                .body(updateStudentEmailID)
                .when().patch("student/{id}");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);

        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Updated");
    }

    @Test(testName = "Validate deletion of the Specified Student")
    public void deleteSpecifiedStudent() {

        Response response = given()
                .pathParam("id", 103)
                .when().delete("/student/{id}");

        response.prettyPrint();
        response.then().assertThat().statusCode(204);
    }
}
