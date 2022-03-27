import Common.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetAppointment {
    private String result_detail;
    private int result_flag;

    public Appointment init(ResultSet rs){
        Appointment temp_ap = new Appointment();

        try {
            while (rs.next()) {
                temp_ap.setMy_id(rs.getString("user_id"));
                temp_ap.setAnother_id(rs.getString("ant_id"));
                temp_ap.setAppointment_time(rs.getString("time"));
                temp_ap.setAppointment_address(rs.getString("address"));
                temp_ap.setIndex_s(rs.getString("index_s"));
                temp_ap.setSet_appoint_agree(rs.getInt("appoint_agree"));
            }
        }
        catch (SQLException e) {
            result_detail = e.toString();
            result_flag = 1;
            return null;
        }
        result_detail = "Success appointment load";
        result_flag = 0;
        return temp_ap;
    }

    public String getResult_detail() {
        return result_detail;
    }

    public int getResult_flag() {
        return result_flag;
    }
}
