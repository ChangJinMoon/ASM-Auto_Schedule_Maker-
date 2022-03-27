package Third_logic;

import Common.Today;
import Common.query_manager;
import First_logic.Important;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Update_time_table {
    private query_manager query;
    private Today today;
    private List<Time_table> timeTableList = new ArrayList<>();
    private List<Time_table> final_timeTableList = new ArrayList<>();
    private List<Important> final_important_list;

    private String id;
    private String result;
    private boolean result_bool = false;

    private Connection con;
    private ResultSet rs;

    public void init(){
        today =new Today().set_current_time();
        today.set_date_kor_version();

        query = new query_manager();
        query.setCon(con);
        //디비에서 시간표 데이터 받아오기
        rs = query.get_time_table_data(this.id, today.getDay_kor_version());
        get_time_table(rs);

        //if(this.result_bool == false){
            //System.out.println(result);
            //return;
        //}
        //받아온 시간표를 최신 데이터로 업데이트
        change_time_table(timeTableList.size());
        //작성된 시간표가 저장 되어있는 final_timeTableList를 db에 삽입
        update_time_table_db();
    }

    private void get_time_table(ResultSet rs){
        try{
            Time_table temp;
            while (rs.next()){
                temp = new Time_table();
                temp.setId(rs.getString("id"));
                temp.setDay(rs.getInt("day"));
                temp.setLocation(rs.getString("location"));
                temp.setArrive(rs.getString("arrive"));
                temp.setDepart(rs.getString("depart"));
                temp.setDay_string(rs.getString("day_string"));
                timeTableList.add(temp);
            }
            this.result_bool = true;
            this.result = "get_time_table success";
        }catch (SQLException e){
            this.result_bool = false;
            this.result = "get_time_table sql error:"+e;
        }catch (NullPointerException e){
            this.result_bool = true;
            this.result = "result set is empty:"+e;
        }
    }

    private void change_time_table(int case_num){
        if(case_num != 0){//이전 타임테이블이 작성 되어 있을경우
            //제일 최근에 작성된 시간표를 2주전 데이터로 명시
            for (int i = 0; i < timeTableList.size(); i++) {
                Time_table temp;
                 if(get_time_table_day(timeTableList.get(i))){
                     temp = timeTableList.get(i);
                     temp.setDay(2);
                     final_timeTableList.add(temp);
                 }
            }
        }
        //새로작성된 테이블 삽입
        insert_new_time_table();
    }

    private boolean get_time_table_day(Time_table time_table){
        int day = time_table.getDay();
        if(day == 1){
            return true;
        }
        else{
            return false;
        }
    }

    private void insert_new_time_table(){
        for (int i = 0; i < final_important_list.size(); i++) {
            insert_time_table(final_important_list.get(i).getK_address()
            ,final_important_list.get(i).getArriveTime()
            ,final_important_list.get(i).getDepartTime()
            ,final_important_list.get(i).getDay_of_week());
        }
    }

    private void insert_time_table(String location, Date arrive, Date depart, String day_of_week){
        Time_table temp = new Time_table();
        try {
            temp.setId(this.id);
            temp.setDay(1);
            temp.setLocation(location);
            temp.setArrive(arrive);
            temp.setDepart(depart);
            temp.setDay_string(day_of_week);
            final_timeTableList.add(temp);
        }catch (NullPointerException e){
            result_bool = false;
            result = "insert_new_time_table error: null Pointer";
        }
    }

    private void update_time_table_db(){
        boolean check_delete = false;
        boolean check_update = false;

        //디비 테이블에 있는 2주전 데이터 삭제
        check_delete = query.delete_time_table_week_two(this.id,today.getDay_kor_version());
        if(check_delete == false){
            result_bool = false;
            result = "delete_time_table_week_two sql error";
            return;
        }

        //새로운 데이터 삽입
        for (int i = 0; i < final_timeTableList.size(); i++) {
            check_update = query.update_time_table_data(final_timeTableList.get(i));
            if(check_update == false){
                result_bool = false;
                result = "update_time_table_data sql error("+i+" index)";
            }
        }
    }

    public String getResult() { return result; }

    public boolean isResult_bool() { return result_bool; }

    public void setting(Connection con,String id){
        this.con = con;
        this.id = id;
    }

    public void setFinal_important_list(List<Important> final_important_list) {
        this.final_important_list = final_important_list;
    }
}
