package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Singleton.LOGIN_KEY;

public class Main_Category_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__category_);
    }

    public void Onclick(View view){
        String category_num="";
        switch (view.getId()){
            case R.id.image_develop:
                category_num="0";
                break;
            case R.id.image_media:
                category_num="1";
                break;
            case R.id.image_animation:
                category_num="2";
                break;
            case R.id.image_study:
                category_num="3";
                break;
        }
        Intent intent = new Intent(Main_Category_Activity.this,Board_Action_Activity.class);
        intent.putExtra("category_num",category_num);
        startActivity(intent);
    }
}
