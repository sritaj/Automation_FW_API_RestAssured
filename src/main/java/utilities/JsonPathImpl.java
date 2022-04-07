package utilities;

import io.restassured.path.json.JsonPath;

public class JsonPathImpl {

    public static JsonPath rawToJSON(String response) {
        return new JsonPath(response);
    }

    public static String extractValueFromResponse(String response, String key) {
        JsonPath jsonPath = new JsonPath(response);
        return jsonPath.getString(key);
    }
}
