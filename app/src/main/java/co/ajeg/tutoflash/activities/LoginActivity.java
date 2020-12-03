package co.ajeg.tutoflash.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.Database;
import co.ajeg.tutoflash.model.User;


public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tiet_login_username;
    private TextInputLayout tiet_login_password;
    private Button btn_login_iniciar;
    private SignInButton btn_login_google;
    private ImageView banner;
    private Database database;
    private Autenticacion autenticacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.autenticacion = new Autenticacion(this);



        //Andres
        this.tiet_login_username = findViewById(R.id.tiet_login_username);
        this.tiet_login_password = findViewById(R.id.tiet_login_password);

        this.btn_login_iniciar = findViewById(R.id.btn_login_iniciar);
        this.btn_login_google = findViewById(R.id.btn_login_google);
        banner = findViewById(R.id.banner);


        this.btn_login_iniciar.setOnClickListener(this::login);
        this.btn_login_google.setOnClickListener(this::loginGoogle);

    }

    public void login(View v) {
        String userName = tiet_login_username.getEditText().getText().toString();
        String password = tiet_login_password.getEditText().getText().toString();

        this.autenticacion.login(userName, password, user -> {
            launchHome(user);
        });
    }

    public void loginGoogle(View v) {
        this.autenticacion.loginWithGoogle();
    }

    public void launchHome(User user) {
        if(user != null){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("myUser", user);
            startActivity(i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.autenticacion.getCurrentUser(user -> {
            launchHome(user);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        this.autenticacion.onActivityResult(requestCode, resultCode, data, user -> {
            launchHome(user);
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}