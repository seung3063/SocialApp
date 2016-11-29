package mobile.com.prototype_socialapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

/**
 * Created by SeungJun on 16. 10. 4..
 */
public class PageFragment extends Fragment {
    private int mPageNumber;

    private RecyclerView main_recycle_view;
    private RecyclerView.LayoutManager layoutManager;
    private MainRecycleviewAdapter Adapter;

    private VolleySingleton volley;
    private RequestQueue requestQueue;
    private final String url ="http://10.0.3.2:8080/socialapp/show_main_list.jsp";

    private ArrayList<BoardTable> items = new ArrayList<>();

    public static PageFragment create(int pageNumber) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPageNumber = getArguments().getInt("page");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page,null);

        volley=VolleySingleton.getmInstance(getActivity());
        requestQueue=volley.getRequestQueue();

        main_recycle_view=(RecyclerView)view.findViewById(R.id.main_recycle_view);
        layoutManager = new LinearLayoutManager(getActivity());
        main_recycle_view.setLayoutManager(layoutManager);

        Adapter=new MainRecycleviewAdapter(getActivity(),items);
        main_recycle_view.setAdapter(Adapter);

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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                if (mPageNumber==0)
                    params.put("page_number","0");
                else
                    params.put("page_number","1");
                return params;
            }
        };
        requestQueue.add(strRequest);

        return view;
    }
}
