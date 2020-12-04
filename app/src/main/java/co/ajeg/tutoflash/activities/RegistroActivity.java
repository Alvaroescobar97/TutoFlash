package co.ajeg.tutoflash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import co.ajeg.tutoflash.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class RegistroActivity extends AppCompatActivity {

    private CircleImageView civ_registro_imagen;
    private TextInputLayout til_registro_nombre;
    private TextInputLayout til_registro_email;
    private TextInputLayout til_registro_carrera;
    private TextInputLayout til_registro_password;
    private TextInputLayout til_registro_re_password;
    private Button btn_registro_crear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.civ_registro_imagen = findViewById(R.id.civ_registro_imagen);

        this.til_registro_nombre = findViewById(R.id.til_registro_nombre);
        this.til_registro_email = findViewById(R.id.til_registro_email);
        this.til_registro_carrera = findViewById(R.id.til_registro_carrera);
        this.til_registro_password = findViewById(R.id.til_registro_password);
        this.til_registro_re_password = findViewById(R.id.til_registro_re_password);
        this.btn_registro_crear = findViewById(R.id.btn_registro_crear);

        this.btn_registro_crear.setOnClickListener(this::crearUsuario);

    }

    public void crearUsuario(View v){

        String nombre = this.til_registro_nombre.getEditText().getText().toString();
        String email = this.til_registro_nombre.getEditText().getText().toString();
        String carrera = this.til_registro_nombre.getEditText().getText().toString();
        String password = this.til_registro_nombre.getEditText().getText().toString();
        String rePassword = this.til_registro_nombre.getEditText().getText().toString();

        if(!nombre.equals("") && !email.equals("") && !carrera.equals("")  && !password.equals("") && password.equals(rePassword)){

        }

    }
}