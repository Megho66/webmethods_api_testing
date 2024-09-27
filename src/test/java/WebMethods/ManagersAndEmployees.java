package WebMethods;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasKey;

public class ManagersAndEmployees {

    @Test
public void GetManagerAndEmployees() throws IOException {

    baseURI = "https://apigwis.satrans.com.sa";
    useRelaxedHTTPSValidation();
    String get_access_token_output = getAccessToken();
    System.out.println(get_access_token_output);

    Map<String,String> params = new HashMap<>();
    params.put("employee_id","11030");


    // String access_token = get_access_token_output.

    Response Auth_User_Response = given().header("Authorization","Bearer " +get_access_token_output)
            .contentType("application/x-www-form-urlencoded")
            .queryParams(params)
            .when()
            .get("gateway/Portal-ActiveDirectory/1.0/ManagersAndEmployees");

    Auth_User_Response.then().statusCode(200);

    Auth_User_Response.then().assertThat().body("managers_and_direct_reports.error_document",hasKey("error_code"));
    Auth_User_Response.then().assertThat().body("managers_and_direct_reports",hasKey("error_document"));

    Assert.assertTrue(Auth_User_Response.getHeaders().hasHeaderWithName("Content-Encoding"));



}





    public static Properties loadproperties() throws IOException {
        Properties properties = new Properties();

        FileInputStream input = new FileInputStream("src/test/credentials.properties");
        properties.load(input);

        return properties;
    }
    public String getAccessToken() throws IOException {

        baseURI = "https://apigwis.satrans.com.sa";
        useRelaxedHTTPSValidation();



        Map<String, String> credentials = new HashMap<>();
        credentials.put("grant_type","client_credentials");

        Properties props = loadproperties();

        String Username =props.getProperty("Username");
        String Password =props.getProperty("Password");


        Response response = given().auth().preemptive()
                .basic(Username , Password)
                .contentType("application/x-www-form-urlencoded")
                .formParams(credentials)
                .when()
                .post("/invoke/pub.oauth/getToken/oauth2/1.0/resources/tokenservice");

        String  accessToken = response.jsonPath().getString("access_token");



        Map <String ,String> request_output = new HashMap<>();
        String status_code = Integer.toString(response.getStatusCode());

//        request_output.put("status_code" , status_code);
//        request_output.put("response_body" , accessToken );

        return accessToken ;
    }
}

