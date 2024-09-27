package APIWzTAU;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class getAndPostExamples {

    @Test
    public void GetTest (){

        baseURI = "https://reqres.in/api";
        given().
                get("/users?page=2").
        then().
                statusCode(200).body("data[0].'first_name'", equalTo("Michael"))
                        .body("data.'first_name'" , hasItems("George","Lindsay")).log().all();

    }

    @Test
    public void postTest(){
        Map <String, Object > map = new HashMap<String, Object>();
        JsonObject object = new JsonObject();

        map.put("name","ahmed");
        map.put("job", "pharmacist");

        System.out.println(map);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(map);

        System.out.println(jsonRequest);

        baseURI = "http://localhost:3000";
         given().contentType(ContentType.JSON).
                 body(jsonRequest).
         when().post("/posts").
                 then().statusCode(201);

    }
}
