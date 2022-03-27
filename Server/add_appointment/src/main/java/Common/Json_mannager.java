package Common;

import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;

/*
json key
user_id, another_id, time, address
 */

public class Json_mannager {
    private JsonObject ob_recive;
    private Appointment temp_appointment;

    public void setOb(JsonObject ob) {
        this.ob_recive = ob;
    }

    public boolean receive_json_check_key() {
        if (!this.ob_recive.has("user_id"))
            return false;
        if (!this.ob_recive.has("another_id"))
            return false;
        if (!this.ob_recive.has("time"))
            return false;
        if (!this.ob_recive.has("address"))
            return false;
        save_member_info();
        return true;
    }

    private void save_member_info() {
        temp_appointment = new Appointment();
        temp_appointment.setMy_id(this.ob_recive.getAsJsonPrimitive("user_id").getAsString());
        temp_appointment.setAnother_id(this.ob_recive.getAsJsonPrimitive("another_id").getAsString());
        temp_appointment.setAppointment_address(this.ob_recive.getAsJsonPrimitive("address").getAsString());
        temp_appointment.setAppointment_time(this.ob_recive.getAsJsonPrimitive("time").getAsString());
        temp_appointment.default_appoint_agree();
    }

    public Appointment get_appointment() {
        return temp_appointment;
    }
}
