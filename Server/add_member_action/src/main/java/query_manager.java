import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class query_manager {
    Connection con;

    PreparedStatement pstmt;

    ResultSet rs;

    public void setCon(Connection con) {
        this.con = con;
    }

    String id_check_query(Member member) {
        String sql = "select id from member where id = ?";
        try {
            this.pstmt = this.con.prepareStatement(sql);
            this.pstmt.setString(1, member.getId());
            this.rs = this.pstmt.executeQuery();
            if (this.rs.next())
                return "Id already exist";
            return "success";
        } catch (SQLException e) {
            return e.toString();
        }
    }

    String add_member_query(Member member) {
        String sql = "insert into member values(?,?,?)";
        try {
            this.pstmt = this.con.prepareStatement(sql);
            this.pstmt.setString(1, member.getId());
            this.pstmt.setString(2, member.getPassword());
            this.pstmt.setString(3, member.getSex());
            this.pstmt.executeUpdate();
            return "success";
        } catch (SQLException e) {
            return e.toString();
        }
    }
}
