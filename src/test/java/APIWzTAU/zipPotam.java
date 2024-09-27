package APIWzTAU;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class zipPotam {



    @Test
    public void get (){

        baseURI = "https://zippopotam.us";
        given().
                when().
                get("/us/90210")
                .then().
                assertThat().statusCode(200).
                body("places.'place name'",hasItem("Beverly Hills"));
    }
}
