package examples;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.JsonPathImpl;

import java.util.List;

public class ComplexJsonParseTest extends BaseTest {

    @Test(testName = "Validate Json Parsing Scenarios")
    public void validateJSONParsing(){

        String sampleJSON = "{\r\n" +
                "  \"dashboard\": {\r\n" +
                "    \"purchaseAmount\": 1162,\r\n" +
                "    \"website\": \"rahulshettyacademy.com\"\r\n" +
                "  },\r\n" +
                "  \"courses\": [\r\n" +
                "    {\r\n" +
                "      \"title\": \"Selenium Python\",\r\n" +
                "      \"price\": 50,\r\n" +
                "      \"copies\": 6\r\n" +
                "    },\r\n" +
                "    {\r\n" +
                "      \"title\": \"Cypress\",\r\n" +
                "      \"price\": 40,\r\n" +
                "      \"copies\": 4\r\n" +
                "    },\r\n" +
                "    {\r\n" +
                "      \"title\": \"RPA\",\r\n" +
                "      \"price\": 45,\r\n" +
                "      \"copies\": 10\r\n" +
                "    },\r\n" +
                "     {\r\n" +
                "      \"title\": \"Appium\",\r\n" +
                "      \"price\": 36,\r\n" +
                "      \"copies\": 7\r\n" +
                "    }\r\n" +
                "    \r\n" +
                "    \r\n" +
                "    \r\n" +
                "  ]\r\n" +
                "}\r\n" +
                "";


        JsonPath js = JsonPathImpl.rawToJSON(sampleJSON);

        //Print courses returned by JSON
        int count = js.getInt("courses.size()");
        System.out.println(count);

        //Print purchase amount
        int purchaseAmt = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmt);

        //Print title of the first course
        String titleFirstCourse = js.get("courses[0].title");
        System.out.println(titleFirstCourse);

        //Print title of the last course
        String titleLastCourse = js.get("courses[-1].title");
        System.out.println(titleLastCourse);

        //Print all the courses title
        List<Object> courseTitles = js.getList("courses.title");
        courseTitles.forEach(System.out::println);
        System.out.println(courseTitles);

        //Print all the courses details
        List<Object> courses = js.getList("courses");
        courses.forEach(System.out::println);
        System.out.println(courses);

        //Print all the courses with their respective prices
        for (int i = 0; i < count; i++) {
            String courseTitle = js.get("courses[" + i + "].title");
            int price = js.getInt("courses[" + i + "].price");
            System.out.println(courseTitle + " : " + price);
        }

        //Print the copies sold for the course "RPA"
        for (int i = 0; i < count; i++) {
            String courseTitle = js.get("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int copies = js.get("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }
        }

        //Verify if purchase Amount total matches with price * copies for all courses
        int total = 0;
        for (int i = 0; i < count; i++) {
            int price = js.get("courses[" + i + "].price");
            int copies = js.get("courses[" + i + "].copies");
            int subTotal = price * copies;
            total = total + subTotal;
        }
        System.out.println(total);

        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);
        Assert.assertEquals(total, purchaseAmount);
    }
}
