package co.ajeg.tutoflash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(()->{
            new Autenticacion(this, (user)->{
                 Intent intent = null;

                if(user == null){
                    intent = new Intent(SplashScreen.this, PreLogin.class);
                }else{
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                }

                startActivity(intent);
                finish();
            });


        },3000);
    }
}