package Common;

import com.google.gson.JsonObject;

public class reply_manager {
    private String result;

    private String result_details;

    public reply_manager make_result(String result, String result_details) {
        this.result = result;
        this.result_details = result_details;
        return this;
    }

    public JsonObject return_json_result() {
        JsonObject response = new JsonObject();
        response.addProperty("payload", this.result);
        response.addProperty("result_details", this.result_details);
        return response;
    }
}
