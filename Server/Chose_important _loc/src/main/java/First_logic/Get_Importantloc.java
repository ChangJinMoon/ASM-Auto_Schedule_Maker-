package First_logic;

import Common.Db_connector;
import Common.Today;
import Common.query_manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Get_Importantloc {
    private String id;
    private String result;
    private boolean result_bool = false;

    private Connection con;
    private ResultSet rs;
    private Today today = new Today();
    private query_manager query_manger = new query_manager();
    private List<Important> important_loc_list = new ArrayList<>();

    public void set_id_and_con(String id,Connection con){
        this.id = id;
        this.con = con;
    }

    public void init(){
        //오늘 날짜 받아오기
        today.set_current_time();
        today.set_date_kor_version();
        int now_day = today.getDay();

        //오늘 찍힌 importantloc에서 중요도가 2 이상인 장소 받아오기
        query_manger.setCon(con);
        rs  = query_manger.get_day_importantloc(this.id,now_day);
        get_data_importantloc(rs);
    }

    private void get_data_importantloc(ResultSet rs){
        try{
            Important temp;
            while(rs.next()){
                temp = new Important();
                temp.setK_address(rs.getString("k_address"));
                temp.setDay_of_week(rs.getString("day"));
                temp.setDay(rs.getInt("day_int"));
                temp.setImportant(rs.getInt("important"));
                important_loc_list.add(temp);
            }
            this.result = "Getting Important data success";
            this.result_bool = true;
        }catch (SQLException e){
            this.result = "get importantloc data error(get_data_importantloc):" + e.toString();
            this.result_bool = false;
        }catch (NullPointerException e1){
            this.result = "Result set is empty:" + e1.toString();
            this.result_bool = false;
        }
    }

    public String getResult() {
        return result;
    }

    public List<Important> getImportant_loc_list(){
        return this.important_loc_list;
    }

    public boolean isResult_bool() {
        return result_bool;
    }

    public static void main(String args[]){
        String id = "jin1004boy";
        List<Important> test_list;

        Get_Importantloc test = new Get_Importantloc();
        Db_connector db = new Db_connector();
        Connection con = db.try_Connect();

        test.set_id_and_con(id,con);
        test.init();

        if(test.isResult_bool()){
            test_list = test.getImportant_loc_list();
            for (int i = 0; i < test_list.size(); i++) {
                Important important = test_list.get(i);
                System.out.println(important.getK_address());
            }
        }
        else
            System.out.println(test.getResult());
    }
}
