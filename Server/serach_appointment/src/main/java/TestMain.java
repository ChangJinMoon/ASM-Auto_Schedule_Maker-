import Common.DB_connector;
import Common.reply_manager;
import First_active.FirstAct;

import java.sql.Connection;

public class TestMain {

    public static void main(String args[]){
        DB_connector db_connector = new DB_connector();
        Connection con;

        String user_id = "test";
        String result = "none";
        String result_detail = "none";

        if ((con = db_connector.try_Connect()) == null) {
            result = "102";
            result_detail = db_connector.getResult_details();
            System.out.println("Error:"+result_detail);
            return ;
        }

        FirstAct firstAct = new FirstAct();
        firstAct.setCon(con);
        firstAct.setUser_id(user_id);

        firstAct.init();
        int result_firstact = firstAct.getResult_flag();

        if(result_firstact == 1 ){
            //최신 약속 데이터가 없음
            result = "201";
            result_detail = "Recent appointment is empty";
            System.out.println(result_detail);
            return;
        }

        //모든게 성공적으로 끝났을때 클라이언트에게 결과 전달
        result = "0";
        result_detail = "Get recent appointment success";

        System.out.println(result_detail);
        System.out.println(new reply_manager().make_result(result,result_detail).return_json_result_with_list(firstAct.get_final_apt_list()).toString());
    }
}
