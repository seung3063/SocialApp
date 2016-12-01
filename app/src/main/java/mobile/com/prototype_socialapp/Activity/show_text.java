package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import mobile.com.prototype_socialapp.R;

public class show_text extends AppCompatActivity {

    private String article_idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);

        Intent intent=getIntent();
        article_idx=intent.getStringExtra("idx");
        Log.d("idx",article_idx);
    }
}
