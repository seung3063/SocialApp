package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.Adapter.MainRecycleviewAdapter;
import mobile.com.prototype_socialapp.Bean.BoardTable;
import mobile.com.prototype_socialapp.Listener.RecyclerViewOnItemClickListener;
import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.LOGIN_KEY;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

public class my_board extends AppCompatActivity {
    private LOGIN_KEY key;
    private String user_idx;

    private RecyclerView my_main_recycle_view;
    private RecyclerView.LayoutManager layoutManager;
    private MainRecycleviewAdapter Adapter;

    private VolleySingleton volley;
    private RequestQueue requestQueue;
    //private final String url ="http://192.168.0.2:8080/socialapp/show_my_list.jsp";
    private final String url ="http://52.78.9.48:8080/socialapp/show_my_list.jsp";

    private ArrayList<BoardTable> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_board);

        setSupportActionBar((Toolbar) findViewById(R.id.my_board_toolbar));
        getSupportActionBar().setTitle("      내가 쓴글");

        key=LOGIN_KEY.getInstance();
        user_idx=key.GetID();
        Log.d("user_idx",user_idx);

        volley=VolleySingleton.getmInstance(this);
        requestQueue=volley.getRequestQueue();

        my_main_recycle_view =(RecyclerView)findViewById(R.id.my_main_recycle_view);
        layoutManager = new LinearLayoutManager(this);
        my_main_recycle_view.setLayoutManager(layoutManager);

        Adapter=new MainRecycleviewAdapter(this,items);
        my_main_recycle_view.setAdapter(Adapter);

        my_main_recycle_view.addOnItemTouchListener(new RecyclerViewOnItemClickListener(this,my_main_recycle_view,new RecyclerViewOnItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(my_board.this, show_text.class);
                intent.putExtra("idx",""+ items.get(position).getIdx());
                intent.putExtra("user_id",items.get(position).getUser_id());
                startActivity(intent); // 다음 화면으로 넘어간다.
            }

            @Override
            public void onItemLongClick(View v, int position) {
            }
        }));

        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
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
                                BoardTable item=new BoardTable();
                                item.setUser_id(object.getString("user_id"));
                                item.setUser_role(object.getString("user_role"));
                                item.setIdx(object.getInt("idx"));
                                item.setTitle(object.getString("title"));
                                item.setNum_of_comment(object.getString("num_of_comment"));
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
                        Toast.makeText(my_board.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idx",key.GetID());
                return params;
            }
        };
        requestQueue.add(strRequest);
    }
}
