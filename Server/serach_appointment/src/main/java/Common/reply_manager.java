package Common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
/*
 * 약속 자동 삭제 알고리즘 다시 한번 확인
 * */

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

    public JsonObject return_json_result_with_list(List<Appointment> appointmentList){
        JsonObject response = return_json_result();
        JsonArray appointment_list = new JsonArray();

        for (int i = 0; i < appointmentList.size(); i++) {
            JsonObject temp = new JsonObject();
            temp.addProperty("my_id",appointmentList.get(i).getMy_id());
            temp.addProperty("another_id",appointmentList.get(i).getAnother_id());
            temp.addProperty("time",appointmentList.get(i).getAppointment_time());
            temp.addProperty("address",appointmentList.get(i).getAppointment_address());
            temp.addProperty("index_s",appointmentList.get(i).getIndex_s());
            appointment_list.add(temp);
        }

        response.add("appointment",appointment_list);
        return response;
    }
}
