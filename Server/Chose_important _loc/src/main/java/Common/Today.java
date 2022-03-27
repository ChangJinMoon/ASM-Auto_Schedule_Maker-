package Common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Today {
    LocalDate now;

    private int year;

    private int month;

    private int day;

    private String day_kor_version;

    List<String> kor_day;

    public Today set_current_time() {
        this.now = LocalDate.now();
        return this;
    }

    public String getDay_kor_version() {
        set_date_kor_version();
        return this.day_kor_version;
    }

    public void set_date_kor_version() {
        set_kor_day_list();
        this.year = this.now.getYear();
        this.month = this.now.getMonthValue();
        this.day = this.now.getDayOfMonth();
        this.day_kor_version = change_day_engTokr(this.now.getDayOfWeek().getValue());
    }

    String change_day_engTokr(int day_of_week_value) {
        set_kor_day_list();
        return this.kor_day.get(day_of_week_value - 1);
    }

    void set_kor_day_list() {
        this.kor_day = new ArrayList<>();
        this.kor_day.add("월");
        this.kor_day.add("화");
        this.kor_day.add("수");
        this.kor_day.add("목");
        this.kor_day.add("금");
        this.kor_day.add("토");
        this.kor_day.add("일");
    }

    public int getDay() {
        return day;
    }

    public static void main(String args[]){
        Today today = new Today();
        today.set_current_time();
        today.set_kor_day_list();
        today.set_date_kor_version();

        System.out.println("Day:"+ today.getDay());
    }
}
