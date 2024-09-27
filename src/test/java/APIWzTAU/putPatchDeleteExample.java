package APIWzTAU;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class putPatchDeleteExample {

    @Test
    public void PutTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "ahmed");
        map.put("job", "pharmacist");

        System.out.println(map);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(map);

        System.out.println(jsonRequest);


        baseURI = "https://reqres.in/api";
         given().contentType(ContentType.JSON).
                body(jsonRequest).
         when().
                put("/users/2").
         then().
                statusCode(200)
         .log().all();
    }

    @Test
    public void deleteTest() {



        baseURI = "http://localhost:3000";

                when().
                delete("/posts/67e3").
                then().
                statusCode(200)
                .log().all();
    }
    }
