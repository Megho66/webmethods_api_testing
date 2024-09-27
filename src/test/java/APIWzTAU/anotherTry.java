package APIWzTAU;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;

public class anotherTry {


    @Test
    public void postToken() {
        baseURI = "https://apigwis.satrans.com.sa";
        RestAssured.useRelaxedHTTPSValidation();

        Map<String, String> credentials = new HashMap<>();
        credentials.put("grant_type","client_credentials");
//        credentials.put("Username","4b5cc5c6-a1ea-436e-9cfd-40813de9c62e");
//        credentials.put("Password", "d1278c48-c1f2-42c1-8cfc-080ef94b68fd");
        System.out.println(credentials);
        String Username ="4b5cc5c6-a1ea-436e-9cfd-40813de9c62e";
        String Password = "d1278c48-c1f2-42c1-8cfc-080ef94b68fd";
//        String encodedCredentials = Base64.encodeBase64String(credentials1.getBytes());

        Response response = RestAssured.given().contentType("application/x-www-form-urlencoded").auth().preemptive()
                .basic(Username , Password)
                .formParams(credentials)
//                .body("client_credentials")
//                .header("Authorization", credentials)
                .when()
                .post("/invoke/pub.oauth/getToken/oauth2/1.0/resources/tokenservice");

//        String accessToken = response.jsonPath().getString("access_token");

        response.then().statusCode(200).log().all();
    }
}
