package Etc;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private String name;
    private String address;
    private String score;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        StringBuilder temp_string = new StringBuilder();
        String[] temp = name.split(" ");
        for (int i = 0; i < temp.length; i++) {
            if(i !=0)
                temp_string.append(temp[i]);
        }
        this.name = temp_string.toString();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
