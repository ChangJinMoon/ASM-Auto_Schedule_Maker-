package Second_logic;

import Common.Db_connector;
import First_logic.Get_Importantloc;
import First_logic.Important;
import Common.Today;
import Common.query_manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Get_importantloc_time {
    private String id;
    private String result;
    private boolean result_bool = false;

    private Connection con;
    private ResultSet rs;
    private Today today = new Today();
    private query_manager query_manger = new query_manager();
    private List<Important> important_loc_list = new ArrayList<>();
    private List<Important> final_important_list = new ArrayList<>();

    public void init(){
        query_manger.setCon(this.con);
        //걸러낸 장소 도착 출발 시간 서치
        Important important_loc;
        try {
            List<Daily_location> daily_locationList;
            Set_important_time setImportantTime;

            for (int i = 0; i < important_loc_list.size(); i++) {
                important_loc = important_loc_list.get(i);
                System.out.println("importantloc:"+important_loc.getK_address());
                rs = query_manger.get_time_daily_location(this.id,important_loc.getK_address());
                daily_locationList = get_daily_location(rs);

                setImportantTime = new Set_important_time();
                setImportantTime.first_setting(important_loc,daily_locationList);
                setImportantTime.init();

                if(setImportantTime.getImportant_list().size() != 0) {
                    for (int j = 0; j < setImportantTime.getImportant_list().size(); j++) {
                        final_important_list.add(setImportantTime.getImportant_list().get(j));
                    }
                }
                daily_locationList.clear();
            }

            this.result_bool = true;
            this.result = "success setting final_important_list";
        }catch (NullPointerException e){
            this.result_bool = false;
            this.result = "important_loc_list is empty:"+e;
        }
    }

    private List<Daily_location> get_daily_location(ResultSet rs){
        List<Daily_location> daily_locationList = new ArrayList<>();
        try{
            Daily_location dailyLocation;
            while(rs.next()){
                dailyLocation = new Daily_location();
                dailyLocation.setCount(rs.getInt("count"));
                dailyLocation.setK_address(rs.getString("k_address"));
                dailyLocation.setTime_string(rs.getString("time"));
                daily_locationList.add(dailyLocation);
            }
            this.result_bool = true;
            this.result = "success";
            return daily_locationList;
        }catch (SQLException e){
            this.result_bool = false;
            this.result = "get_daily_location sql error:"+e;
        }catch (NullPointerException e){
            this.result_bool = false;
            this.result = "result set is empty:"+e;
        }
        return null;
    }

    public void set_id_and_con(String id,Connection con){
        this.id = id;
        this.con = con;
    }

    public void setImportant_loc_list(List<Important> important_loc_list) {
        this.important_loc_list = important_loc_list;
    }

    public String getResult() { return result; }

    public List<Important> getFinal_important_list(){
        return this.final_important_list;
    }

    public boolean isResult_bool() { return result_bool; }

    public static void main(String args[]){
        String id = "jin1004boy";
        List<Important> test_list;

        Get_Importantloc test = new Get_Importantloc();
        Db_connector db = new Db_connector();
        Connection con = db.try_Connect();

        test.set_id_and_con(id,con);
        test.init();

        if(test.isResult_bool()){
            test_list = test.getImportant_loc_list();
            Get_importantloc_time get_importantloc_time = new Get_importantloc_time();
            get_importantloc_time.set_id_and_con(id,con);
            get_importantloc_time.setImportant_loc_list(test_list);
            get_importantloc_time.init();

            if(get_importantloc_time.isResult_bool()){
                System.out.println("success:"+get_importantloc_time.getResult());
                //System.out.println("success:"+get_importantloc_time.getFinal_important_list().size());
                for (int i = 0; i < get_importantloc_time.getFinal_important_list().size(); i++) {
                    System.out.println(i+"번째-도착시간:"+get_importantloc_time.getFinal_important_list().get(i).getArriveTime()
                            + "출발시간:"+get_importantloc_time.getFinal_important_list().get(i).getDepartTime());
                }
            }
            else{
                System.out.println("failed:"+get_importantloc_time.getResult());
            }
        }
        else
            System.out.println("failed"+test.getResult());
    }
}
