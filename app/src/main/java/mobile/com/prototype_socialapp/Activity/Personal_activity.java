package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.Adapter.CommentAdapter;
import mobile.com.prototype_socialapp.Adapter.MainRecycleviewAdapter;
import mobile.com.prototype_socialapp.Adapter.NoticeAdapter;
import mobile.com.prototype_socialapp.Bean.Comment;
import mobile.com.prototype_socialapp.Bean.Notice;
import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.LOGIN_KEY;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

public class Personal_activity extends AppCompatActivity {

    private Button user_info_mofication_btn;
    private Button my_board_btn;
    private Button btn_logout;

    private RecyclerView notice_list;
    private RecyclerView.LayoutManager layoutManager;
    private NoticeAdapter Adapter;
    private ArrayList<Notice> items = new ArrayList<>();

    private VolleySingleton volley;
    private RequestQueue requestQueue;

    //private final String delete_token_url="http://192.168.0.2:8080/socialapp/delete_token.jsp";
    //private final String show_notice_url="http://192.168.0.2:8080/socialapp/show_notice.jsp";
    private final String delete_token_url="http://52.78.9.48:8080/socialapp/delete_token.jsp";
    private final String show_notice_url="http://52.78.9.48:8080/socialapp/show_notice.jsp";


    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        pref = getSharedPreferences("auto_login", MODE_PRIVATE);
        editor = pref.edit();

        volley=VolleySingleton.getmInstance(this);
        requestQueue=volley.getRequestQueue();

        setSupportActionBar((Toolbar) findViewById(R.id.personal_toolbar));
        getSupportActionBar().setTitle("      Personal Page");
        user_info_mofication_btn=(Button)findViewById(R.id.btn_userinfomodi);
        my_board_btn=(Button)findViewById(R.id.btn_myboard);
        btn_logout=(Button)findViewById(R.id.btn_logout);

        notice_list=(RecyclerView)findViewById(R.id.notice_list);
        layoutManager = new LinearLayoutManager(this);
        notice_list.setLayoutManager(layoutManager);
        Adapter=new NoticeAdapter(this,items);
        notice_list.setAdapter(Adapter);

        StringRequest strRequest_notice = new StringRequest(Request.Method.POST, show_notice_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("main_list");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                Notice item=new Notice();
                                item.setUser_name(object.getString("user_name"));
                                items.add(item);
                            }
                            Log.d("array",""+jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Adapter.setItems(items);
                        Adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(Personal_activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_idx",""+LOGIN_KEY.getInstance().GetID());
                return params;
            }
        };
        requestQueue.add(strRequest_notice);







        user_info_mofication_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Personal_activity.this,user_info_modification.class);
                startActivity(intent);
            }
        });
        my_board_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Personal_activity.this,my_board.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();


                StringRequest strRequest = new StringRequest(Request.Method.POST, delete_token_url,
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
                        params.put("idx", LOGIN_KEY.getInstance().GetID());
                        return params;
                    }
                };
                requestQueue.add(strRequest);

                ActivityCompat.finishAffinity(Personal_activity.this);
            }
        });
    }
}

