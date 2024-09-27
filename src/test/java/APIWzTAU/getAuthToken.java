package APIWzTAU;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class getAuthToken {

    @Test
    public void GetTest (){
        baseURI = "https://apigwis.satrans.com.sa";
        RestAssured.useRelaxedHTTPSValidation();

        Map<String, String> credentials = new HashMap<>();
        credentials.put("grant_type", "client_credentials");
        credentials.put("Username", "4b5cc5c6-a1ea-436e-9cfd-40813de9c62e");
        credentials.put("Password", "4b5cc5c6-a1ea-436e-9cfd-40813de9c62e");




        Response response = RestAssured.given().contentType("application/json").formParams(credentials)
                .when()
                .get("/your-invoke/pub.oauth/getToken/oauth2/1.0/resources/tokenservice");

        response.then().statusCode(200).log().all();


        String accessToken = response.jsonPath().getString("access_token");
        System.out.println("Access Token: " + accessToken);

    }

    }

