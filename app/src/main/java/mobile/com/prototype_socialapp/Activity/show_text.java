package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.Adapter.CommentAdapter;
import mobile.com.prototype_socialapp.Bean.BoardTable;
import mobile.com.prototype_socialapp.Bean.Comment;
import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.LOGIN_KEY;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

public class show_text extends AppCompatActivity {

    private String article_idx;
    private String user_id;

    private RecyclerView comment_list;
    private RecyclerViewHeader header;
    private RecyclerView.LayoutManager layoutManager;
    private CommentAdapter adapter;
    private ArrayList<Comment> items = new ArrayList<>();


    private TextView show_title;
    private TextView show_role;
    private TextView show_name;
    private TextView show_region;
    private TextView show_time;
    private TextView show_content;
    private EditText comment_edittext;
    private Button comment_btn;
    private Button btn_modify;

    private VolleySingleton volley;
    private RequestQueue requestQueue;
    private final String show_comment_url ="http://10.0.3.2:8080/socialapp/show_comment.jsp";
    private final String show_article_url ="http://10.0.3.2:8080/socialapp/show_article.jsp";
    private final String comment_insert_url ="http://10.0.3.2:8080/socialapp/insert_comment.jsp";
    //private final String show_artile_url ="http://52.78.9.48:8080/socialapp/show_article.jsp";
    //private final String comment_insert_url ="http://52.78.9.48:8080/socialapp/insert_comment.jsp";


    String intent_String_title;
    String intent_String_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);

        Intent intent=getIntent();
        article_idx=intent.getStringExtra("idx");
        user_id=intent.getStringExtra("user_id");
        Log.d("idx",article_idx);
        Log.d("user_id",user_id);
        comment_list=(RecyclerView)findViewById(R.id.comment_list);
        header=(RecyclerViewHeader)findViewById(R.id.header);
        layoutManager = new LinearLayoutManager(this);
        comment_list.setLayoutManager(layoutManager);
        header.attachTo(comment_list);
        adapter=new CommentAdapter(this,items);
        comment_list.setAdapter(adapter);

        show_title=(TextView)findViewById(R.id.show_title);
        show_role=(TextView)findViewById(R.id.show_role);
        show_name=(TextView)findViewById(R.id.show_name);
        show_region=(TextView)findViewById(R.id.show_region);
        show_time=(TextView)findViewById(R.id.show_time);
        show_content=(TextView)findViewById(R.id.show_content);
        comment_edittext=(EditText)findViewById(R.id.comment_edittext);
        comment_btn=(Button)findViewById(R.id.comment_button);
        btn_modify=(Button)findViewById(R.id.modify_btn);

        volley=VolleySingleton.getmInstance(this);
        requestQueue=volley.getRequestQueue();

        if (LOGIN_KEY.getInstance().GetID().equals(user_id))
        {
            btn_modify.setVisibility(View.VISIBLE);
        }

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(show_text.this,text_modification.class);
                intent.putExtra("idx",article_idx);
                intent.putExtra("title",intent_String_title);
                intent.putExtra("content",intent_String_content);
                startActivity(intent);
            }
        });

        StringRequest strRequest = new StringRequest(Request.Method.POST, show_article_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject object=new JSONObject(response);
                            String user_id=object.getString("user_id");
                            String main_category=object.getString("main_category");
                            String sub_category=object.getString("sub_category");
                            String title=object.getString("title");
                            String content=object.getString("content");
                            String time=object.getString("time");
                            String num_comment=object.getString("num_of_comment");
                            String user_name=object.getString("user_name");
                            String user_region=object.getString("user_region");
                            String user_role=object.getString("user_role");

                            intent_String_title=title;
                            intent_String_content=content;


                            show_title.setText(title);

                            show_name.setText(user_name);
                            show_region.setText(user_region);
                            show_time.setText(time);
                            show_content.setText(content);

                            String role="";
                            switch (user_role){
                                case ""+0:
                                    role="개발자";
                                    break;
                                case ""+1:
                                    role="디자이너";
                                    break;
                                case ""+2:
                                    role="기획자";
                                    break;
                                case ""+3:
                                    role="배우";
                                    break;
                                case ""+4:
                                    role="카메라감";
                                    break;
                                case ""+5:
                                    role="조명감독";
                                    break;
                                case ""+6:
                                    role="애니메이터";
                                    break;
                                case ""+7:
                                    role="작가";
                                    break;
                                case ""+8:
                                    role="감독";
                                    break;
                            }
                            show_role.setText(role);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("article_idx",""+article_idx);
                params.put("user_id",""+user_id);
                return params;
            }
        };
        requestQueue.add(strRequest);

        StringRequest strRequest_comment = new StringRequest(Request.Method.POST, show_comment_url,
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
                                Comment item=new Comment();
                                item.setUser_name(object.getString("user_name"));
                                item.setComment_content(object.getString("comment_content"));
                                items.add(item);
                            }
                            Log.d("array",""+jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.setItems(items);
                        adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(show_text.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("article_idx",article_idx);
                return params;
            }
        };
        requestQueue.add(strRequest_comment);



        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String comment=comment_edittext.getText().toString();
                LOGIN_KEY key=LOGIN_KEY.getInstance();
                final String device_user_name=key.getUser_name();


                StringRequest strRequest = new StringRequest(Request.Method.POST, comment_insert_url,
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
                        params.put("user_name",""+device_user_name);
                        params.put("comment",comment);
                        Log.d("check",article_idx+user_id+comment);
                        return params;
                    }
                };
                requestQueue.add(strRequest);
            }
        });
    }
}
