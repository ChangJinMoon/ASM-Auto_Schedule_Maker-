package Common;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class Get_Address_Geo {

    private String url;
    private String key;
    private String result;


    public void setUrl(double lat,double lng) {
        // key 등록
        setKey();
        //요청에 필요한 url 생성
        String reverseGeocodeURL = "http://api.vworld.kr/req/address?"
                + "service=address&request=getAddress&version=2.0&crs=epsg:4326&point="
                + lng + "," + lat
                + "&format=json"
                + "&type=both&zipcode=true"
                + "&simple=false&"
                + "key="+key;

        this.url = reverseGeocodeURL;
    }

    public void setKey() {
        this.key = "01E1E281-DB0F-32E4-B236-463DD7F193ED";
    }

    public String getResult() {
        return result;
    }

    public String getJsonData(){
        System.out.println("Requeted URL:" + this.url);
        StringBuilder sb = new StringBuilder();

        URLConnection urlConn = null;
        InputStreamReader in = null;

        try {
            URL url = new URL(this.url);
            urlConn = url.openConnection();

            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);

            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset()); //charset 문자 집합의 인코딩을 사용해 urlConn.getInputStream을 문자스트림으로 변환 객체를 생성.
                BufferedReader bufferedReader = new BufferedReader(in); //주어진 문자 입력 스트림 inputStream에 대해 기본 크기의 버퍼를 갖는 객체를 생성.

                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                    this.result = "Successful getting a address(json)";
                }
                else
                    this.result = "get empty response";
            }

            else
                this.result = "Url Connection failed";

            in.close();
        } catch (Exception e) {
            this.result = "Exception URL:" + e;
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String get_kaddress(String jsonData){
        String k_address = "";
        //파서로 root object 생성
        if(jsonData.isEmpty())
            return "error";

        JsonParser parser =  new JsonParser();
        JsonObject root = (JsonObject) parser.parse(jsonData);

        //root에서 결과를 담고 있는 response 객체 받기
        JsonObject response = (JsonObject) root.get("response");
        JsonArray result_array = (JsonArray) response.get("result");
        JsonObject result = (JsonObject) result_array.get(0);
        k_address = result.get("text").getAsString();

        if(k_address.equals("")) {//주소가 없을 때
            this.result = "address is not Exist";
            return "empty";
        }

        this.result = "Successful getting a address";
        return k_address;
    }

    public static void main(String args[]){
        //Test code
        Get_Address_Geo getAddress = new Get_Address_Geo();
        getAddress.setUrl(37.5413719, 127.083932);

        String jsonData = getAddress.getJsonData();
        getAddress.get_kaddress(jsonData);
        System.out.println("Kaddress:"+getAddress.get_kaddress(jsonData));
        System.out.println("Final result"+ getAddress.getResult());
    }

}
