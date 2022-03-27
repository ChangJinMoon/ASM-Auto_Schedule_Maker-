package About_importantloc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Get_term_address {
    //get 6 k_address and save
    // return String ArrayList
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private List<String> k_address = new ArrayList<>();

    private int term;
    private String user_id;
    private int now_count;

    public void setCon(Connection con) {
        this.con = con;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Get_term_address setUser_id(String id){
        this.user_id = id;
        return this;
    }
    public Get_term_address setNow_count(int count){
        this.now_count = count;
        return this;
    }


    public List<String> getK_address(){
        query_sender();
        return this.k_address;
    }

    public boolean init(){
        return query_sender();
    }

    private String return_query(int count){
        String sql = "select k_address from "+this.user_id+"_daily_location where count = "+Integer.toString(count);
        return sql;
    }

    private boolean query_sender(){
        try{
            for (int i = 0; i <this.term; i++) {
                this.pstmt = con.prepareStatement(return_query(now_count-i));
                this.rs = this.pstmt.executeQuery();

                if(this.rs.next())
                    this.k_address.add(this.rs.getString("k_address"));
                else
                    this.k_address.add("none");
            }
            return true;
        }catch (SQLException e){
            System.out.println(e);
            return false;
        }
    }


}
