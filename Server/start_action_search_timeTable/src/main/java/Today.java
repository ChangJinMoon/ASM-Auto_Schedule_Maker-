import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Today {
    LocalDate now;
    private int year;
    private int month;
    private int day;
    private String day_kor_version;
    List<String> kor_day ;

    Today set_current_time(){
        now = LocalDate.now();
        return this;
    }

    public String getDay_kor_version(){
        return this.day_kor_version;
    }

    void set_date_kor_version(){
        year = now.getYear();
        month = now.getMonthValue();
        day = now.getDayOfMonth();
        day_kor_version = change_day_engTokr(now.getDayOfWeek().getValue());

        System.out.println(Integer.toString(year)+"년 "+Integer.toString(month)+"월 "+Integer.toString(day)+"일 "+day_kor_version+"요일");
    }

    String change_day_engTokr(int day_of_week_value){
        set_kor_day_list();
        return kor_day.get(day_of_week_value-1);
    }

    void set_kor_day_list(){
        kor_day = new ArrayList<>();
        kor_day.add("월");
        kor_day.add("회");
        kor_day.add("수");
        kor_day.add("목");
        kor_day.add("금");
        kor_day.add("토");
        kor_day.add("일");
    }
}
