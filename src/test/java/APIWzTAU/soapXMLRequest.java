package APIWzTAU;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import io.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class soapXMLRequest {

    @Test
    public void validateSoapXMLRequest() throws IOException {

        File file = new File("./soapRequest/add.XML");

        // hena bae3mel if statement to make sure
        // that file exists and i do not proceed without making sure that file actually exist
        if (file.exists()) {
            System.out.println(">> file exists");
        }

        FileInputStream XMLFileInput = new FileInputStream((file));
        // b3d kda 3ayz ageeb el body as a string using IOUtils from apache commos dependency
        String requestBody = IOUtils.toString(XMLFileInput, "utf-8");

        baseURI = "http://www.dneonline.com";

        given().
                contentType("text/XML").
                accept(ContentType.XML).
                body(requestBody).
                when().
                post("/calculator.asmx")
                .then().statusCode(200).log().all();


    }


    @Test
    public void validateSoapXMLRequest1() throws IOException {

        File file = new File("./soapRequest/divide.XML");

        // hena bae3mel if statement to make sure
        // that file exists and i do not proceed without making sure that file actually exist
        if (file.exists()) {
            System.out.println(">> file exists");
        }

        FileInputStream XMLFileInputDivide = new FileInputStream((file));
        // b3d kda 3ayz ageeb el body as a string using IOUtils from apache commos dependency
        String requestBody = IOUtils.toString(XMLFileInputDivide, "utf-8");

        baseURI = "http://www.dneonline.com";

        given().
                contentType("text/XML").
                accept(ContentType.XML).
                body(requestBody).
                when().
                post("/calculator.asmx")
                .then().statusCode(200).log().all().
                and().body("//*:DivideResult.text()",equalTo("5"));
    }

    @Test
    public void validateSoapXMLRequest2() throws IOException {

        File file = new File("./soapRequest/multiply.XML");

        // hena bae3mel if statement to make sure
        // that file exists and i do not proceed without making sure that file actually exist
        if (file.exists()) {
            System.out.println(">> file exists");
        }

        FileInputStream XMLFileInputMultiply = new FileInputStream((file));
        // b3d kda 3ayz ageeb el body as a string using IOUtils from apache commos dependency
        String requestBody = IOUtils.toString(XMLFileInputMultiply, "utf-8");

        baseURI = "http://www.dneonline.com";

        given().
                contentType("text/XML").
                accept(ContentType.XML).
                body(requestBody).
        when().
                post("/calculator.asmx").
        then().
                statusCode(200).
                    log().all().
                and().
                body("//*:MultiplyResult.text()",equalTo("100"));
        // hena ana katbt "and" for further assertion w b assert 3la haga fel body fa lazm adelo
        // el x path w b2ollo equals to "value"
        //Todo >> xpath form hena hya " //*:blabla.text()"  w dont forget the brackets
    }
}