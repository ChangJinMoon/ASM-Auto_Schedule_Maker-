public class Get_Timetable {

    private String user_id;
    private String table_name = "time_table";
    private String day_kor;

    public Get_Timetable setUser_id(String id){
        this.user_id = id;
        return this;
    }

    public void setDay_kor(String day_kor){
        this.day_kor = day_kor;
    }

    String make_search_query(){
        StringBuilder query = new StringBuilder();
        query.append("select day from ");
        query.append(table_name);
        query.append(" where ");
        query.append("id ='"+user_id+"'");
        query.append(" and ");
        query.append("day_string ='"+day_kor+"';");
        return  query.toString();
    }

    String make_get_timetable_query(){
        StringBuilder query = new StringBuilder();
        query.append("select * from ");
        query.append(table_name);
        query.append(" where ");
        query.append("id ='"+user_id+"'");
        query.append(" and ");
        query.append("day_string ='"+day_kor+"';");
        return query.toString();
    }
}
