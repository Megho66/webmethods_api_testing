package APIWzTAU;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class testTest {

@Test
    public void test_get_auth_user (){

        baseURI = "https://apigwis.satrans.com.sa/";
        given().contentType("application/json").
                get("   ").
                then().
                statusCode(200).body("data[0].'full_name'", equalTo("TestHR"))
               .log().all();

    }
}
