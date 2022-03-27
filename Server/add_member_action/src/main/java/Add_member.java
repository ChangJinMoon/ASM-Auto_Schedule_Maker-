import com.google.gson.JsonObject;
import java.sql.Connection;

public class Add_member {
    public static JsonObject main(JsonObject args) {
        Json_mannager json_mannager = new Json_mannager();
        json_mannager.setOb(args);
        String result = "none";
        String result_detail = "none";

        if (!json_mannager.receive_json_check_key()) {
            result = "101";
            result_detail = "received json data has error";
            return (new reply_manager()).make_result(result, result_detail).return_json_result();
        }

        Member member = json_mannager.save_member_info();
        Connection con;
        Db_connector db_connector = new Db_connector();
        if ((con = db_connector.try_Connect()) == null) {
            result = "102";
            result_detail = db_connector.getResult_details();
            return (new reply_manager()).make_result(result, result_detail).return_json_result();
        }

        query_manager query_manager = new query_manager();
        query_manager.setCon(con);
        if (!(result_detail = query_manager.id_check_query(member)).equals("success")) {
            result = "103";
            return (new reply_manager()).make_result(result, result_detail).return_json_result();
        }

        if ((result_detail = query_manager.add_member_query(member)).equals("success")) {
            result = "0";
        } else {
            result = "104";
        }
        return (new reply_manager()).make_result(result, result_detail).return_json_result();
    }
}
