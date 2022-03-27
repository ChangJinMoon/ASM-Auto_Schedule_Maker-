import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Query_sender {
    private Connection con;

    public void setCon(Connection con) {
        this.con = con;
    }
    private String table_name = "time_table";

    String search_query(String sql) {
        System.out.println("search_query:"+sql);
        try {
            int size;
            PreparedStatement pstmt = this.con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = pstmt.executeQuery();
            rs.last();
            size = rs.getRow();
            if(size == 0)
                return "empty";
            else {
                rs.beforeFirst();
                return "exist";
            }
        }catch (SQLException e) {
            System.out.println(e);
            return "table check error:"+e;
        }
    }

    List<Time_table_data> get_timeTable_query(String sql) {
        List<Time_table_data> temp = new ArrayList<>();
        System.out.println("get_timeTable_query:"+sql);
        try {
            PreparedStatement pstmt = this.con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                Time_table_data data = new Time_table_data();
                data.setDay(rs.getInt("day"));
                data.setLocation(rs.getString("location"));
                data.setArrive(rs.getString("arrive"));
                data.setDepart(rs.getString("depart"));
                data.setDay_string(rs.getString("day_string"));
                temp.add(data);
            }

            if(temp.size() == 0)
                return null;

            return temp;
        }catch (SQLException e) {
            System.out.println("table check error:"+e);
            return null;
        }
    }
}

