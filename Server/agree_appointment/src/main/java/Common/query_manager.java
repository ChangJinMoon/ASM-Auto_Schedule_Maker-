package Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class query_manager {
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    Appointment appointment;

    public ResultSet get_appointment(String index_s) {
        String sql = "select * from appointment where index_s = ?;";

        try {
            int size;
            this.pstmt = this.con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.pstmt.setString(1, index_s);
            this.rs = this.pstmt.executeQuery();
            rs.last();
            size = rs.getRow();
            if(size == 0)
                return null;
            else {
                rs.beforeFirst();
                return rs;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    public int update_agree_query(Appointment appointment){
        String sql = "update appointment set appoint_agree = ? where index_s= ?;";
        try{
            this.pstmt = con.prepareStatement(sql);
            this.pstmt.setInt(1,appointment.getSet_appoint_agree());
            this.pstmt.setString(2,appointment.getIndex_s());
            System.out.println(this.pstmt.toString());
            this.pstmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
            return 1;
        }
        return 0;
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
