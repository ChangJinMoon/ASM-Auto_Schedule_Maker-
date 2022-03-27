import com.google.gson.JsonObject;

public class Reply_manager {
    private String result = "never use";
    private String day;
    private boolean was_called = false;

    private String result_details = "never use";

    public void setResult(String result) {
        this.result = result;
    }

    public void setResult_details(String result_details){
        this.result_details = result_details;
    }


    JsonObject return_json_result() {
        JsonObject response = new JsonObject();
        response.addProperty("payload", this.result);
        response.addProperty("result_details",this.result_details);
        return response;
    }

    JsonObject return_simple(){
        JsonObject response = new JsonObject();
        response.addProperty("payload", this.result);
        return response;
    }

    String return_reply_as_String(){
        StringBuilder answer = new StringBuilder();
        answer.append(result);
        answer.append("\n");
        answer.append(result_details);
        return answer.toString();
    }
}
