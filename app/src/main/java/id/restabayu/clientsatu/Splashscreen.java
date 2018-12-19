package id.restabayu.clientsatu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import id.restabayu.clientsatu.welcome.WelcomeActivity;

//import java.util.logging.Handler;

public class Splashscreen extends AppCompatActivity {

    private int show = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent home=new Intent(Splashscreen.this, SigninActivity.class);
                startActivity(home);
                finish();

            }
        },show);
    }
}