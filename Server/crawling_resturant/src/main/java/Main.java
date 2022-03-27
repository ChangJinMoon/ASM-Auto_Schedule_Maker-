import Common.Json_mannager;
import Common.reply_manager;
import Crawiling.Crawiling_main;
import Etc.MakeSerachString;
import Etc.Store;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.List;

public class Main {
    public static JsonObject main(JsonObject args) {

        Json_mannager json_mannager = new Json_mannager();
        json_mannager.setOb(args);


        String address;
        String result = "none";
        String result_detail = "none";

        if (!json_mannager.receive_json_check_key()) {
            result = "101";
            result_detail = "received json data has error";
            return (new reply_manager()).make_result(result, result_detail).return_json_result();
        }

        address = json_mannager.getUser_id();

        //1. 시간이 지난 약속 삭제 및 사용자가 가지고 있는 약속 추출
        Crawiling_main crawiling_main = new Crawiling_main();
        MakeSerachString makeSerachString = new MakeSerachString();

        makeSerachString.setting_city_citydetails(address);
        crawiling_main.setMakeSerachString(makeSerachString);
        crawiling_main.search_stores();

        if(crawiling_main.getFlag() == 1){
            result = "201";
            result_detail = "Error occur: Document is empty";
            return new reply_manager().make_result(result,result_detail).return_json_result();
        }

        if(crawiling_main.getFlag() == 2){
            result = "201";
            result_detail = "Error occur: StoreList is empty";
            return new reply_manager().make_result(result,result_detail).return_json_result();
        }

        List<Store> storeList = crawiling_main.getStoreList();

        //모든게 성공적으로 끝났을때 클라이언트에게 결과 전달
        result = "0";
        result_detail = address;
        return (new reply_manager()).make_result(result, result_detail).return_json_result_with_list(storeList);
    }

}
