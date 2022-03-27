package Common;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

public class Get_Address {
    double latitude;
    double longitude;
    String regionAddress;

    public void set_corodinate(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int get_address_reply(){
        this.regionAddress = getJSONData(getApiAddress());
        if(this.regionAddress.equals("none")){
            return 2;
        }
        else
            return 0;
    }

    String jsonPassing() {
        String f_address = "";
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject)parser.parse(this.regionAddress);
        JsonArray array = (JsonArray)obj.get("results");
        JsonObject addr = (JsonObject)array.get(0);
        JsonObject roadaddr = (JsonObject)array.get(1);
        JsonObject region = (JsonObject)addr.get("region");
        JsonObject land2 = (JsonObject)addr.get("land");
        JsonObject land = (JsonObject)roadaddr.get("land");
        JsonObject[] area = new JsonObject[3];
        JsonObject addition0 = (JsonObject)land.get("addition0");
        area[0] = (JsonObject)region.get("area1");
        area[1] = (JsonObject)region.get("area2");
        area[2] = (JsonObject)region.get("area3");
        String space = "";
        for (int i = 0; i < area.length; i++) {
            if (i != 0)
                space = " ";
            JsonElement jsonElement = area[i].get("name");
            f_address = f_address + space + jsonElement.getAsString();
        }
        JsonElement element;
        f_address = f_address + " " + (element = land2.get("number1")).getAsString();
        if ((element = land2.get("number2")).getAsString() != null)
            f_address = f_address + "-" + land2.get("number2").getAsString();
        if ((element = addition0.get("value")).getAsString() != null)
            f_address = f_address + " " + (element = addition0.get("value")).getAsString();
        return f_address;
    }

    String getApiAddress() {
        String apiURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?request=coordsToaddr&coords=" + this.longitude + "," + this.latitude + "&orders=addr,roadaddr&output=json";
        return apiURL;
    }

    String getJSONData(String apiURL){
        String jsonString = new String();
        try {
            URL url = new URL(getApiAddress());
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "xgundembra");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", "GBV0ga1iRMmXljxEAjpwMq1hefwYZRorXoRPpMkW");
            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String buf;
            while ((buf = br.readLine()) != null)
                jsonString = jsonString + buf;

            System.out.println(jsonString);
            return jsonString;
        }catch (Exception e){
            return e.toString();
        }
    }

    public String getAddress() {
        return jsonPassing();
    }

    public static void main(String[] args) throws Exception {
        double lat = 37.541341D, lng = 127.083946D;
        Get_Address t = new Get_Address();
        t.set_corodinate(lat,lng);
        if(t.get_address_reply() == 0){
            System.out.println("success");
            String location = t.getAddress();
            System.out.println(location);
        }
        else
            System.out.println("failed");

    }
}
