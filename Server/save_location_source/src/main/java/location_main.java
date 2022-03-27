import About_location.save_location;
import Common.Respose;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class location_main {
    public static JsonObject main(JsonObject args) throws Exception {
        String result = "", result2 = "",payload = "";
        String db_connect = "";
        String mysql_url = "jdbc:mysql://14.39.92.194:3306/project_db?useUnicode=true&characterEncoding=utf8";
        String db_id = "jin1004boy";
        String db_pw = "ckdwls31412!";
        int time_term = 6;
        String id = "";
        double address_lat = 0.0D, address_lng = 0.0D , address_accuracy = 0.0D;

        if (!args.has("id")) {
            payload = "101";
            result = "recieved data is wrong(id)";
            return new Respose().reponse_maker(payload,result,result2,db_connect);
        }
        if (!args.has("address_lat")) {
            payload = "102";
            result = "recieved data is wrong(lat)";
            return new Respose().reponse_maker(payload,result,result2,db_connect);
        }
        if (!args.has("address_lng")) {
            payload = "103";
            result = "recieved data is wrong(lng)";
            return new Respose().reponse_maker(payload,result,result2,db_connect);
        }

        if (!args.has("address_acc")) {
            payload = "104";
            result = "recieved data is wrong(accuracy)";
            return new Respose().reponse_maker(payload,result,result2,db_connect);
        }

        id = args.getAsJsonPrimitive("id").getAsString();
        address_lat = args.getAsJsonPrimitive("address_lat").getAsDouble();
        address_lng = args.getAsJsonPrimitive("address_lng").getAsDouble();
        address_accuracy = args.getAsJsonPrimitive("address_acc").getAsDouble();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(mysql_url, db_id, db_pw);
            save_location save_loc = new save_location();
            save_loc.setCon(con);
            save_loc.save_location(id,address_lat,address_lng,address_accuracy);
            result = save_loc.result();

            if (save_loc.getCount() % time_term == 0) {
                SelectLocation select_loc = new SelectLocation(con, id, time_term, save_loc.getCount());
                result2 = select_loc.result();
            }
            payload = "0";
        } catch (ClassNotFoundException e) {
            payload = "105";
            db_connect = "driver connect faild:" + e;
        } catch (SQLException e) {
            payload = "106";
            db_connect = "sql error:" + e;
        }


        return new Respose().reponse_maker(payload,result,result2,db_connect);
    }
}
