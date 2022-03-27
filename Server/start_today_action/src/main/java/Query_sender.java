import java.sql.*;

public class Query_sender {
    private Connection con;

    public void setCon(Connection con) {
        this.con = con;
    }

    String table_already_exist(String sql) {
        try {
            PreparedStatement pstmt = this.con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.isBeforeFirst())
                return "exist";
            else
                return "empty";
        }catch (SQLException e) {
            return "table check error:"+e;
        }
    }

    String creat_daily_table(String sql) {
        try {
            Statement stmt = con.createStatement();
            if(stmt.execute(sql))
                return "failed";
            else
                return  "success";
        }catch (SQLException e){
            return "SqlException occur:"+e;
        }
    }
    String delete_daily_table(String sql) {
        try {
            Statement stmt = con.createStatement();
            if(stmt.execute(sql))
                return "failed";
            else
                return  "success";
        }catch (SQLException e){
            return "SqlException occur:"+e;
        }
    }
}
