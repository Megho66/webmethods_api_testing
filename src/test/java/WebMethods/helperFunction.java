package WebMethods;

import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;


import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class helperFunction {

    public static Response GET_API_Active_Directory (String resource_name,String access_token,HashMap<String,String> parameters) throws IOException {
        Properties properties = loadproperties();
        baseURI = properties.getProperty("PORTAL_ACTIVE_DIRECTORY_BASE_URL");
        Response r = given().header("Authorization", "Bearer " + access_token)
                .contentType("application/json")
                .queryParams(parameters)
                .when()
                .get(resource_name);
        return r ;
    }

    public static Response GET_API_PO(String resource_name,String access_token,HashMap<String,String> parameters) throws IOException {
        Properties properties= loadproperties();
        baseURI =properties.getProperty("PORTAL_PO_BASE_URL");
        Response r= given().header("Authorization","Bearer " +access_token)
                .contentType("application/json")
                .queryParams(parameters)
                .when()
                .get(resource_name);

        return r;
    }
    public  static Response GET_API_Service_Desk (String resource_name,String access_token,HashMap<String,String> parameters) throws IOException {
        Properties properties = loadproperties();
        baseURI = properties.getProperty("PORTAL_SERVICE_DESK_BASE_URL");
        Response r = given().header("Authorization", "Bearer " + access_token)
                .contentType("application/json")
                .queryParams(parameters)
                .when()
                .get(resource_name);
        return r ;
    }
    public static Response GET_API_ERP(String resource_name,String access_token,HashMap<String,String> parameters) throws IOException {
        Properties properties= loadproperties();
        baseURI =properties.getProperty("PORTAL_ERP_BASE_URL");
        Response r= given().header("Authorization","Bearer " +access_token)
                .contentType("application/json")
                .queryParams(parameters)
                .when()
                .get(resource_name);

        return r;
    }
    public static Response  POST_API_ERP (String resource_name,String access_token,String getJsonBody ,HashMap<String,String> parameters) throws IOException {

        Properties properties = loadproperties();
        baseURI=properties.getProperty("PORTAL_ERP_BASE_URL");
        Response r = given().header("Authorization","Bearer " +access_token)
                .contentType("application/json").queryParams(parameters).
                body(getJsonBody).when()
                .post(resource_name);

        return r;
    }
    public static Response  POST_API_PO (String resource_name,String access_token,String getJsonBody ,HashMap<String,String> parameters) throws IOException {

        Properties properties = loadproperties();
        baseURI=properties.getProperty("PORTAL_PO_BASE_URL");
        Response r = given().header("Authorization","Bearer " +access_token)
                .contentType("application/json").queryParams(parameters).
                body(getJsonBody).when()
                .post(resource_name);

        return r;
    }
    public static Response  POST_API_Service_Desk (String resource_name,String access_token,String getJsonBody ,HashMap<String,String> parameters) throws IOException {

        Properties properties = loadproperties();
        baseURI=properties.getProperty("PORTAL_SERVICE_DESK_BASE_URL");
        Response r = given().header("Authorization","Bearer " +access_token)
                .contentType("application/json").queryParams(parameters).
                body(getJsonBody).when()
                .post(resource_name);

        return r;
    }

    public static Properties loadproperties() throws IOException {

        Properties properties = new Properties();

        FileInputStream input = new FileInputStream("src/test/java/TestData/credentials.properties");
        properties.load(input);

        return properties;
    }

}
