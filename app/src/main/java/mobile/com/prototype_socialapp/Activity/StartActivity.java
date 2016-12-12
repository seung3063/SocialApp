package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.LOGIN_KEY;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

public class StartActivity extends AppCompatActivity {

    private VolleySingleton volley;
    private RequestQueue requestQueue;



    private LOGIN_KEY login_key;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        volley=VolleySingleton.getmInstance(this);
        requestQueue=volley.getRequestQueue();

        pref = getSharedPreferences("auto_login", MODE_PRIVATE);
        editor = pref.edit();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                if (pref.getString("id","")==""){
                    Intent intent=new Intent(StartActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    LOGIN_KEY login_key=LOGIN_KEY.getInstance();

                    String idx=pref.getString("idx","");
                    String user_name=pref.getString("user_name","");
                    String user_age=pref.getString("user_age","");
                    String user_region=pref.getString("user_region","");
                    String user_act=pref.getString("user_act","");
                    String user_sex=pref.getString("user_sex","");
                    String user_role=pref.getString("user_role","");

                    login_key.SetID(idx);

                    login_key.setUser_name(user_name);
                    login_key.setUser_age(user_age);
                    login_key.setStr_user_sex(user_sex);
                    login_key.setUser_role(user_role);
                    login_key.setUser_act(user_act);
                    login_key.setUser_region(user_region);
                    Intent intent=new Intent(StartActivity.this,Main_Category_Activity.class);
                    startActivity(intent);
                    finish();
                }



            }
        }, 2000);
    }
}
