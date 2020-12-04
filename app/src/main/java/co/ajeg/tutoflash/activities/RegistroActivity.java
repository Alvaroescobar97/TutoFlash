package co.ajeg.tutoflash.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.firebase.storage.StorageFirebase;
import co.ajeg.tutoflash.galeria.Galeria;
import co.ajeg.tutoflash.model.User;
import de.hdodenhof.circleimageview.CircleImageView;


public class RegistroActivity extends AppCompatActivity implements Galeria.OnCompleteListenerImage {

    private CircleImageView civ_registro_imagen;
    private TextInputLayout til_registro_nombre;
    private TextInputLayout til_registro_email;
    private TextInputLayout til_registro_carrera;
    private TextInputLayout til_registro_password;
    private TextInputLayout til_registro_re_password;
    private Button btn_registro_crear;
    private Galeria galeria;
    private String path;
    private Autenticacion autenticacion;
    private SignInButton btn_registro_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        galeria = new Galeria(this);
        galeria.getResultImage(this);
        autenticacion = new Autenticacion(this);
        this.civ_registro_imagen = findViewById(R.id.civ_registro_imagen);

        this.til_registro_nombre = findViewById(R.id.til_registro_nombre);
        this.til_registro_email = findViewById(R.id.til_registro_email);
        this.til_registro_carrera = findViewById(R.id.til_registro_carrera);
        this.til_registro_password = findViewById(R.id.til_registro_password);
        this.til_registro_re_password = findViewById(R.id.til_registro_re_password);
        this.btn_registro_crear = findViewById(R.id.btn_registro_crear);
        this.btn_registro_google = findViewById(R.id.btn_registro_google);

        this.btn_registro_google.setOnClickListener(this::signGoogle);

        this.btn_registro_crear.setOnClickListener(this::crearUsuario);
        this.civ_registro_imagen.setOnClickListener(this::addphoto);
    }



    public void crearUsuario(View v){

        String nombre = this.til_registro_nombre.getEditText().getText().toString();
        String email = this.til_registro_email.getEditText().getText().toString();
        String carrera = this.til_registro_carrera.getEditText().getText().toString();
        String password = this.til_registro_password.getEditText().getText().toString();
        String rePassword = this.til_registro_re_password.getEditText().getText().toString();

        if(!nombre.equals("") && !email.equals("") && !carrera.equals("")  && !password.equals("") && password.equals(rePassword)){
            //String id, String date, String name, String email, String carrera, String image
            Date date = new Date();
            User user = null;
            if (path != null) {
                user = new User("", date.toString(), nombre, email, carrera, "image");
            }else {
                user = new User("", date.toString(), nombre, email, carrera, "");
            }

            autenticacion.registro(email, password, user, usuario->{
                if(usuario != null){
                    if(this.path !=null){
                        StorageFirebase.uploadFile(new String[]{DBROUTES.USERS_IMAGES, usuario.getId()}, path, (urlResult)-> {
                            if(urlResult != null){
                                gotoHome();
                            }
                        });
                    }else{
                        gotoHome();
                    }

                }else{

                }

            });

        }

    }

    private void gotoHome(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    private void signGoogle(View v){
        autenticacion.loginWithGoogle();
    }

    private void addphoto(View v){
        galeria.openGaleria();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        galeria.onActivityResult(requestCode, resultCode, data);

        autenticacion.onActivityResult(requestCode, resultCode, data, (user)->{
            if (user != null){
                gotoHome();
            }
            });
    }

    @Override
    public void onLoad(Bitmap bitmap, String path) {
        civ_registro_imagen.setImageBitmap(bitmap);
        this.path = path;
    }
}