package Common;

import com.google.gson.JsonObject;

public class Respose {
    JsonObject response;

    public JsonObject reponse_maker(String payload,String result,String result2,String db_connect){
        response = new JsonObject();
        response.addProperty("payload",payload);
        response.addProperty("result", result);
        response.addProperty("result2", result2);
        response.addProperty("db_connect", db_connect);
        return response;
    }
}
