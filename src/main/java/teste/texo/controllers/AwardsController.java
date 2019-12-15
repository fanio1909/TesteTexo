package teste.texo.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;
import spark.Spark;
import teste.texo.dao.AwardsDAO;
import teste.texo.dto.ProducerDTO;

public class AwardsController {
    private AwardsDAO awardsDAO = new AwardsDAO();

    public AwardsController() {
        Spark.get("/awards", this::getAwards);
    }

    private JsonObject getAwards(Request request, Response response) {
        ProducerDTO shortestIntervalAward = awardsDAO.getShortestIntervalAward();
        ProducerDTO longestIntervalAward = awardsDAO.getLongestIntervalAward();

        JsonObject result = new JsonObject();

        JsonArray shortestIntervalAwardList = new JsonArray();
        shortestIntervalAwardList.add(new Gson().toJsonTree(shortestIntervalAward));

        JsonArray longestIntervalAwardList = new JsonArray();
        longestIntervalAwardList.add(new Gson().toJsonTree(longestIntervalAward));

        result.add("min", shortestIntervalAwardList);
        result.add("max", longestIntervalAwardList);

        return result;
    }
}
