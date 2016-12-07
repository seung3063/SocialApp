package mobile.com.prototype_socialapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import mobile.com.prototype_socialapp.R;

public class Personal_activity extends AppCompatActivity {

    private Button user_info_mofication_btn;
    private Button my_board_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        user_info_mofication_btn=(Button)findViewById(R.id.btn_userinfomodi);
        my_board_btn=(Button)findViewById(R.id.btn_myboard);

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
    }
}

