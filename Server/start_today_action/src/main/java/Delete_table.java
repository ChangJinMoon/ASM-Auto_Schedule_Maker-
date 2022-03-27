public class Delete_table {

    private String user_id;

    public Delete_table setUser_id(String id){
        this.user_id = id;
        return this;
    }

    public String delete_table_query(){
        StringBuilder sql = new StringBuilder();
        sql.append("DROP TABLE ");
        sql.append(this.user_id +"_daily_location;");
        return sql.toString();
    }

}
