package About_location;

import Common.Get_Address;
import Common.Get_Address_Geo;
import Common.Today;
import Common.Two_cordinate_check;
import javafx.scene.input.DataFormat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class save_location {
    ResultSet rs;
    Connection con;
    PreparedStatement pstmt = null;
    Get_Address_Geo get_in_kAddress;
    Date realTime;
    DateFormat df;

    String err;
    String k_address;
    int count = 0;
    double accuracy = 0.0;

    List<Double> temp_data = new ArrayList<>();
    List<Double> now_data = new ArrayList<>();

    public void save_location(String id, double lat, double lng, double accuracy){
        double insert_lat,insert_lng,insert_accuracy;
        String insert_k_address;
        // 시간 형식 지정
        set_Time();
        //이전 데이터 있는지 확인
        if(check_data(id)){
            //이전 데이터 받아오기
            get_temp_data(id);
            //오차 범위내에 있는지 확인
            set_cor_list(lat,lng);
            Two_cordinate_check check = new Two_cordinate_check();
            check.set_two_cor(temp_data,now_data);
            check.check();

            if(check.getResult()){
                //있으면 있으면 기존 데이터 삽입
                insert_lat = temp_data.get(0);insert_lng = temp_data.get(1);
                insert_accuracy = temp_data.get(2);
                insert_k_address = this.k_address;
                //데이터 삽입
                insert_data(id,insert_lat,insert_lng,insert_accuracy,insert_k_address);
                return;
            }
        }
        get_in_kAddress = new Get_Address_Geo();
        get_in_kAddress.setUrl(lat,lng);

        String jsonData = get_in_kAddress.getJsonData();
        String k_address = get_in_kAddress.get_kaddress(jsonData);
        //새로운 데이터 삽입
        insert_lat = lat;insert_lng = lng;
        insert_accuracy = accuracy;
        insert_k_address = k_address;
        insert_data(id,insert_lat,insert_lng,insert_accuracy,insert_k_address);
    }

    private void set_cor_list(double n_lat , double n_lng){
        now_data.add(n_lat);
        now_data.add(n_lng);
        temp_data.add(this.accuracy);
    }

    private void set_Time(){
        realTime = new Date();
        df = new SimpleDateFormat("yyy-MM-dd HH:mm");
        TimeZone time = TimeZone.getTimeZone("Asia/Seoul");
        df.setTimeZone(time);
    }

    private boolean check_data(String id){
        String sql = "select count from "+id+"_daily_location"+" order by count desc;";
        try{
            this.pstmt = con.prepareStatement(sql);
            this.rs = this.pstmt.executeQuery();

            if(this.rs.next()){
                //data exist
                this.count = this.rs.getInt("count");
                return true;
            }
            else
                return false;

        }catch (SQLException e){
            this.err = "check db data failed:" + e;
        }
        return false;
    }

    private void get_temp_data(String id){
        String sql = "select * from "+id+"_daily_location"+" where count = "+this.count+";";
        try{
            this.pstmt = con.prepareStatement(sql);
            this.rs = this.pstmt.executeQuery();

            if(this.rs.next()){
                temp_data.add(this.rs.getDouble("lat"));
                temp_data.add(this.rs.getDouble("lng"));
                this.accuracy = this.rs.getDouble("accuracy");
                this.k_address = this.rs.getString("k_address");
            }
            else{
                this.err = "get_temp_data empty";
            }
        }catch (SQLException e){
            this.err = "check db data failed:" + e;
        }
    }

    private void insert_data(String id,double lat, double lng , double accuracy, String k_address){
        this.count++;
        Today today = new Today();
        today.set_current_time();
        today.set_date_kor_version();

        if(k_address.equals("error")) {
            this.err = "k_address has error:"
                +get_in_kAddress.getResult();
            return;
        }

        String sql = "insert into "+id+"_daily_location"+" values(?,?,?,?,?,?,?);";
        try{
            this.pstmt = con.prepareStatement(sql);
            this.pstmt.setDouble(1, lat);
            this.pstmt.setDouble(2, lng);
            this.pstmt.setDouble(3, accuracy);
            this.pstmt.setString(4, k_address);
            this.pstmt.setString(5, df.format(realTime));
            this.pstmt.setInt(6,today.getDay());
            this.pstmt.setInt(7, this.count);
            this.pstmt.executeUpdate();
            this.err = "update_save_loc";
        }catch (SQLException e){
            this.err = "save_location_sql error:"
                    + e
                    +"\nResult_detail:"
                    +get_in_kAddress.getResult();
        }

    }

    public String result() {
        return this.err;
    }

    public int getCount() { return this.count;}

    public String return_kAddress() {return this.k_address;}

    public void setCon(Connection con) {this.con = con;}
}
