import About_location.save_location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test_main {

    public void test(List<Double> test_set){
        String result = "", result2 = "",payload = "";
        String db_connect = "";
        String mysql_url = "jdbc:mysql://14.39.92.194:3306/project_db?useUnicode=true&characterEncoding=utf8";
        String db_id = "jin1004boy";
        String db_pw = "ckdwls31412!";
        int time_term = 6;
        String id = "jin1004boy";

        double address_lat = test_set.get(0), address_lng = test_set.get(1), address_accuracy = test_set.get(2);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(mysql_url, db_id, db_pw);
            save_location save_loc = new save_location();
            save_loc.setCon(con);
            save_loc.save_location(id,address_lat,address_lng,address_accuracy);
            result = save_loc.result();
            System.out.println(save_loc.result());

            if (save_loc.getCount() % time_term == 0) {
                SelectLocation select_loc = new SelectLocation(con, id, time_term, save_loc.getCount());
                result2 = select_loc.result();
                System.out.println(result2);
            }

            payload = "0";
        } catch (ClassNotFoundException e) {
            payload = "104";
            db_connect = "driver connect faild:" + e;
        } catch (SQLException e) {
            payload = "105";
            db_connect = "sql error:" + e;
        }
    }

    public List<Double> set_test_list(double lat,double lng ,double accuracy){
        List<Double> temp = new ArrayList<>();
        temp.add(lat);
        temp.add(lng);
        temp.add(accuracy);
        return temp;
    }

    public static void main(String args[]) {
        Test_main test = new Test_main();
        List<Double> test_set;
        List<List> test_list = new ArrayList<>();
        int count = 1;
        int list_count = 0;

        test_list.add(test.set_test_list(37.5413868,127.0839414,13.221));
        test_list.add(test.set_test_list(37.5394830,127.0830329,12.033));
        test_list.add(test.set_test_list(37.5384780,127.0733491,15.542));
        test_set = test_list.get(0);

        System.out.println("Test Start");

        while(true){
            System.out.println("count:"+count);
            if(count%12 == 0){
                list_count++;
                System.out.println("test set changed!!"+list_count);
                if(list_count == 3){
                    list_count = 0;
                }
                test_set = test_list.get(list_count);
            }
            test.test(test_set);
            try{
                Thread.sleep(1000*60*10);
            }catch (InterruptedException e){
                e.printStackTrace();
                return;
            }
            count++;
        }
    }
}
