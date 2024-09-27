package APIWzTAU;

import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class RefreshToken {

    @Test
    public void getAuthGemini (){

        baseURI = "https://apigwis.satrans.com.sa";
        useRelaxedHTTPSValidation();

        String credentials ="4b5cc5c6-a1ea-436e-9cfd-40813de9c62e:d1278c48-c1f2-42c1-8cfc-080ef94b68fd";
        String encodedCredentials = Base64.encodeBase64String(credentials.getBytes());

        Response authResponse = given().header("Authorization","bearer", encodedCredentials).
                contentType("application/json").when().header("grant_type","client_credentials").
                post("/invoke/pub.oauth/getToken/oauth2/1.0/resources/tokenservice");

        authResponse.then().statusCode(200).log().all();

//        String accessToken = authResponse.jsonPath("application/json;charset=UTF-8).getString("access_token");

        //authResponse.then().statusCode(200).log().all();

    }
@Test
    public void aiHaga (){




    baseURI = "https://apigwis.satrans.com.sa";
    useRelaxedHTTPSValidation();
        Map<String,String> get_access_token_output = getAccessToken();
//        System.out.println(get_access_token_output);
//        String body= get_access_token_output.get("response_body");





        String access_token = get_access_token_output.get("");
        //String status_code = get_access_token_output.get(0);
    //System.out.println(access_token);
    //System.out.println(status_code);




        Map<String,String> params = new HashMap<>();
        params.put("email","TestHR@PTCO.COM.SA");
        params.put("password","cz644x5P");
        params.put("company","PTC");

        Response ApiResponse = given().header("authorization","bearer" + access_token)
                .contentType("application/x-www-form-urlencoded")
                .queryParams(params)
                .when()
                .get("/gateway/Portal-ActiveDirectory/1.0/AuthUser?email=TestHR@PTCO.COM.SA&password=cz644x5P&company=PTC");

        ApiResponse.then().statusCode(200).body(".body.emplyee_info.full_name",equalTo("TestHR"));


    }


    @Test
    public Map<String, String> getAccessToken(){

        baseURI = "https://apigwis.satrans.com.sa";
        useRelaxedHTTPSValidation();

        Map<String, String> credentials = new HashMap<>();
        credentials.put("grant_type","client_credentials");

        System.out.println(credentials);

        String Username ="4b5cc5c6-a1ea-436e-9cfd-40813de9c62e";
        String Password = "d1278c48-c1f2-42c1-8cfc-080ef94b68fd";


        Response response = given().auth().preemptive()
                .basic(Username , Password)
                .contentType("application/x-www-form-urlencoded")
                .formParams(credentials)
                .when()
                .post("/invoke/pub.oauth/getToken/oauth2/1.0/resources/tokenservice");

        String accessToken = response.getBody().prettyPrint();

//        System.out.println(accessToken);

        Map <String ,String> request_output = new HashMap<>();
        String status_code = Integer.toString(response.getStatusCode());
        System.out.println(status_code);
        request_output.put("status_code" , status_code);
        request_output.put("response_body" , response.getBody().prettyPrint() );

        System.out.println(request_output);

        response.getStatusCode();
//        response.then().statusCode(200).log().all();

        return request_output ;
    }

}
