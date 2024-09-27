package APIWzTAU;

import io.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;
import static io.restassured.matcher.RestAssuredMatchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class XMLSchemaValidation {

    @Test
    public void schemaValidation () throws IOException {

            File file = new File("./soapRequest/multiply.XML");
            File schema = new File("./soapRequest/calculator.xsd");

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
                    statusCode(200).log().all().
                    and().
                    body("//*:MultiplyResult.text()",equalTo("100")).
                    and().assertThat().body(matchesXsd(file));

    }

}
