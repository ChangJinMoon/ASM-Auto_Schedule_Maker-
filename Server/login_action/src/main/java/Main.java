import com.google.gson.JsonObject;

import java.sql.Connection;

public class Main {
    public static JsonObject main(JsonObject args){

        Connection con;
        String user_id,user_password;
        Query_sender q_s = new Query_sender();
        Reply_manager r_m = new Reply_manager();

        // 1.json_Data_check
        if(!args.has("id")){
            r_m.setResult("101");
            return r_m.return_simple();
        }
        if(!args.has("password")){
            r_m.setResult("102");
            return r_m.return_simple();
        }
        user_id = args.getAsJsonPrimitive("id").getAsString();
        user_password = args.getAsJsonPrimitive("password").getAsString();

        // 2.Connect db
        if((con = new Db_connector().try_Connect()) == null){
            r_m.setResult("103");
            return r_m.return_simple();
        }

        // 3. Search id
        boolean answer = false;

        q_s.setCon(con);
        answer = q_s.search_query(new Group_query().return_check_id_query(user_id));

        if(answer){
            Member member = new Member();
            member.setId(user_id);
            member.setPassword(q_s.getAnswer_pass());
            if(member.login_access(user_password)){
                r_m.setResult("0");
                r_m.setResult_details("success");
            }
            else{
                r_m.setResult("1");
                r_m.setResult_details("Wrong password");
            }
        }
        else{
            r_m.setResult("2");
            r_m.setResult_details(q_s.getFaild_message());
        }
        return r_m.return_json_result();
    }
}
