package Common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {
    private String my_id;
    private String another_id;
    private String appointment_time;
    private String appointment_address;
    private String index_s;
    private int set_appoint_agree;
    private Date time_format;


    public String getMy_id() { return my_id; }

    public void setMy_id(String my_id) { this.my_id = my_id; }

    public String getAnother_id() { return another_id; }

    public void setAnother_id(String another_id) { this.another_id = another_id; }

    public String getAppointment_time() { return appointment_time; }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
        set_date();
    }

    public String getAppointment_address() { return appointment_address; }

    public void setAppointment_address(String appointment_address) { this.appointment_address = appointment_address; }

    void set_date(){
        SimpleDateFormat transform = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            time_format = transform.parse(appointment_time);
        }catch (ParseException e){
            System.out.println(e);
        }
    }

    public void setSet_appoint_agree(int set_appoint_agree) {
        // 0 -> new 1 -> another user accepted appointment
        this.set_appoint_agree = set_appoint_agree;
    }

    public int getSet_appoint_agree() {
        return set_appoint_agree;
    }

    public String getIndex_s() {
        return index_s;
    }

    public void setIndex_s(String index_s) {
        this.index_s = index_s;
    }

    public Date getTime_format() {
        return time_format;
    }
}
