import About_importantloc.Select_important_loc;
import About_importantloc.Get_term_address;
import Common.Today;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectLocation {
    ResultSet rs;
    PreparedStatement pstmt = null;

    String sql;
    String err;
    String temp_importantloc;
    String today_kor;
    int day;

    int count_important = 0;

    boolean conn = false;

    public SelectLocation(Connection con, String id, int time_term, int now_count) {
        // Get DAY DATA
        Today today = new Today().set_current_time();
        today.set_date_kor_version();
        today_kor = today.getDay_kor_version();
        day = today.getDay();

        try {
             // 최근 6개의 주소 리스트로 저장
             Get_term_address gta = new Get_term_address().setUser_id(id).setNow_count(now_count);
             gta.setCon(con);
             gta.setTerm(time_term);

             if(gta.init()) {
                 Select_important_loc sil = new Select_important_loc().setK_address(gta.getK_address());
                 sil.setTime_term(time_term);
                 sil.setMax_count_term(4);

                 this.temp_importantloc = sil.return_max_overlap_k_address();

                 if (!temp_importantloc.equals("none")) {
                     this.sql = "select important from importantloc where id = ? and k_address = ? and day_int = ?";
                     this.pstmt = con.prepareStatement(this.sql);
                     this.pstmt.setString(1, id);
                     this.pstmt.setString(2, temp_importantloc);
                     this.pstmt.setInt(3,day);
                     this.rs = this.pstmt.executeQuery();

                     if (this.rs.next()) {
                         this.count_important = this.rs.getInt("important");

                         this.sql = "update importantloc set important = ? where id = ? and k_address = ? and day_int = ?";
                         this.pstmt = con.prepareStatement(this.sql);
                         this.count_important++;
                         this.pstmt.setInt(1, this.count_important);
                         this.pstmt.setString(2, id);
                         this.pstmt.setString(3, this.temp_importantloc);
                         this.pstmt.setInt(4,day);
                     } else {
                         this.sql = "insert into importantloc values(?,?,?,?,?)";
                         this.pstmt = con.prepareStatement(this.sql);
                         this.pstmt.setString(1, id);
                         this.pstmt.setString(2, temp_importantloc);
                         this.pstmt.setInt(3, 1);
                         this.pstmt.setString(4,today_kor);
                         this.pstmt.setInt(5,day);
                     }
                     this.pstmt.executeUpdate();
                     this.err = "update important loc sucess";
                 }
                 else {
                    this.err = "failed(Not Enough daily_location)";
                 }
             }
             else
                 this.err = "finding 6 recent k_address failed ";
        } catch (SQLException e) {
            this.err = "select_" + "location_sql error" + e;
        }
    }

    String result() {
        return this.err;
    }
}
