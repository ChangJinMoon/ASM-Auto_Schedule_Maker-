import java.sql.Connection;

public class Test {
    public static void main(String args[]){
        Connection con;
        con = new Db_connector().try_Connect();

        if(con == null){
            System.out.println("connect faild");
        }
        else{
            System.out.println("connect success");
        }
    }
}
