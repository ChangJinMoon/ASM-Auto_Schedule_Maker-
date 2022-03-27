package Common;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Appointment {
    private String my_id;
    private String another_id;
    private String appointment_time;
    private String appointment_address;
    private int set_appoint_agree;
    private String index;
    private Date time_format;


    public String getMy_id() { return my_id; }

    public void setMy_id(String my_id) {
        String temp = null;
        try {
            temp = new String(my_id.getBytes(StandardCharsets.ISO_8859_1),"utf-8");
        }catch (Exception e){
        }
        this.my_id = temp; }

    public String getAnother_id() { return another_id; }

    public void setAnother_id(String another_id) { this.another_id = another_id; }

    public String getAppointment_time() { return appointment_time; }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
        set_date();
        set_appointment_index();
    }

    public String getAppointment_address() { return appointment_address; }

    public void setAppointment_address(String appointment_address) {
        this.appointment_address = appointment_address;
    }

    void set_date(){
        SimpleDateFormat transform = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            time_format = transform.parse(appointment_time);
        }catch (ParseException e){
            System.out.println(e);
        }
    }
    public void default_appoint_agree() {
        // 0 -> new 1 -> another user accepted appointment
        this.set_appoint_agree = 0;
    }
    public void setSet_appoint_agree(int set_appoint_agree) {
        // 0 -> new 1 -> another user accepted appointment
        this.set_appoint_agree = set_appoint_agree;
    }

    public void set_appointment_index(){
        StringBuilder temp_appoint = new StringBuilder();
        int hours = time_format.getHours();
        int minute = time_format.getMinutes();

        temp_appoint.append(my_id.substring(0,4));
        temp_appoint.append("_");
        temp_appoint.append(another_id.substring(0,4));
        temp_appoint.append("_");
        temp_appoint.append(Integer.toString(hours));
        temp_appoint.append("_");
        temp_appoint.append(Integer.toString(minute));

        index = temp_appoint.toString();
    }

    public String get_appointment_index(){
        return index;
    }

    public int getSet_appoint_agree() {
        return set_appoint_agree;
    }
}
