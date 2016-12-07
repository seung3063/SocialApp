package mobile.com.prototype_socialapp.Singleton;

import android.widget.EditText;

/**
 * Created by SeungJun on 2016. 11. 10..
 */
public class LOGIN_KEY {
    private static LOGIN_KEY ourInstance = null;
    private String key_value;
    private String user_name;
    private String user_age;
    private String user_region;
    private String user_act;
    private String str_user_sex;
    private String user_role;

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_region() {
        return user_region;
    }

    public void setUser_region(String user_region) {
        this.user_region = user_region;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_act() {
        return user_act;
    }

    public void setUser_act(String user_act) {
        this.user_act = user_act;
    }

    public String getStr_user_sex() {
        return str_user_sex;
    }

    public void setStr_user_sex(String str_user_sex) {
        this.str_user_sex = str_user_sex;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String GetID(){
        return  key_value;
    }
    public void SetID(String value){
        key_value=value;
    }

    public static LOGIN_KEY getInstance() {
        if (ourInstance==null){
            ourInstance=new LOGIN_KEY();
        }
        return ourInstance;
    }

}
