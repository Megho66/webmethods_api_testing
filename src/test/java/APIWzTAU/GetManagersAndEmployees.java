package APIWzTAU;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetManagersAndEmployees {
    @Test
    public void getOmar (){



            baseURI = "https://apigwis.satrans.com.sa";
        RestAssured.useRelaxedHTTPSValidation();

            given().auth().oauth2("af1aeedd95c44672b7b31a2c5c30c309db505309a3644148a331afd8453040b1").
                    get("/gateway/Portal-ActiveDirectory/1.0/ManagersAndEmployees?employee_id=11030").
                    then().
                    statusCode(200)
                    .body("managers_and_direct_reports.message.direct_report[12].'name'", equalTo("Omar Hassan")).log().all();
//                    .body("data.'first_name'" , hasItems("George","Lindsay")).log().all();


    }
}
