package APIWzTAU;

import io.restassured.RestAssured;
import static   io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

public class TestOne {

    @Test
    public void test_1(){



        Response response =  get("https://reqres.in/api/users?page=2/");

        System.out.println(response.getStatusCode());
        System.out.println(response.getTime());
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-type"));

        int statusCode = response.statusCode();

        Assert.assertEquals(statusCode, 200);




    }

    @Test
    public void Test_2(){

        baseURI = "https://reqres.in/api";
                  given().
                get("/users?page=2").
                          then().statusCode(200).
                          body("data[1].id" , equalTo(8));
    }


    @Test
    public void Test_3 (){

        baseURI = "https://reqres.in/api";

        given().
                get("/users?page=2")
                .then().statusCode(200).body("data[5].'last_name'", equalTo("Howell")).log().all();
    }
}
