package Common;

import com.google.gson.JsonObject;

/*
json key
user_id, another_id, time, address
 */

public class Json_mannager {
    private JsonObject ob_recive;
    private String user_id;
    private String index_s;

    public void setOb(JsonObject ob) {
        this.ob_recive = ob;
    }

    public boolean receive_json_check_key() {
        if (!this.ob_recive.has("user_id"))
            return false;
        if (!this.ob_recive.has("index_s"))
            return false;
        save_member_info();
        return true;
    }

    private void save_member_info() {
        this.user_id = this.ob_recive.getAsJsonPrimitive("user_id").getAsString();
        this.index_s = this.ob_recive.getAsJsonPrimitive("index_s").getAsString();
    }

    public String getUser_id() {
        return user_id;
    }

    public String getIndex_s() { return index_s; }
}
