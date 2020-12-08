package co.ajeg.tutoflash.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.UUID;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.LoginActivity;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.activities.PreLogin;
import co.ajeg.tutoflash.firebase.FirebaseMensajes;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.Database;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseUser;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.galeria.Galeria;
import co.ajeg.tutoflash.galeria.UtilDomi;
import co.ajeg.tutoflash.model.User;

import static android.app.Activity.RESULT_OK;
import static co.ajeg.tutoflash.galeria.Galeria.GALLERY_CALLBACK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment implements Galeria.OnCompleteListenerImage {

    private Button btn_perfil_cerrar_session,guardarBtn;
    private ImageView fotoPerfil;
    private EditText nombreET,correoET,passwordET, et_perfil_carrera;


    private User user;
    private MainActivity mainActivity;
    private String path;

    Autenticacion autenticacion;

    public PerfilFragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity = mainActivity;

        this.autenticacion = new Autenticacion(mainActivity);


    }

    public static PerfilFragment newInstance(MainActivity mainActivity) {
        PerfilFragment fragment = new PerfilFragment(mainActivity);
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        FragmentUtil.getActivity().headerFragment.changeTitleHeader( "Mi Perfil" );

        this.user = Autenticacion.getUser();
        this.mainActivity.galeria.getResultImage(this);

        fotoPerfil = view.findViewById(R.id.fotoPerfil);
        nombreET = view.findViewById( R.id.nombreET);
        correoET = view.findViewById( R.id.correoET );
        passwordET = view.findViewById( R.id.passwordET );
        et_perfil_carrera = view.findViewById( R.id.et_perfil_carrera );

        guardarBtn = view.findViewById( R.id.guardarBtn );
        btn_perfil_cerrar_session = view.findViewById(R.id.btn_perfil_cerrar_session);

        nombreET.setText( user.getName() );
        correoET.setText( user.getEmail() );
        et_perfil_carrera.setText( user.getCarrera() );

        if(user.getType() == Autenticacion.USER_GOOGLE){
            correoET.setVisibility(View.GONE);
            passwordET.setVisibility(View.GONE);
        }

        if(path == null){
            DatabaseUser.getImageUrlProfile(this.mainActivity, user.getImage(), (urlResult)->{
                if(urlResult != null){
                    Glide.with(this.fotoPerfil)
                            .load(urlResult)
                            .apply(RequestOptions.circleCropTransform())
                            .into(this.fotoPerfil);
                }

            });
        }


        btn_perfil_cerrar_session.setOnClickListener(this::cerrarSession);
        fotoPerfil.setOnClickListener( this::cargarFoto );
        guardarBtn.setOnClickListener( this::guardar );



        return view;
    }

    private void cargarFoto(View view) {
        this.path = "";
        this.mainActivity.galeria.openGaleria();
    }

    private void guardar(View view) {

        String name = nombreET.getText().toString();
        String email = correoET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String carrera = et_perfil_carrera.getText().toString().trim();

        User newUser = this.user;

        boolean condicionName = !name.equals("");
        boolean condicionEmail = !email.equals("") && user.getEmail().equals(email) == false;
        boolean condicionPass = !password.equals("");

        if(this.path != null || condicionName || condicionEmail){
            newUser.setName(name);

            if(carrera.equals("") == false){
                newUser.setCarrera(carrera);
            }
            
            this.autenticacion.updateEmail(email, (updateEmail)->{
                if(updateEmail != null){
                    newUser.setEmail(email);
                        this.autenticacion.updateUserInformation(newUser, this.path, (usuarioResult)->{
                            this.path = null;
                            if(usuarioResult != null){
                                FragmentUtil.goToBackFragment();
                            } else {
                                FragmentUtil.goToBackFragment();
                            }
                        });
                }else{
                    FragmentUtil.goToBackFragment();
                }
            });
        }

        if(condicionPass){
            this.autenticacion.updatePass(password,(passwordResult)->{});
        }


    }

    public void cerrarSession(View v){
        FirebaseMensajes.desuscribirse((task -> {
            Autenticacion.logout();
            FragmentUtil.startActivity(PreLogin.class);
        }));

    }

    @Override
    public void onLoad(Bitmap bitmap, String path) {
        this.path = path;
        this.fotoPerfil.setImageBitmap(bitmap);


    }
}