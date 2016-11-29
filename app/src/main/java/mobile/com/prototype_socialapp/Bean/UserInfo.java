package mobile.com.prototype_socialapp.Bean;

/**
 * Created by SeungJun on 2016. 11. 28..
 */
public class UserInfo {

    private int idx;
    private String user_name;
    private String user_id;
    private String user_pw;
    private int user_age;
    private String user_sex;
    private String user_region;
    private String user_act;
    private String user_role;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getUser_act() {
        return user_act;
    }

    public void setUser_act(String user_act) {
        this.user_act = user_act;
    }

    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getUser_region() {
        return user_region;
    }

    public void setUser_region(String user_region) {
        this.user_region = user_region;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }
}
