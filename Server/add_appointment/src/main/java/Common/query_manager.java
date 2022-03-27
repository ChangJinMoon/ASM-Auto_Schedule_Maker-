package Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
/*
* 인코딩 변환 픽스
* */
public class query_manager {
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    Appointment appointment;

    public int check_appointment() {
        String sql = "select * from appointment where index_s = ?;";

        try {
            int size;
            this.pstmt = this.con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.pstmt.setString(1, appointment.get_appointment_index());
            this.rs = this.pstmt.executeQuery();
            rs.last();
            size = rs.getRow();
            if(size == 0)
                return 0;
            else {
                return 1;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return 99;
        }
    }

    public boolean update_appointment(){
        String sql = "insert into appointment values(?,?,?,?,?,?);";
        try{
            this.pstmt = con.prepareStatement(sql);
            this.pstmt.setString(1, appointment.get_appointment_index());
            this.pstmt.setString(2, appointment.getMy_id());
            this.pstmt.setString(3, appointment.getAnother_id());
            this.pstmt.setString(4, appointment.getAppointment_time());
            this.pstmt.setString(5, appointment.getAppointment_address());
            this.pstmt.setInt(6,appointment.getSet_appoint_agree());
            this.pstmt.executeUpdate();
            return true;
        }catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    public void setAppointment(Appointment ap) { this.appointment = ap;}

    public query_manager setCon_d(Connection con) {
        this.con = con;
        return this;
    }
}
