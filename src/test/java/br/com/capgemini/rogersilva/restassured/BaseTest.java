package br.com.capgemini.rogersilva.restassured;

import static io.restassured.http.ContentType.JSON;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

@SpringBootTest
public abstract class BaseTest {

    @Value("${setup.base-uri}")
    private String baseURI;

    @Value("${setup.port}")
    private Integer port;

    @Value("${setup.key}")
    private String key;

    @Value("${setup.ts}")
    private Integer ts;

    @Value("${setup.hash}")
    private String hash;

    @Value("${setup.timeout}")
    private Long timeout;

    protected RequestSpecification requestSpec;

    protected ResponseSpecification responseSpec;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = baseURI;
        if (port != null) {
            RestAssured.port = port;
        }
        RestAssured.basePath = "/v1/public";
//        RestAssured.authentication = caso tenha que adicionar autenticação

        RequestSpecBuilder requestSpecbuilder = new RequestSpecBuilder();
        requestSpecbuilder.setContentType(JSON);
        requestSpec = requestSpecbuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectResponseTime(Matchers.lessThan(timeout), MILLISECONDS);
        responseSpec = responseSpecBuilder.build();
    }

    protected String endPointWithSuffix(String endPoint) {
        return endPoint.concat(String.format("?apikey=%s&ts=%s&hash=%s", key, ts, hash));
    }
}