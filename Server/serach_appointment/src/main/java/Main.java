import Common.DB_connector;
import Common.Json_mannager;
import Common.query_manager;
import Common.reply_manager;
import First_active.FirstAct;
import com.google.gson.JsonObject;

import java.sql.Connection;

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
        user_id = json_mannager.getUser_id();
        //1. 시간이 지난 약속 삭제 및 사용자가 가지고 있는 약속 추출
        FirstAct firstAct = new FirstAct();
        firstAct.setCon(con);
        firstAct.setUser_id(user_id);

        firstAct.init();
        int result_firstact = firstAct.getResult_flag();

        if(result_firstact == 1 ){
            //최신 약속 데이터가 없음
            result = "201";
            result_detail = "Recent appointment is empty";
            return (new reply_manager().make_result(result,result_detail).return_json_result());
        }

        //모든게 성공적으로 끝났을때 클라이언트에게 결과 전달
        result = "0";
        result_detail = "Get recent appointment success";

        return (new reply_manager()).make_result(result, result_detail).return_json_result_with_list(firstAct.get_final_apt_list());
    }
}
