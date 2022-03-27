package Crawiling;

public class Make_Tag {
    StringBuilder tag = new StringBuilder();

    public void add_div_tag(){ tag.append("div "); }

    public void add_a_tag(){ tag.append("a "); }

    public void add_span_tag(){ tag.append("span "); }

    public void add_id_tag(){ tag.append("id=");}

    public void add_class_tag(){ tag.append("class=");}

    public void add_href_tag(){ tag.append("href=");}

    public void add_value_tag(String value){
        tag.append(value);
    }

    public String return_tag(){
        return tag.toString();
    }

}
