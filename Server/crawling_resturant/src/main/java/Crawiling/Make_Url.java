package Crawiling;

public class Make_Url {
    private String default_search_url = "https://www.diningcode.com/list.php?query=";
    private String profile_url = "https://www.diningcode.com";

    public String search_url(String address){
        StringBuilder temp = new StringBuilder();
        temp.append(default_search_url);
        temp.append(address);
        return temp.toString();
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }
}
