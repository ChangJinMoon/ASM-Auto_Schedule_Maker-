import com.google.gson.JsonObject;

public class Json_mannager {
    JsonObject ob_recive;

    public void setOb(JsonObject ob) {
        this.ob_recive = ob;
    }

    boolean receive_json_check_key() {
        int count = 0;
        if (this.ob_recive.has("id"))
            count++;
        if (this.ob_recive.has("password"))
            count++;
        if (this.ob_recive.has("sex"))
            count++;
        if (count == 3)
            return true;
        return false;
    }

    Member save_member_info() {
        Member member = new Member();
        member.setId(this.ob_recive.getAsJsonPrimitive("id").getAsString());
        member.setPassword(this.ob_recive.getAsJsonPrimitive("password").getAsString());
        member.setSex(this.ob_recive.getAsJsonPrimitive("sex").getAsString());
        return member;
    }
}
