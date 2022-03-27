import java.sql.Connection;

public class Test_main {

    public static void main(String[] args){
        Connection con;
        Query_sender q_s = new Query_sender();
        Reply_manager r_m = new Reply_manager();

        String result,result_detail,user_id,user_password;

        user_id = "jin1004boy";
        user_password = "ckdwls31412";

        // 2.Connect db
        if((con = new Db_connector().try_Connect()) == null){
            System.out.println("db_connection faild");
            return;
        }

        // 3. Search id
        boolean answer = false;
        q_s.setCon(con);
        answer = q_s.search_query(new Group_query().return_check_id_query(user_id));

        if(answer){
            Member member = new Member();
            member.setId(user_id);
            member.setPassword(q_s.getAnswer_pass());
            if(member.login_access(user_password)){
                r_m.setResult("0");
                r_m.setResult_details("success");
            }
            else{
                r_m.setResult("1");
                r_m.setResult_details("Wrong password");
            }
        }
        else{
            r_m.setResult("2");
            r_m.setResult_details(q_s.getFaild_message());
        }
        System.out.println(r_m.return_reply_as_String());
    }
}
