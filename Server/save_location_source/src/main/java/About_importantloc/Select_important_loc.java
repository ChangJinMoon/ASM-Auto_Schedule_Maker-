package About_importantloc;

import java.util.ArrayList;
import java.util.List;

public class Select_important_loc {
    private List<String> k_address = new ArrayList<>();
    private int[] overlap_count;

    private int time_term;
    private int max_count_term;

    public Select_important_loc setK_address(List<String> k_address) {
        this.k_address = k_address;
        return this;
    }

    public void setTime_term(int term) {
        this.time_term = term;
    }

    public void setMax_count_term(int term){
        this.max_count_term = term;
    }

    public String return_max_overlap_k_address(){
        int max_count = 0;
        int max_count_index = 0;
        count_overlap();

        for (int i = 0; i < overlap_count.length; i++) {
            if(overlap_count[i] > max_count) {
                max_count = overlap_count[i];
                max_count_index = i;
            }
        }

        if(max_count >= this.max_count_term)
            return k_address.get(max_count_index);

        else
            return "none";
    }

    void count_overlap(){
        overlap_count = new int[this.time_term];

        for (int i = 0; i < this.time_term; i++) {
            overlap_count[i] = check_overlap(k_address.get(i));
        }
    }

    int check_overlap(String ex){
        int count = 0;
        for (int i = 0; i < this.time_term; i++) {
            if(ex.equals(k_address.get(i))){
                count++;
            }
        }
        return count;
    }

}
