package APIWzTAU;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

    public class intro {

        @Test
        public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

            given().
                    when().
                    log().all();
                    get("http://zippopotam.us/us/90210").
                    then().log().body();
//                    assertThat().contentType(ContentType.XML);
//                    assertThat().statusCode(200);
//                    body("places[0].'place name'", equalTo("Beverly Hills"));
        }

        @Test
        public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills1() {

            given().
                    when().
                    log().all();
            get("http://zippopotam.us/us/90210").
                    then().
                    assertThat().body("places.'place name'", hasSize(1) );
        }

        @Test
        public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills2() {

            given().
                    when().
            get("http://zippopotam.us/BE/1000")
                    .then().log().all()
                    .assertThat().statusCode(200)
                    .contentType(ContentType.JSON)
                    .extract().response();
//                    then().assertThat().body(("places.state") , not(hasItem("California")));
//                    assertThat().contentType(ContentType.XML);
//                    assertThat().statusCode(200);
//                    body("places[0].'place name'", equalTo("Beverly Hills"));
        }
    }

