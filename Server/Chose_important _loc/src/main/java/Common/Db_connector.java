package Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db_connector {
    private String url;
    private String id;
    private String password;
    private String result_details;

    Connection con;

    void setUrl() {
        this.url = "jdbc:mysql://14.39.92.194:3306/project_db?useUnicode=true&characterEncoding=utf8";
    }

    void setId() {
        this.id = "jin1004boy";
    }

    void setPassword() {
        this.password = "ckdwls31412!";
    }

    public String getResult_details(){
        return result_details;
    }

    public Connection try_Connect() {
        System.out.println("====================================================");
        System.out.println("DB_Connection process start");
        setUrl();
        setId();
        setPassword();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(this.url, this.id, this.password);
            System.out.println("Connection success\n");
            return this.con;
        } catch (SQLException|ClassNotFoundException e) {
            System.out.println("err:" + e.toString());
            result_details = e.toString();
            return null;
        }
    }
}
