import com.google.gson.JsonObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Test_main {
    public static void main(String[] args){

        Connection con;
        String result,result_detail,user_id;
        Query_sender q_s = new Query_sender();
        Reply_manager r_m = new Reply_manager();

        user_id = "jin1004boy";

        Today current = new Today().set_current_time();
        current.set_date_kor_version();

        // 2.Connect db
        if((con = new Db_connector().try_Connect()) == null){
            r_m.setResult("102");
            result_detail = "Db_connection failed";
           // System.out.println(r_m.return_json_result().toString()+":"+result_detail);
        }
        q_s.setCon(con);

        //check day time_table exist
        Get_Timetable query = new Get_Timetable().setUser_id(user_id);
        query.setDay_kor(current.getDay_kor_version());
        String result1 = q_s.search_query(query.make_search_query());
        System.out.println("Step1:"+result1);

        if(result1.equals("exist")){
            // time_table exist!!
            // get time_table data
            List<Time_table_data> data = new ArrayList<>();
            data = q_s.get_timeTable_query(query.make_get_timetable_query());
            if(data == null){
                r_m.setResult("104");
                System.out.println("err occur from get_timeTable method");
                //System.out.println(r_m.return_json_result().toString());
            }
            //
            else {
                r_m.setResult("0");
                r_m.setData(data);
                System.out.println(r_m.return_json_result("success"));
            }
        }
        else{
            //time_table not exist
            //return result 1
            r_m.setResult("1");
            System.out.println("TimeTable data(from db) not exist:"+result1);
            //System.out.println(r_m.return_json_result().toString());
        }
    }
}
