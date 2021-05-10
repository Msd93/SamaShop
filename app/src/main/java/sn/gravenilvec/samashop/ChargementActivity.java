package sn.gravenilvec.samashop;

import androidx.appcompat.app.AppCompatActivity;
import sn.gravenilvec.samashop.auth.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ChargementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);
        
       Runnable runnable=new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        new Handler().postDelayed(runnable,3000);
    }

}
