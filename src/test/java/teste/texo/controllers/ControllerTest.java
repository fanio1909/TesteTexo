package teste.texo.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.BeforeClass;
import spark.Spark;

import java.util.Map;

public class ControllerTest {
    private final static String baseUrl = "http://localhost:9090/";

    @BeforeClass
    public static void setupSpark() {
        Spark.port(9090);
        Spark.before((request, response) -> response.type("application/json"));
        Spark.defaultResponseTransformer(object -> new Gson().toJson(object));
    }

    public HttpResponse<JsonElement> get(String endpoint, Map<String, Object> parametros) {
        return Unirest.get(baseUrl + endpoint)
                .header("accept", "application/json")
                .queryString(parametros)
                .asObject(rawResponse -> new Gson().fromJson(rawResponse.getContentAsString(), JsonElement.class));
    }
}
