import com.google.gson.JsonObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static JsonObject main(JsonObject args){

        Connection con;
        String result,result_detail,user_id;
        Query_sender q_s = new Query_sender();
        Reply_manager r_m = new Reply_manager();

        // 1.json_Data_check
        if(!args.has("id")){
            r_m.setResult("101");
            return r_m.return_simple();
        }
        user_id = args.getAsJsonPrimitive("id").getAsString();

        // 2.Connect db
        if((con = new Db_connector().try_Connect()) == null){
            r_m.setResult("102");
            return r_m.return_simple();
        }
        q_s.setCon(con);

        // 3.set current day
        Today current = new Today().set_current_time();
        current.set_date_kor_version();
        try {
            // 4.check day time_table exist
            Get_Timetable query = new Get_Timetable().setUser_id(user_id);
            query.setDay_kor(current.getDay_kor_version());
            result_detail = q_s.search_query(query.make_search_query());
            if (result_detail.equals("exist")) {
                // get time_table data
                List<Time_table_data> data = new ArrayList<>();
                data = q_s.get_timeTable_query(query.make_get_timetable_query());
                if (data == null) {
                    //err occur from get_timeTable method
                    r_m.setResult("104");
                    result_detail = "err occur from get_timeTable method";
                } else {
                    //successfully get timeTable data
                    r_m.setResult("0");
                    r_m.setData(data);
                }
            } else {
                //time_table not exist
                r_m.setResult("1");
                result_detail = "time_table not exist";
            }
        }catch (Exception e){
            result_detail = e.toString();
        }

        return r_m.return_json_result(result_detail);
    }
}
