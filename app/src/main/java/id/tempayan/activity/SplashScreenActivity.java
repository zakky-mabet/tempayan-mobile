package id.tempayan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import id.tempayan.R;

public class SplashScreenActivity extends AppCompatActivity {

    LinearLayout l1,l2;
    Animation uptodown,dowmtoup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        l1 = (LinearLayout) findViewById(R.id.l1);
        l2= (LinearLayout) findViewById(R.id.l2);

        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown );
        dowmtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup );
        l1.setAnimation(uptodown);
        l2.setAnimation(dowmtoup);

        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(2000); // set Waktu Pending selama 1 detik
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }finally {
                    startActivity(new Intent(SplashScreenActivity.this, SigninActivity.class));
                    finish(); // Menutup Activity
                }
            }
        };
        thread.start();


    }
}
