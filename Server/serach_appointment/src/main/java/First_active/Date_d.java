package First_active;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Date_d {

    public LocalDateTime get_now_time(){
        LocalDateTime now = LocalDateTime.now();
        return now;
    }

    public boolean date_compare_1hour(LocalDateTime now, Date appointment_time){
        int n_d = now.getDayOfMonth(),n_h = now.getHour(), n_m = now.getMinute();
        int ap_d = appointment_time.getDate(), ap_h = appointment_time.getHours(), ap_m = appointment_time.getMinutes();

        System.out.println("now:"+Integer.toString(n_d)+"/"+Integer.toString(n_h)+":"+Integer.toString(n_m)+"");
        System.out.println("ap_D:"+Integer.toString(ap_d)+"/"+Integer.toString(ap_h)+":"+Integer.toString(ap_m)+"");

        //비교 날짜가 지났는지 확인
        if(n_d > ap_d)
            return true;

        //시간차이가 한시간 이상나는 확인
        int result = (ap_h - n_h)*60 + (ap_m - n_m);
        if(result < -60)
            return true;

        return false;
    }
}
