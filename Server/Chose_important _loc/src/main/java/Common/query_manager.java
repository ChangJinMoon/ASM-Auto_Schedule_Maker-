package Common;

import Third_logic.Time_table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class query_manager {
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;

    public ResultSet get_day_importantloc(String id ,int day) {
        int min_important = 2;
        String sql = "select * from importantloc where id = ? and day_int = ? and important >= ?";

        try {
            int size;
            this.pstmt = this.con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.pstmt.setString(1, id);
            this.pstmt.setInt(2, day);
            this.pstmt.setInt(3, min_important);
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

    public ResultSet get_time_daily_location(String id, String k_address){
        String sql = "select k_address,time,count from "+id+"_daily_location"
                +" where k_address = ? order by count ASC;";
        try {
            int size;
            this.pstmt = this.con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.pstmt.setString(1,k_address);
            this.rs = this.pstmt.executeQuery();

            rs.last();
            size = rs.getRow();

            if(size == 0)
                return null;
            else {
                rs.beforeFirst();
                return rs;
            }
        }catch (SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public ResultSet get_time_table_data(String id, String day_of_week){
        String sql = "select * from time_table where id = ? and day_string = ? order by day ASC;";
        try {
            int size;
            this.pstmt = this.con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            this.pstmt.setString(1,id);
            this.pstmt.setString(2,day_of_week);
            this.rs = this.pstmt.executeQuery();

            rs.last();
            size = rs.getRow();

            if(size == 0)
                return null;
            else {
                rs.beforeFirst();
                return rs;
            }
        }catch (SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public boolean update_time_table_data(Time_table temp){
        SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm");
        String sql = "insert into time_table values(?,?,?,?,?,?);";
        try{
            this.pstmt = con.prepareStatement(sql);
            this.pstmt.setString(1, temp.getId());
            this.pstmt.setInt(2, temp.getDay());
            this.pstmt.setString(3, temp.getLocation());
            this.pstmt.setString(4, df.format(temp.getArrive()));
            this.pstmt.setString(5, df.format(temp.getDepart()));
            this.pstmt.setString(6,temp.getDay_string());
            this.pstmt.executeUpdate();
            return true;
        }catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }

    public boolean delete_time_table_week_two(String id,String day_of_week){
        String sql = "delete from time_table where id = ? and day_string = ? and day = '2'";
        try{
            this.pstmt = con.prepareStatement(sql);
            this.pstmt.setString(1,id);
            this.pstmt.setString(2,day_of_week);
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

    public query_manager setCon_d(Connection con) {
        this.con = con;
        return this;
    }
}
