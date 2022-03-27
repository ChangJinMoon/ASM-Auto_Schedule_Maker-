package Common;

import java.util.ArrayList;
import java.util.List;

public class Two_cordinate_check {

    private List<Double> temp_cor, now_cor;
    private boolean result = false ;

    public void set_two_cor(List<Double> temp_cor , List<Double> now_cor) {
        this.temp_cor = temp_cor;
        this.now_cor = now_cor;
    }

    public boolean getResult() {
        return result;
    }

    public void check(){
        if(empty_check())
            return;

        if(temp_cor.size() != 3)
            return;

        double accuracy =temp_cor.get(2);

        double distance = circulate_distance_m();

        System.out.println("check_distance:" + distance);

        if(distance > accuracy)
            result = false;
        else
            result = true;

    }

    private double circulate_distance_m(){
        double temp_x = temp_cor.get(0);
        double temp_y = temp_cor.get(1);
        double now_x = now_cor.get(0);
        double now_y = now_cor.get(1);

        double theta = temp_y - now_y;
        double distance =Math.sin(deg2rad(temp_x)) * Math.sin(deg2rad(now_x))
                + Math.cos(deg2rad(temp_x)) * Math.cos(deg2rad(now_x)) * Math.cos(deg2rad(theta));

        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515;

        double distance_meter = distance * 1609.344;

        return distance_meter;
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private boolean empty_check(){
        if(temp_cor.isEmpty() || now_cor.isEmpty())
            return true;
        else
            return false;
    }

    public static void main(String args[]){
        List<Double> temp = new ArrayList<>();
        List<Double> now = new ArrayList<>();

        temp.add(37.5413868);
        temp.add(127.0839414);
        temp.add(13.284);

        now.add(37.5413907);
        now.add(127.0839513);

        Two_cordinate_check cordinate_check = new Two_cordinate_check();
        cordinate_check.set_two_cor(temp,now);
        cordinate_check.check();
        System.out.println("result:"+cordinate_check.getResult());
    }
}
