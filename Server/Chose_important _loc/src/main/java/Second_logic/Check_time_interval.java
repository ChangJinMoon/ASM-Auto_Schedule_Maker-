package Second_logic;

import java.util.Date;

public class Check_time_interval {

    public long get_time_interval(Date before, Date now){
        long interval;
        interval = (now.getTime() - before.getTime())/60000;//분
        return interval;
    }

    public boolean interval_check(long interval , int max){
        //시간의 차 1시간 30분이 기준
        int max_interval = max;

        if(interval < max_interval)
            return true;
        else
            return false;
    }

}
