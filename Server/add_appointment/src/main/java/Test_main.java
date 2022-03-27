import Common.Appointment;
import Common.DB_connector;
import Common.query_manager;
import Common.reply_manager;

import java.sql.Connection;

public class Test_main {

    public static void main(String args[]){
        DB_connector db_connector = new DB_connector();
        Connection con;

        String my_id = "jin1004boy";
        String ant_id = "test";
        String time = "2021-12-25 17:30:00";
        String address = "서울특별시 광진구 구의동 251-177번지";

        if ((con = db_connector.try_Connect()) == null) {
            System.out.println("db_connect error");
        }

        Appointment temp = new Appointment();
        temp.setMy_id(my_id);
        temp.setAnother_id(ant_id);
        temp.setAppointment_time(time);
        temp.setAppointment_address(address);
        temp.default_appoint_agree();

        query_manager queryManager = new query_manager();
        queryManager.setCon(con);
        queryManager.setAppointment(temp);

        //1. 약속이 이미 존재하는지 확인
        int check_appoint = 0;
        check_appoint = queryManager.check_appointment();

        if(check_appoint == 1){
            //이미 약속이 지정되어 있을 때
            System.out.println("Appointment is already exist");
        }

        //2. 존재 하지 않으면 데이터 저장
        boolean result_upadate = false;
        if((result_upadate = queryManager.update_appointment()) == false){
            //업데이트 실패시
            System.out.println("Update appointment is failed");
        }

        System.out.println("Chose important loc activity is success");
    }
}
