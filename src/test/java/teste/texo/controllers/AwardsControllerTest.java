package teste.texo.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kong.unirest.HttpResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AwardsControllerTest extends ControllerTest {
    @BeforeClass
    public static void setup() {
        new AwardsController();
    }

    @Test
    public void getAwards() {
        HttpResponse<JsonElement> response = get("awards", null);

        assertEquals(200, response.getStatus());

        JsonObject body = response.getBody().getAsJsonObject();

        assertNotNull(body.get("min"));
        assertTrue(body.get("min").getAsJsonArray().size() > 0);
        assertEquals("Bo Derek", body.get("min").getAsJsonArray().get(0).getAsJsonObject().get("producer").getAsString());

        assertNotNull(body.get("max"));
        assertTrue(body.get("max").getAsJsonArray().size() > 0);
        assertEquals("Matthew Vaughn", body.get("max").getAsJsonArray().get(0).getAsJsonObject().get("producer").getAsString());
    }

}