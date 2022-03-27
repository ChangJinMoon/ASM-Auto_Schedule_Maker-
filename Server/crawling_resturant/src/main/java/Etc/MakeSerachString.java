package Etc;

import java.util.ArrayList;
import java.util.List;

public class MakeSerachString {
    private String city;
    private String city_detail = null;

    public void setting_city_citydetails(String address){
        //공백 기준 String 나누기
        String[] temp_list = address.split(" ");
        if(temp_list.length == 1){
            city = temp_list[0];
        }
        else {
            //리스트 1번 2번 나우어서 city citydetail에 삽입
            city = temp_list[1];
            city_detail = temp_list[2];
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_detail() {
        return city_detail;
    }

    public void setCity_detail(String city_detail) {
        this.city_detail = city_detail;
    }

    public String combine_city_addr(){
        StringBuilder temp = new StringBuilder();
        temp.append(city);
        temp.append("%20");
        temp.append(city_detail);
        return temp.toString();
    }
}
