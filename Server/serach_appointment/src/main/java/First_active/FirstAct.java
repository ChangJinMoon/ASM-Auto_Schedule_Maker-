package First_active;

import Common.Appointment;
import Common.query_manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FirstAct {
    private Connection con;
    private String user_id;
    private int result_flag;
    private List<Appointment> appointmentList = new ArrayList<>();


    public void init(){
        //appoint table에서 현재시간에서 부터 1시간이상 지난 데이터 삭제
        //1.현재 날짜 데이터 받아오기
        Date_d date_mangement = new Date_d();
        LocalDateTime now = date_mangement.get_now_time();
        Stack<Integer> remove_index = new Stack<>();

        //2.약속 시간표 테이블에서 데이터 받아오기
        query_manager q_m = new query_manager();
        q_m.setCon(this.con);
        ResultSet rs = q_m.get_appointment(user_id);
        if(rs == null) {
            result_flag = 1;
            return;
        }
        //3. 이미 지난 약속시간 데이터 삭제
        appointmentList = return_apptList(rs);

        for (int i = 0; i < appointmentList.size(); i++) {
            boolean result = false;
            result = date_mangement.date_compare_1hour(now,appointmentList.get(i).getTime_format());
            if(result) {
                //delete data
                q_m.delete_appointment(appointmentList.get(i).getIndex_s());
                remove_index.push(i);
            }
        }
        //4.시간이 지난 약속객체 삭제
        if(remove_index.size() != 0) {
            int remove = 0;
            for (int i = 0; i < remove_index.size(); i++) {
                remove = remove_index.pop();
                appointmentList.remove(remove);
            }
        }
        if(appointmentList.size() == 0) {
            result_flag = 1;
            return;
        }

        result_flag = 0;
    }

    private List<Appointment> return_apptList(ResultSet rs){
        List<Appointment> temp_list = new ArrayList<>();
        try {
            Appointment temp_ap;
            while (rs.next()) {
                temp_ap = new Appointment();
                temp_ap.setMy_id(rs.getString("ant_id"));
                temp_ap.setAnother_id(rs.getString("user_id"));
                temp_ap.setAppointment_time(rs.getString("time"));
                temp_ap.setAppointment_address(rs.getString("address"));
                temp_ap.setIndex_s(rs.getString("index_s"));
                temp_ap.setSet_appoint_agree(rs.getInt("appoint_agree"));
                temp_list.add(temp_ap);
            }
        }catch (NullPointerException | SQLException e){
            return null;
        }
        return temp_list;
    }

    public void setUser_id(String user_id) { this.user_id = user_id; }
    public void setCon(Connection con) { this.con = con; }
    public List<Appointment> get_final_apt_list(){
        return appointmentList;
    }

    public int getResult_flag() {
        return result_flag;
    }
}
