package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.LOGIN_KEY;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

public class register_board extends AppCompatActivity {

    private Spinner main_category_spinner;
    private Spinner need_category_spinner;
    private EditText title_edittext;
    private EditText content_edittext;
    private Button register_btn;

    private String main_category;
    private String sub_category;

    private VolleySingleton volley;
    private RequestQueue requestQueue;
    //private final String login_url="http://192.168.0.2:8080/socialapp/board_table.jsp";
    private final String login_url="http://52.78.9.48:8080/socialapp/board_table.jsp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_board);

        volley=VolleySingleton.getmInstance(this);
        requestQueue=volley.getRequestQueue();

        main_category_spinner=(Spinner)findViewById(R.id.menu_spinner);
        need_category_spinner=(Spinner)findViewById(R.id.need_spinner);
        title_edittext=(EditText)findViewById(R.id.title_edittext);
        content_edittext=(EditText)findViewById(R.id.content_edittext);
        register_btn=(Button)findViewById(R.id.register_btn);

        ArrayAdapter<CharSequence> adapter1 =ArrayAdapter.createFromResource(this,R.array.main_category_array,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 =ArrayAdapter.createFromResource(this,R.array.need_category_array,android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        main_category_spinner.setAdapter(adapter1);
        need_category_spinner.setAdapter(adapter2);

        main_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(""+parent.getItemAtPosition(position).toString(),"여기여기");
                main_category=String.valueOf(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        need_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(""+parent.getItemAtPosition(position).toString(),"여기여기");
                sub_category=String.valueOf(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title;
                final String content;
                title=title_edittext.getText().toString();
                content=content_edittext.getText().toString();

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                final String formatDate = sdfNow.format(date);
                Log.d("date",formatDate);

                StringRequest strRequest = new StringRequest(Request.Method.POST, login_url,
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
                        params.put("user_id",""+LOGIN_KEY.getInstance().GetID());
                        params.put("main_category",main_category);
                        params.put("sub_category",sub_category);
                        params.put("title",title);
                        params.put("content",content);
                        params.put("date",formatDate);
                        return params;
                    }
                };

                if (content==null ||title==null)
                    Toast.makeText(register_board.this,"빈칸을 채워주세요",Toast.LENGTH_LONG).show();
                else{
                    requestQueue.add(strRequest);
                    finish();
                }

            }
        });
    }
}
