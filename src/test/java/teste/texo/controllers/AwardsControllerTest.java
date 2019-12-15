package teste.texo.controllers;

import com.google.gson.JsonElement;
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
    }

}