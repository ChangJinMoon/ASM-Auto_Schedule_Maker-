package Crawiling;

import Etc.Store;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawiling_act {

    public Document define_document(String url){
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            return doc;
        }catch (IOException e){
            System.err.println(e);
            return null;
        }
    }

    public List<Store> get_store_list(Document doc){
        List<Store> temp = new ArrayList<>();
        Elements element = doc.select("ul#div_list");
        int count = 0,for_count = 0;

        if(element.size() == 0){
            return null;
        }

        for(Element e: element){
            for (Element url: element.select("span")){
                if(url.className().equals("btxt")) {
                    Store temp_store = new Store();
                    temp_store.setName(url.text());
                    temp.add(temp_store);
                    count++;
                }
            }
            for (Element store: element.select("a")){
                if(for_count >= temp.size())
                    break;
                temp.get(for_count++).setUrl(store.attr("abs:href"));
            }
            for_count = 0;
            for (Element url: element.select("span")){
                if(url.className().equals("point")){
                    if(for_count >= temp.size())
                        break;
                    temp.get(for_count++).setScore(url.text());
                }
            }
            for_count = 0;
            for(Element address: element.select("i")){
                if(address.className().equals("loca")){
                    if(for_count >= temp.size())
                        break;
                    temp.get(for_count++).setAddress(address.text());
                }
            }

        }
        return temp;
    }
}
