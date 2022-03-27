public class Group_query {

    String return_check_id_query(String id){
        String sql = "select password from member where id = '"+id+"';";
        return sql;
    }
}
