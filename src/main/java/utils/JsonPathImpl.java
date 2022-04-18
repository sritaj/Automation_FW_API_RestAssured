package utils;

import io.restassured.path.json.JsonPath;

/**
 * JsonPathImpl class to define wrapper methods for JsonPath
 */
public class JsonPathImpl {

    /**
     * Method to get JSON response in JsonPath syntax
     *
     * @param response - The JSON response converted to String
     * @return JsonPath - Json response in JsonPath syntax
     */
    public static JsonPath rawToJSON(String response) {
        return new JsonPath(response);
    }

    /**
     * Method to get the key from the JSON response
     *
     * @param response - The JSON response converted to String
     * @param key - The key that needs to be extracted from the Json
     * @return String - The specified key value
     */
    public static String extractValueFromResponse(String response, String key) {
        JsonPath jsonPath = new JsonPath(response);
        return jsonPath.getString(key);
    }
}
