package co.ajeg.tutoflash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.ajeg.tutoflash.R;

public class PreLogin extends AppCompatActivity {
    private Button btn_prelogin_register;
    private Button btn_prelogin_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

        btn_prelogin_register = findViewById(R.id.btn_prelogin_register);
        btn_prelogin_login = findViewById(R.id.btn_prelogin_login);

        btn_prelogin_login.setOnClickListener(this::login);
        btn_prelogin_register.setOnClickListener(this::register);

    }

    public void login (View v){
        Intent inten = new Intent(this, LoginActivity.class);
        startActivity(inten);
    }

    public void register (View v){
        Intent inten = new Intent(this, RegistroActivity.class);
        startActivity(inten);
    }


}