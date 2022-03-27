import java.sql.Connection;

public class Create_table {
    private String user_id;

    public Create_table setUser_id(String id){
        this.user_id = id;
        return this;
    }

    public String check_table_exist_query(){
        StringBuilder sql = new StringBuilder();
        sql.append("SHOW TABLES LIKE ");
        sql.append("'"+this.user_id +"_daily_location';");
        System.out.println(sql);
        return sql.toString();
    }

}
