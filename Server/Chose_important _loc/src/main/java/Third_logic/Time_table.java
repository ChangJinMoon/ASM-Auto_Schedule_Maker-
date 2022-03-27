package Third_logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time_table {
    private String id;
    private String location;
    private String day_string;
    private int day;
    private Date arrive,depart;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getDay_string() { return day_string; }

    public void setDay_string(String day_string) { this.day_string = day_string; }

    public int getDay() { return day; }

    public void setDay(int day) { this.day = day; }

    public Date getArrive() { return arrive; }

    public void setArrive(String time_string) {
        SimpleDateFormat transform = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            this.arrive = transform.parse(time_string);
        }catch (ParseException e){
            System.err.println(e);
        }
    }

    public void setArrive(Date time){
        this.arrive = time;
    }

    public Date getDepart() { return depart; }

    public void setDepart(String time_string) {
        SimpleDateFormat transform = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            this.depart = transform.parse(time_string);
        }catch (ParseException e){
            System.err.println(e);
        }
    }

    public void setDepart(Date time){
        this.depart = time;
    }
}
