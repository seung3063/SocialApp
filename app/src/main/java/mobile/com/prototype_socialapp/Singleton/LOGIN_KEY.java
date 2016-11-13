package mobile.com.prototype_socialapp.Singleton;

/**
 * Created by SeungJun on 2016. 11. 10..
 */
public class LOGIN_KEY {
    private static LOGIN_KEY ourInstance = null;
    private String key_value;

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
