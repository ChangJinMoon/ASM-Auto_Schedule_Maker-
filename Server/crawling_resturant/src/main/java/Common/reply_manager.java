package Common;

import Etc.Store;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

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

    public JsonObject return_json_result_with_list(List<Store> storeList){
        JsonObject response = return_json_result();
        JsonArray appointment_list = new JsonArray();

        for (int i = 0; i < storeList.size(); i++) {
            JsonObject temp = new JsonObject();
            temp.addProperty("name",storeList.get(i).getName());
            temp.addProperty("score",storeList.get(i).getScore());
            temp.addProperty("address",storeList.get(i).getAddress());
            temp.addProperty("url",storeList.get(i).getUrl());
            appointment_list.add(temp);
        }

        response.add("store_list",appointment_list);
        return response;
    }
}
