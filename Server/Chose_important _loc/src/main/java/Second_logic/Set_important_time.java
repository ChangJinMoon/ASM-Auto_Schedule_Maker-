package Second_logic;

import First_logic.Important;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Set_important_time {
    private Important important;
    private List<Important> temp_list = new ArrayList<>();
    private List<Daily_location> daily_locationList;
    private Check_time_interval check_time_interval = new Check_time_interval();

    private Date standardTime,departTime,arriveTime;
    public void init(){

        boolean check = false;

        for (int i = 0; i < daily_locationList.size(); i++) {
            if(i == 0){
                standardTime = daily_locationList.get(i).getTime();
                arriveTime = standardTime;
            }
            else{
                check = check_time_interval.interval_check(check_time_interval
                        .get_time_interval(standardTime,daily_locationList.get(i).getTime()),90);
                if(check){
                    //1시간 30분이내에 찍힌 장소이면
                    standardTime = daily_locationList.get(i).getTime();
                }
                else{
                    //1시간 30분 이상이면
                    check = check_time_interval.interval_check(check_time_interval
                            .get_time_interval(arriveTime,standardTime),60);
                    //도착시간과 기준시간(도착시간 가정)의 차이가 1시간이상 나는지 확인
                    if(!check){
                        //머무른 시간이 1시간이상
                        departTime = standardTime;
                        important.setArriveTime(arriveTime);
                        important.setDepartTime(departTime);
                        temp_list.add(make_new_im(important));
                    }

                    //머무른 시간이 1시간이내 or 전 시간대 정보 저장 이후 기준 전환
                    standardTime = daily_locationList.get(i).getTime();
                    arriveTime = standardTime;
                }
            }
        }

        //도착시간과 기준시간(도착시간 가정)의 차이가 1시간이상 나는지 확인
        check = check_time_interval.interval_check(check_time_interval
                .get_time_interval(arriveTime,standardTime),60);

        if(!check) {
            departTime = standardTime;
            important.setArriveTime(arriveTime);
            important.setDepartTime(departTime);
            temp_list.add(make_new_im(important));
        }

        System.out.println("-------------------------------------------");
        for (int i = 0; i < temp_list.size(); i++) {
            System.out.println(i+"번째 -도착시간:"+temp_list.get(i).getArriveTime()+" 출발시간:"+temp_list.get(i).getDepartTime());
        }
        System.out.println("-------------------------------------------");
    }

    public List<Important> getImportant_list() {
        return temp_list;
    }

    public void first_setting(Important important,List<Daily_location> daily_locationList) {
        this.important = important;
        this.daily_locationList = daily_locationList;
    }

    private Important make_new_im(Important im){
        Important important = new Important();
        important.setImportant(im.getImportant());
        important.setArriveTime(im.getArriveTime());
        important.setDepartTime(im.getDepartTime());
        important.setDay(im.getDay());
        important.setK_address(im.getK_address());
        important.setDay_of_week(im.getDay_of_week());
        return important;
    }
}
