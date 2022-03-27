import Common.DB_connector;
import Common.Json_mannager;
import Common.query_manager;
import Common.reply_manager;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static JsonObject main(JsonObject args) {
        DB_connector db_connector = new DB_connector();
        Connection con;

        Json_mannager json_mannager = new Json_mannager();
        json_mannager.setOb(args);


        String user_id;
        String result = "none";
        String result_detail = "none";

        if (!json_mannager.receive_json_check_key()) {
            result = "101";
            result_detail = "received json data has error";
            return (new reply_manager()).make_result(result, result_detail).return_json_result();
        }
        if ((con = db_connector.try_Connect()) == null) {
            result = "102";
            result_detail = db_connector.getResult_details();
            return (new reply_manager()).make_result(result, result_detail).return_json_result();
        }

        //1. 약속이 이미 존재하는지 확인
        query_manager queryManager = new query_manager();
        int check_appoint = 0;

        queryManager.setCon(con);
        queryManager.setAppointment(json_mannager.get_appointment());
        check_appoint = queryManager.check_appointment();

        if(check_appoint == 1){
            //이미 약속이 지정되어 있을 때
            result = "201";
            result_detail = "Appointment is already exist";
            return (new reply_manager()).make_result(result, result_detail).return_json_result();
        }

        //2. 존재 하지 않으면 데이터 저장
        boolean result_upadate = false;

        if((result_upadate = queryManager.update_appointment()) == false){
            //업데이트 실패시
            result = "202";
            result_detail = "Update appointment is failed";
            return (new reply_manager()).make_result(result, result_detail).return_json_result();
        }

        //모든게 성공적으로 끝났을때 클라이언트에게 결과 전달
        result = "0";
        result_detail = "Chose important loc activity is success";

        return (new reply_manager()).make_result(result, result_detail).return_json_result();
    }
}
