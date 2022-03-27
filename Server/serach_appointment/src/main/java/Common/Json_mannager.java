package Common;

import com.google.gson.JsonObject;

/*
json key
user_id, another_id, time, address
 */

public class Json_mannager {
    private JsonObject ob_recive;
    private String user_id;

    public void setOb(JsonObject ob) {
        this.ob_recive = ob;
    }

    public boolean receive_json_check_key() {
        if (!this.ob_recive.has("user_id"))
            return false;
        save_member_info();
        return true;
    }

    private void save_member_info() {
        this.user_id = this.ob_recive.getAsJsonPrimitive("user_id").getAsString();
    }

    public String getUser_id() {
        return user_id;
    }
}
