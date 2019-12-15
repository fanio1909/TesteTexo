package teste.texo;

import com.google.gson.Gson;
import spark.Spark;
import teste.texo.controllers.AwardsController;


public class Main {
    public static void main(String[] args) {
        Spark.port(8080);
        Spark.before((request, response) -> response.type("application/json"));
        Spark.defaultResponseTransformer(object -> new Gson().toJson(object));

        new AwardsController();
    }
}
