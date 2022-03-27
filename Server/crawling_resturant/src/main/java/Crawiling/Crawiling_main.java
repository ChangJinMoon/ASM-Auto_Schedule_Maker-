package Crawiling;

import Etc.MakeSerachString;
import Etc.Store;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Crawiling_main {
    Crawiling_act crawiling_act = new Crawiling_act();
    Make_Url make_url;
    private MakeSerachString makeSerachString;
    private  List<Store> storeList;
    private int flag = 0;

    public void search_stores(){
        Document temp;
        make_url = new Make_Url();
        //1.상위 10개 맛집 리스트 저장
        if(makeSerachString.getCity_detail() == null){
            temp =crawiling_act.define_document(make_url.search_url(
                    makeSerachString.getCity()));
        }
        else {
            temp = crawiling_act.define_document(make_url.search_url(
                    makeSerachString.combine_city_addr()));
        }

        if(temp == null) {
            flag = 1;
            return;
        }
        try {
            storeList = crawiling_act.get_store_list(temp);
            if(storeList == null){
                flag =2;
                return;
            }
        }catch (NullPointerException e){
            flag = 2;
            return;
        }
        flag = 0;
    }

    public void setMakeSerachString(MakeSerachString makeSerachString) {
        this.makeSerachString = makeSerachString;
    }

    public List<Store> getStoreList(){
        return this.storeList;
    }

    public int getFlag() {
        return flag;
    }
}
