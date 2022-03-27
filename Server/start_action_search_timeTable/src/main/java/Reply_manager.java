import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Reply_manager {
    private String result;
    private String day;
    private boolean was_called = false;

    private JsonObject result_details;
    private List<Time_table_data> data = new ArrayList<>();

    public void setResult(String result) {
        this.result = result;
    }


    public void setData(List<Time_table_data> data) {
        this.data = data;
        was_called = true;
    }


    JsonObject return_json_result(String result_de) {
        JsonObject response = new JsonObject();
        JsonArray result_array = new JsonArray();
        response.addProperty("payload", this.result);
        response.addProperty("result_detail",result_de);

        for (int i = 0; i < data.size(); i++) {
            JsonObject result = new JsonObject();
            result.addProperty("day",data.get(i).getDay());
            result.addProperty("location",data.get(i).getLocation());
            result.addProperty("arrive",data.get(i).getArrive().toString());
            result.addProperty("depart",data.get(i).getDepart().toString());
            result.addProperty("day_string",data.get(i).getDay_string());
            result_array.add(result);
        }
        response.add("result",result_array);
        return response;
    }

    JsonObject return_simple(){
        JsonObject response = new JsonObject();
        response.addProperty("payload:", this.result);
        return response;
    }
}
