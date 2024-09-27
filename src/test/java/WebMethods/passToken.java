package WebMethods;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class passToken {



    @Test
    public void passBearerToken () throws IOException {

        Properties properties = loadproperties();
        baseURI= properties.getProperty("getTokenBaseURI");

        useRelaxedHTTPSValidation();
        Response get_token_response = getAccessToken();

        get_token_response.then().statusCode(200);
        get_token_response.then().body("",hasKey("access_token"));




        String  get_access_token_output = get_token_response.jsonPath().getString("access_token");



        System.out.println(get_access_token_output);

        Map<String,String> params = new HashMap<>();
        params.put("email","TestHR@PTCO.COM.SA");
        params.put("password","cz644x5P");
        params.put("company","PTC");

       // String access_token = get_access_token_output.

        Response Auth_User_Response = given().header("Authorization","Bearer " +get_access_token_output)
                .contentType("application/x-www-form-urlencoded")
                .queryParams(params)
                .when()
                .get("/gateway/Portal-ActiveDirectory/1.0/AuthUser");

        Auth_User_Response.then().statusCode(200);

        Auth_User_Response.then().assertThat().body("body.error_document",hasKey("error_code"));

        Assert.assertTrue(Auth_User_Response.getHeaders().hasHeaderWithName("Content-Encoding"));


//                response.getHeaders():  todo >> Retrieves all headers.
//                response.getHeader("Header-Name"):  todo >>Retrieves the value of a specific header.
//                response.getHeaders().hasHeaderWithName("Header-Name"): todo >> Checks if the specified header exists.





    }

    @Test
    public void Get_Employee_Info() throws IOException{
        Properties properties= loadproperties();

        Response get_access_token_response = getAccessToken();
        String access_token = get_access_token_response.jsonPath().getString("access_token");

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("company_id","1");

        Response get_employee_info_response = GET_API(properties.getProperty("resource_ERP_GetEmployeeInfo"),access_token,parameters);

        get_employee_info_response.then().statusCode(200).body(".employee_info",hasKey("error_document"));

    }
    public static Properties loadproperties() throws IOException {
        Properties properties = new Properties();

        FileInputStream input = new FileInputStream("src/test/credentials.properties");
        properties.load(input);

        return properties;
    }



    public Response getAccessToken() throws IOException {

        baseURI = "https://apigwis.satrans.com.sa";
        useRelaxedHTTPSValidation();



        Map<String, String> credentials = new HashMap<>();
        credentials.put("grant_type","client_credentials");

        Properties props = loadproperties();

        String Username =props.getProperty("Username");
        String Password =props.getProperty("Password");

        System.out.println(Username);
        System.out.println(Password);

        Response response = given().auth().preemptive()
                .basic(Username , Password)
                .contentType("application/x-www-form-urlencoded")
                .formParams(credentials)
                .when()
                .post("/invoke/pub.oauth/getToken/oauth2/1.0/resources/tokenservice");

        String  accessToken = response.jsonPath().getString("access_token");

        System.out.println(accessToken);

        Map <String ,String> request_output = new HashMap<>();
        String status_code = Integer.toString(response.getStatusCode());

        request_output.put("status_code" , status_code);
        request_output.put("response_body" , accessToken );

        return response ;
    }

    public Response GET_API(String resource_name,String access_token,HashMap<String,String> parameters) throws IOException {
        Properties properties= loadproperties();
        baseURI =properties.getProperty("PORTAL_ERP_BASE_URL");
        Response r= given().header("Authorization","Bearer " +access_token)
                .contentType("application/json")
                .queryParams(parameters)
                .when()
                .get(resource_name);

        return r;
    }
}
