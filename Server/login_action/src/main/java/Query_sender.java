import java.sql.*;

public class Query_sender {
    private Connection con;
    private String answer_pass;
    private String faild_message;

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getAnswer_pass(){
        return this.answer_pass;
    }
    public String getFaild_message(){
        return this.faild_message;
    }

    boolean search_query(String sql) {
        System.out.println("search_query:"+sql);
        try {
            PreparedStatement pstmt = this.con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
               this.answer_pass = rs.getString("password");
               return true;
            }
            else{
                this.faild_message = "Wrong Id";
                return false;
            }
        }catch (SQLException e) {
            this.faild_message = e.toString();
            return false;
        }
    }
}

