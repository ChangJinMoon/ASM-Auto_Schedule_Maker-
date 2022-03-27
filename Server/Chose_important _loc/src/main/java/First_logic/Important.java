package First_logic;

import java.util.Date;

public class Important {

    private String k_address;
    private String day_of_week;
    private int important;
    private int day;
    private Date departTime,arriveTime;


    public String getK_address() {return k_address;}

    public void setK_address(String k_address) {this.k_address = k_address;}

    public int getImportant() {return important;}

    public void setImportant(int important) {this.important = important;}

    public int getDay() {return day;}

    public void setDay(int day) {this.day = day;}

    public String getDay_of_week() {return day_of_week;}

    public void setDay_of_week(String day_of_week) { this.day_of_week = day_of_week; }

    public void setArriveTime(Date arriveTime){ this.arriveTime = arriveTime;}

    public void setDepartTime(Date departTime){ this.departTime = departTime;}

    public Date getArriveTime(){return arriveTime;}

    public Date getDepartTime(){return departTime;}
}
