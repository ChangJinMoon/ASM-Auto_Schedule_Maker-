package Second_logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Daily_location {
    private String k_address;
    private String time_string;
    private int count;
    private Date time;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getK_address() {
        return k_address;
    }

    public void setK_address(String k_address) {
        this.k_address = k_address;
    }

    public String getTime_string() {
        return time_string;
    }

    public void setTime_string(String date_string){
        this.time_string = date_string;
        SimpleDateFormat transform = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            time = transform.parse(time_string);
        }catch (ParseException e){
            System.err.println(e);
        }
    }

    public Date getTime() {
        return time;
    }
}
