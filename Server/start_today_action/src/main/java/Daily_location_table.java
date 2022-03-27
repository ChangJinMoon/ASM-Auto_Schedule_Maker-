import java.util.ArrayList;
import java.util.List;

public class Daily_location_table {

    private String user_id;
    private String query;
    private List<String> colums;


    public Daily_location_table setUser_id(String id){
        this.user_id = id;
        return this;
    }

    public String getQuery(){
        set_table_colum();
       return make_create_query();
    }

    private String make_create_query(){
        StringBuilder query = new StringBuilder();
        query.append("create table ");
        query.append(user_id);
        query.append("_daily_location(");

        for (int i = 0; i < colums.size(); i++) {
             query.append(colums.get(i)+" "+return_DataInfo(colums.get(i)));
             if(i == colums.size() -1)
                 query.append(");");
             else
                 query.append(",");
        }
        System.out.println("sql:"+query);
        return query.toString();
    }

    String return_DataInfo(String key){
        if(key.equals("lat"))
            return "double";
        if(key.equals("lng"))
            return "double";
        if(key.equals("accuracy"))
            return "double";
        if(key.equals("k_address"))
            return "varchar(1000)";
        if(key.equals("time"))
            return "datetime";
        if(key.equals("day"))
            return "int";
        if(key.equals("count"))
            return "int";
        return null;
    }

    void set_table_colum(){
        this.colums = new ArrayList<String>();
        colums.add("lat");
        colums.add("lng");
        colums.add("accuracy");
        colums.add("k_address");
        colums.add("time");
        colums.add("day");
        colums.add("count");
        System.out.println("colums list:"+colums);
    }
}
