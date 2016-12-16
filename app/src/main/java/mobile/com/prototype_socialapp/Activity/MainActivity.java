package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.LOGIN_KEY;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

public class MainActivity extends AppCompatActivity {

    private TextView btn_signup;
    private Button btn_login;
    private EditText user_id;
    private EditText user_pw;

    private VolleySingleton volley;
    private RequestQueue requestQueue;

    //private final String login_url="http://192.168.0.2:8080/socialapp/login.jsp";
    private final String login_url="http://52.78.9.48:8080/socialapp/login.jsp";

    private LOGIN_KEY login_key;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("auto_login", MODE_PRIVATE);
        editor = pref.edit();

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();
        Log.d("Token",""+FirebaseInstanceId.getInstance().getToken());

        btn_signup=(TextView) findViewById(R.id.btn_signup);
        btn_login=(Button)findViewById(R.id.btn_login);
        user_id=(EditText)findViewById(R.id.user_id);
        user_pw=(EditText)findViewById(R.id.user_pw);

        volley=VolleySingleton.getmInstance(this);
        requestQueue=volley.getRequestQueue();

        login_key=LOGIN_KEY.getInstance();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignUp_Activity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String str_user_id=user_id.getText().toString();
                final String str_user_pw=user_pw.getText().toString();

                StringRequest strRequest = new StringRequest(Request.Method.POST, login_url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                try{
                                    JSONObject jsonObject=new JSONObject(response);
                                    String check_id=jsonObject.getString("login_key");

                                    switch (check_id){
                                        case "no_id" :
                                            Toast.makeText(getApplicationContext(), "아이디가 없습니다", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "no_pw" :
                                            Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                                            break;
                                        default:


                                            String user_name=jsonObject.getString("user_name");
                                            String user_age=jsonObject.getString("user_age");
                                            String user_region=jsonObject.getString("user_region");
                                            String user_act=jsonObject.getString("user_act");
                                            String user_sex=jsonObject.getString("user_sex");
                                            String user_role=jsonObject.getString("user_role");

                                            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                            login_key.SetID(check_id);

                                            login_key.setUser_name(user_name);
                                            login_key.setUser_age(user_age);
                                            login_key.setStr_user_sex(user_sex);
                                            login_key.setUser_role(user_role);
                                            login_key.setUser_act(user_act);
                                            login_key.setUser_region(user_region);

                                            editor.putString("id", str_user_id);
                                            editor.putString("pw", str_user_pw);

                                            editor.putString("idx", check_id);
                                            editor.putString("user_name", user_name);
                                            editor.putString("user_age", user_age);
                                            editor.putString("user_sex", user_sex);
                                            editor.putString("user_role", user_role);
                                            editor.putString("user_act", user_act);
                                            editor.putString("user_region", user_region);
                                            editor.commit();



                                            Intent intent=new Intent(MainActivity.this,Main_Category_Activity.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id",str_user_id);
                        params.put("user_pw",str_user_pw);
                        params.put("token",FirebaseInstanceId.getInstance().getToken());
                        return params;
                    }
                };
                requestQueue.add(strRequest);
            }
        });
    }
}
