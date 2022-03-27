import Common.Appointment;
import Common.DB_connector;
import Common.query_manager;
import Common.reply_manager;

import java.sql.Connection;
import java.sql.ResultSet;

public class TestMain {
    public static void main(String args[]){
        DB_connector db_connector = new DB_connector();
        Connection con;

        String user_id = "jin1004boy";
        String index_s = "jin1_test_15_3";
        String result_detail;

        if ((con = db_connector.try_Connect()) == null) {
            result_detail = db_connector.getResult_details();
        }

        //1. index_s를 통해서 테이블 조회
        query_manager q_m = new query_manager();
        q_m.setCon(con);
        ResultSet rs  = q_m.get_appointment(index_s);
        if(rs == null){
            result_detail = "ResultSet is empty";
            System.out.println(result_detail);
            return;
        }
        //2. 테이블데이터 받아오기
        GetAppointment getAppointment = new GetAppointment();
        Appointment appointment = getAppointment.init(rs);
        if(getAppointment.getResult_flag() == 1){
            result_detail = getAppointment.getResult_detail();
            System.out.println(result_detail);
            return ;
        }
        //3.약속 동의 후 테이블 업데이트
        appointment.setSet_appoint_agree(1);
        if(q_m.update_agree_query(appointment) == 1){
            result_detail = "Failed update appointment data";
            System.out.println(result_detail);
            return;
        }
        result_detail = "Success update appointment data";
        System.out.println(result_detail);
    }
}
