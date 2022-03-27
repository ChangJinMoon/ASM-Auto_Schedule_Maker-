public class Member {
    private String id;
    private String password;

    public void setId(String id){
        this.id = id;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getId() {
        return id;
    }
    public String getPassword(){
        return this.password;
    }

    public boolean login_access(String password){
        if(this.password.equals(password))
            return true;
        else
            return false;
    }
}
