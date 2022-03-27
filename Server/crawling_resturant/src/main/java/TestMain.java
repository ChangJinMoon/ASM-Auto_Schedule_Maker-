import Common.reply_manager;
import Crawiling.Crawiling_main;
import Etc.MakeSerachString;
import Etc.Store;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TestMain {

    public static void main(String args[]){
        Crawiling_main crawiling_main = new Crawiling_main();
        MakeSerachString makeSerachString = new MakeSerachString();

        String result = "none";
        String result_detail = "none";

        makeSerachString.setting_city_citydetails("건대입구");
        crawiling_main.setMakeSerachString(makeSerachString);
        crawiling_main.search_stores();

        if(crawiling_main.getFlag() == 1){
            result = "201";
            result_detail = "Error occur: Document is empty";
            System.out.println(result_detail);
        }

        if(crawiling_main.getFlag() == 2){
            result = "201";
            result_detail = "Error occur: StoreList is empty";
            System.out.println(result_detail);
        }

        List<Store> storeList = crawiling_main.getStoreList();

        //모든게 성공적으로 끝났을때 클라이언트에게 결과 전달
        result = "0";
        result_detail = "Success get StoreList";
        new TestMain().act();
        System.out.println(new reply_manager().make_result(result, result_detail).return_json_result_with_list(storeList).toString());

    }
    void act(){
        String originalStr = "테스트";
        String[] charSet = {"utf-8", "euc-kr", "ksc5601", "iso-8859-1", "x-windows-949"};

        for(int i = 0; i<charSet.length; i++){
            for(int j = 0; j<charSet.length; j++){
                try{
                    System.out.println("[" + charSet[i] + "," + charSet[j] + "]" + new String(originalStr.getBytes(charSet[i]), charSet[j]));
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
