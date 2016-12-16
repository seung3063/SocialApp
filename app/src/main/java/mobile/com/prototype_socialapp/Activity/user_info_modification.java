package mobile.com.prototype_socialapp.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.LOGIN_KEY;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

public class user_info_modification extends AppCompatActivity {

    private LOGIN_KEY key;
    String user_idx;

    private VolleySingleton volley;
    private RequestQueue requestQueue;

    private EditText user_name;
    private EditText user_age;
    private EditText user_region;
    private EditText user_act;
    private RadioGroup radioGroup;
    private Button btn_modify;
    private Spinner role_category_spinner;

    private String str_role;

    //private final String modify_user_info_url="http://192.168.0.2:8080/socialapp/modify_user_info.jsp";
    private final String modify_user_info_url="http://52.78.9.48:8080/socialapp/modify_user_info.jsp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_modification);

        setSupportActionBar((Toolbar) findViewById(R.id.user_info_mod_toolbar));
        getSupportActionBar().setTitle("      회원 정보 수정");

        key=LOGIN_KEY.getInstance();
        user_idx=key.GetID();
        Log.d("user_idx",user_idx);
        Log.d("user_name",key.getUser_name());
        Log.d("user_age",key.getUser_age());
        Log.d("user_region",key.getUser_region());
        Log.d("user_act",key.getUser_act());
        Log.d("str_user_sex",key.getStr_user_sex());
        Log.d("user_role",key.getUser_role());

        volley=VolleySingleton.getmInstance(this);
        requestQueue=volley.getRequestQueue();

        user_name=(EditText)findViewById(R.id.user_name_mod);
        user_age=(EditText)findViewById(R.id.user_age_mod);
        user_region=(EditText)findViewById(R.id.user_region_mod);
        user_act=(EditText)findViewById(R.id.user_act_mod);
        role_category_spinner =(Spinner)findViewById(R.id.role_category_mod);
        radioGroup=(RadioGroup)findViewById(R.id.radio_group_mod);
        RadioButton radioButton=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        btn_modify=(Button)findViewById(R.id.btn_modify);

        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.role_category_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role_category_spinner.setAdapter(adapter);


        user_name.setText(key.getUser_name());
        user_age.setText(key.getUser_age());
        user_region.setText(key.getUser_region());
        user_act.setText(key.getUser_act());

        role_category_spinner.setSelection(Integer.parseInt(key.getUser_role()));

        if(key.getStr_user_sex().equals("남자"))
            radioGroup.check(R.id.male_mod);
        else
            radioGroup.check(R.id.female_mod);

        role_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                Log.d(""+parent.getItemAtPosition(position).toString(),"여기여기");
                Log.d(""+position,"여기여기");
                str_role=""+position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_user_name=user_name.getText().toString();
                final String str_user_age=user_age.getText().toString();
                final String str_user_region=user_region.getText().toString();
                final String str_user_act=user_act.getText().toString();

                AppCompatRadioButton radioButton=(AppCompatRadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                final String str_user_sex=radioButton.getText().toString();
                Log.d("sex",""+str_user_sex);

                StringRequest strRequest = new StringRequest(Request.Method.POST, modify_user_info_url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                key.setUser_name(str_user_name);
                                key.setUser_age(str_user_age);
                                key.setUser_region(str_user_region);
                                key.setUser_act(str_user_act);
                                key.setStr_user_sex(str_user_sex);
                                key.setUser_role(str_role);
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
                        params.put("user_idx",key.GetID());
                        params.put("user_name",str_user_name);
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

            }
        });


    }

}
