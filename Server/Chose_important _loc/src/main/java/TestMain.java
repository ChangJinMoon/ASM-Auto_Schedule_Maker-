import Common.Db_connector;
import Common.reply_manager;
import First_logic.Get_Importantloc;
import First_logic.Important;
import Second_logic.Get_importantloc_time;
import Third_logic.Update_time_table;

import java.sql.Connection;
import java.util.List;

public class TestMain {

    public static void main(String args[]){
        Db_connector db_connector = new Db_connector();
        Connection con;

        List<Important> important_list;
        List<Important> final_important_list;

        String user_id = "jin1004boy";
        String result = "none";
        String result_detail = "none";

        if ((con = db_connector.try_Connect()) == null) {
            result = "102";
            result_detail = db_connector.getResult_details();
            System.out.println("final result code:"+result);
            System.out.println("final result_detail:"+result_detail);
            return;
        }

        //Main logic start
        //1. Importantloc 테이블에서 주요 장소 받아오기
        Get_Importantloc get_importantloc = new Get_Importantloc();
        get_importantloc.set_id_and_con(user_id,con);
        get_importantloc.init();
        if(get_importantloc.isResult_bool() == false){
            result = "201";
            result_detail = get_importantloc.getResult();
            System.out.println("final result code:"+result);
            System.out.println("final result_detail:"+result_detail);
            return;
        }

        //오늘의 중요 장소가 담겨 있는 리스트 저장
        important_list = get_importantloc.getImportant_loc_list();

        //2. 걸러낸 장소의 출발, 도착시간 얻기
        Get_importantloc_time get_importantloc_time = new Get_importantloc_time();
        get_importantloc_time.set_id_and_con(user_id,con);
        get_importantloc_time.setImportant_loc_list(important_list);
        get_importantloc_time.init();
        if(get_importantloc_time.isResult_bool() == false){
            result = "202";
            result_detail = get_importantloc.getResult();
            System.out.println("final result code:"+result);
            System.out.println("final result_detail:"+result_detail);
            return;
        }
        //오늘의 중요 장소에 출발 도착시간을 저장한 리스트
        final_important_list = get_importantloc_time.getFinal_important_list();

        //3. 최종으로 작성된 중요장소를 시간표로 반환 후 db에 업데이트
        Update_time_table update_time_table = new Update_time_table();
        update_time_table.setting(con,user_id);
        update_time_table.setFinal_important_list(final_important_list);
        update_time_table.init();
        if(update_time_table.isResult_bool() == false){
            result = "203";
            result_detail = update_time_table.getResult();
            System.out.println("final result code:"+result);
            System.out.println("final result_detail:"+result_detail);
            return;
        }

        //모든게 성공적으로 끝났을때 클라이언트에게 결과 전달
        result = "0";
        result_detail = "Chose important loc activity is success";
        System.out.println("final result code:"+result);
        System.out.println("final result_detail:"+result_detail);
    }
}
