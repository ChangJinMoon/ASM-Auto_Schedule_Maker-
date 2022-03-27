import com.google.gson.JsonObject;
import netscape.javascript.JSObject;

import java.sql.Connection;

public class Main {
    public static JsonObject main(JsonObject args){

        Connection con;
        String result,result_detail,user_id;

        // 1.json_Data_check
        if(!args.has("id")){
            result = "101";
            result_detail = "Json_Data has not key(id)";
            return new Reply_manager().make_result(result,result_detail).return_json_result();
        }
        // 2.Connect db
        if((con = new Db_connector().try_Connect()) == null){
            result = "102";
            result_detail = "Db_connection failed";
            return new Reply_manager().make_result(result,result_detail).return_json_result();
        }

        user_id = args.getAsJsonPrimitive("id").getAsString();
        Query_sender q_s = new Query_sender();
        q_s.setCon(con);

        //3. Check table exist
        Create_table c_t = new Create_table().setUser_id(user_id);
        result_detail = q_s.table_already_exist(c_t.check_table_exist_query());

        if(result_detail.equals("exist") == false && result_detail.equals("empty") == false){
            //critical err
            result = "103";
            return new Reply_manager().make_result(result,result_detail).return_json_result();
        }

        if(result_detail.equals("exist")){
            //delete table
            result_detail =q_s.delete_daily_table(new Delete_table().setUser_id(user_id).delete_table_query());

            if(!result_detail.equals("success")){
                result = "104";
                return new Reply_manager().make_result(result,result_detail).return_json_result();
            }
        }
        result_detail ="";

        //4. Creat new daily_location table
        Daily_location_table dlc = new Daily_location_table().setUser_id(user_id);

        if(!(result_detail = q_s.creat_daily_table(dlc.getQuery())).equals("success")) {
            result = "105";
        }
        else{
            result = "0";
            result_detail = "Daily_table is remaked";
        }
        return new Reply_manager().make_result(result,result_detail).return_json_result();
    }
}
