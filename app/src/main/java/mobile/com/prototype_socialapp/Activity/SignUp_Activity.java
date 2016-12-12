package mobile.com.prototype_socialapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

public class SignUp_Activity extends AppCompatActivity {

    private EditText user_name;
    private EditText user_id;
    private EditText user_pw;
    private EditText user_pw_re;
    private EditText user_age;
    private EditText user_region;
    private EditText user_act;


    private RadioGroup radioGroup;
    private Button btn_signup;
    private Button btn_id_check;
    private VolleySingleton volley;
    private RequestQueue requestQueue;
    //private final String user_info_url ="http://192.168.0.2:8080/socialapp/user_info.jsp";
    //private final String id_check_url="http://192.168.0.2:8080/socialapp/id_check.jsp";
    private final String user_info_url ="http://52.78.9.48:8080/socialapp/user_info.jsp";
    private final String id_check_url="http://52.78.9.48:8080/socialapp/id_check.jsp";
    private Spinner role_category_spinner;

    private int check_num;
    private Boolean check_ok=false;

    private String str_role="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);

        volley=VolleySingleton.getmInstance(this);
        requestQueue=volley.getRequestQueue();

        radioGroup=(RadioGroup)findViewById(R.id.radio_group);
        btn_signup=(Button)findViewById(R.id.btn_signup_true);
        btn_id_check=(Button)findViewById(R.id.btn_id_check);
        user_name=(EditText)findViewById(R.id.user_name);
        user_id=(EditText)findViewById(R.id.user_id);
        user_pw=(EditText)findViewById(R.id.user_pw);
        user_pw_re=(EditText)findViewById(R.id.user_pw_re);
        user_age=(EditText)findViewById(R.id.user_age);
        user_region=(EditText)findViewById(R.id.user_region);
        user_act=(EditText)findViewById(R.id.user_act);
        role_category_spinner =(Spinner)findViewById(R.id.role_category);

        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.role_category_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role_category_spinner.setAdapter(adapter);
        role_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(""+parent.getItemAtPosition(position).toString(),"여기여기");
                Log.d(""+position,"여기여기");
                str_role=""+position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btn_id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_user_id=user_id.getText().toString();

                StringRequest strRequest = new StringRequest(Request.Method.POST, id_check_url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    JSONArray jsonArray=jsonObject.getJSONArray("datasend");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        jsonObject = jsonArray.getJSONObject(i);

                                        check_num=jsonObject.getInt("check_num");
                                    }
                                    Log.d("check_num",""+check_num);
                                    if (check_num==0){
                                        check_ok=true;
                                        Toast.makeText(getApplicationContext(), "아이디 사용 가능", Toast.LENGTH_SHORT).show();
                                    }else{
                                        check_ok=false;
                                        Toast.makeText(getApplicationContext(), "이미 있는 아이디", Toast.LENGTH_SHORT).show();
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
                                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id",str_user_id);
                        return params;
                    }
                };
                requestQueue.add(strRequest);
//                user_id.setFocusable(false);
//                user_id.setClickable(false);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String str_user_name=user_name.getText().toString();
                final String str_user_id=user_id.getText().toString();
                final String str_user_pw=user_pw.getText().toString();
                final String str_user_pw_re=user_pw_re.getText().toString();
                final String str_user_age=user_age.getText().toString();
                final String str_user_region=user_region.getText().toString();
                final String str_user_act=user_act.getText().toString();

                RadioButton radioButton=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                final String str_user_sex=radioButton.getText().toString();
                Log.d("sex",""+str_user_sex);


                if(check_ok && str_user_pw.equals(str_user_pw_re)){
                    StringRequest strRequest = new StringRequest(Request.Method.POST, user_info_url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response)
                                {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
                            params.put("user_name",str_user_name);
                            params.put("user_pw",str_user_pw);
                            params.put("user_id",str_user_id);
                            params.put("user_age",str_user_age);
                            params.put("user_region",str_user_region);
                            params.put("user_act",str_user_act);
                            params.put("user_sex",str_user_sex);
                            params.put("user_role",str_role);

                            return params;
                        }
                    };
                    requestQueue.add(strRequest);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"아이디와 패스워드를 다시 확인",Toast.LENGTH_LONG).show();
                    if(str_user_pw.equals(str_user_pw_re))
                        Log.d("equal","dd");
                }

            }
        });
    }
}
