package Common;

import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/*
json key
user_id, another_id, time, address
 */

public class Json_mannager {
    private JsonObject ob_recive;
    private String address;

    public void setOb(JsonObject ob) {
        this.ob_recive = ob;
    }

    public boolean receive_json_check_key() {
        if (!this.ob_recive.has("address"))
            return false;
        save_member_info();
        return true;
    }

    private void save_member_info() {
        String temp = this.ob_recive.getAsJsonPrimitive("address").getAsString();
        try {
            temp = new String(temp.getBytes("iso-8859-1"), "UTF-8");
        }catch (UnsupportedEncodingException e){}
        this.address = temp;
    }

    public String getUser_id() {
        return address;
    }
}
