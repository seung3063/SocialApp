package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

public class text_modification extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private Button btn_mod;

    private VolleySingleton volley;
    private RequestQueue requestQueue;
    //private final String modify_board_table_url ="http://192.168.0.2:8080/socialapp/modify_board_table.jsp";
    private final String modify_board_table_url ="http://52.78.9.48:8080/socialapp/modify_board_table.jsp";

    private String article_idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_modification);

        volley=VolleySingleton.getmInstance(this);
        requestQueue=volley.getRequestQueue();

        title=(EditText)findViewById(R.id.title_edittext_text_mod);
        content=(EditText)findViewById(R.id.content_edittext_text_mod);
        btn_mod=(Button)findViewById(R.id.register_btn_mod);

        Intent intent=getIntent();
        article_idx=intent.getStringExtra("idx");
        title.setText(intent.getStringExtra("title"));
        content.setText(intent.getStringExtra("content"));

        btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String str_title=title.getText().toString();
                final String str_content=content.getText().toString();


                StringRequest strRequest = new StringRequest(Request.Method.POST, modify_board_table_url,
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
                        params.put("article_idx",article_idx);
                        params.put("title",str_title);
                        params.put("content",str_content);
                        Log.d(article_idx+str_content+str_content,"aaa");
                        return params;
                    }
                };
                requestQueue.add(strRequest);
                finish();
            }
        });
    }
}
