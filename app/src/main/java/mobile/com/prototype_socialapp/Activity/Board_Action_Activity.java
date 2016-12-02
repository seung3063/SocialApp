package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.content.res.Configuration;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.Adapter.Pager_Adapter;
import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.VolleySingleton;

public class Board_Action_Activity extends ActionBarActivity{

    private String[] list_item={"웹앱개발","영상","3D애니메이션","스터디"};

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private ListView navigation_list;
    private ViewPager mPager;
    private TabLayout tabLayout;
    Pager_Adapter pager_adapter;

    private int category_num;

    private VolleySingleton volley;
    private RequestQueue requestQueue;
    //private final String page_list_url ="http://10.0.3.2:8080/socialapp/page_list.jsp";
    private final String page_list_url ="http://52.78.9.48:8080/socialapp/page_list.jsp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_action);

        volley=VolleySingleton.getmInstance(this);
        requestQueue=volley.getRequestQueue();

        Intent intent=getIntent();
        category_num= Integer.parseInt(intent.getStringExtra("category_num"));
        Log.d("category_num",""+category_num);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        mPager=(ViewPager)findViewById(R.id.pager);

        navigation_list =(ListView)findViewById(R.id.navigation_list);
        navigation_list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_item));
        navigation_list.setOnItemClickListener(new ListItemClickListner());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.rcvr_tl_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Need You"));
        tabLayout.addTab(tabLayout.newTab().setText("Need Team"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        pager_adapter =new Pager_Adapter(getSupportFragmentManager(),tabLayout.getTabCount(),0);
        mPager.setAdapter(pager_adapter);

        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        new ListItemClickListner().onItemClick(null,null,category_num,category_num);




    }


    public class ListItemClickListner implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

            pager_adapter.setMain_category_num(position);
            pager_adapter.notifyDataSetChanged();

            drawerLayout.closeDrawer(navigation_list);


        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            case R.id.addbutton:
                Log.d("dd","dd");
                Intent intent=new Intent(Board_Action_Activity.this,register_board.class);
                startActivity(intent);
                return true;
            case R.id.personal_button:
                Intent intent1=new Intent(Board_Action_Activity.this,Personal_activity.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
