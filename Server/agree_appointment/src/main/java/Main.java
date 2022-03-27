import Common.*;
import com.google.gson.JsonObject;
import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.ResultSet;

public class Main {
    public static JsonObject main(JsonObject args) {
        DB_connector db_connector = new DB_connector();
        Connection con;

        Json_mannager json_mannager = new Json_mannager();
        json_mannager.setOb(args);

        String user_id;
        String index_s;
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
        user_id = json_mannager.getUser_id();
        index_s = json_mannager.getIndex_s();

        //1. index_s를 통해서 테이블 조회
        query_manager q_m = new query_manager();
        q_m.setCon(con);
        ResultSet rs = q_m.get_appointment(index_s);
        if(rs == null){
            result = "201";
            result_detail = "Resultset is empty";
            return new reply_manager().make_result(result,result_detail).return_json_result();
        }
        //2. 테이블데이터 받아오기
        GetAppointment getAppointment = new GetAppointment();
        Appointment appointment = getAppointment.init(rs);
        if(getAppointment.getResult_flag() == 1){
            result = "202";
            result_detail = getAppointment.getResult_detail();
        }
        //3.약속 동의 후 테이블 업데이트
        appointment.setSet_appoint_agree(1);
        if(q_m.update_agree_query(appointment) == 1){
            result = "203";
            result_detail = "Failed update appointment data";
            return new reply_manager().make_result(result,result_detail).return_json_result();
        }
        result = "0";
        result_detail = "Success update appointment data";

        return new reply_manager().make_result(result,result_detail).return_json_result();
    }
}
