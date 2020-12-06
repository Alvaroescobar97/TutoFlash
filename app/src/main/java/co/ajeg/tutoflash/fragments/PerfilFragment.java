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
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.galeria.UtilDomi;
import co.ajeg.tutoflash.model.User;

import static android.app.Activity.RESULT_OK;
import static co.ajeg.tutoflash.galeria.Galeria.GALLERY_CALLBACK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    private Button btn_perfil_cerrar_session,guardarBtn;
    private ImageView fotoPerfil;
    private EditText nombreET,correoET,passwordET;

    private User user;
    private String path;

    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private FirebaseAuth auth;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        FragmentUtil.getActivity().headerFragment.changeTitleHeader( "Perfil" );

        user = Autenticacion.getUser();
        Log.e( ">>>>",user.getName() );
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        fotoPerfil = view.findViewById(R.id.fotoPerfil);
        nombreET = view.findViewById( R.id.nombreET);
        correoET = view.findViewById( R.id.correoET );
        passwordET = view.findViewById( R.id.passwordET );

        guardarBtn = view.findViewById( R.id.guardarBtn );
        btn_perfil_cerrar_session = view.findViewById(R.id.btn_perfil_cerrar_session);

        nombreET.setText( user.getName() );
        correoET.setText( user.getEmail() );
        if(user.getImage()!= null){
            loadPhoto();
        }

        btn_perfil_cerrar_session.setOnClickListener(this::cerrarSession);
        fotoPerfil.setOnClickListener( this::cargarFoto );
        guardarBtn.setOnClickListener( this::guardar );
        return view;
    }

    private void cargarFoto(View view) {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,GALLERY_CALLBACK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CALLBACK && resultCode == RESULT_OK){
            Uri photoUri = data.getData();
            path = UtilDomi.getPath(getContext(),photoUri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            fotoPerfil.setImageBitmap(bitmap);
        }
    }

    private void guardar(View view) {
        user.setEmail( correoET.getText().toString() );
        user.setName( nombreET.getText().toString() );

        if(!passwordET.getText().toString().trim().isEmpty()){
            auth.getCurrentUser().updatePassword(passwordET.getText().toString().trim());
        }
        if(path == user.getImage() || path == null) return;
        try {
            FileInputStream fis = new FileInputStream(new File(path));
            Log.e(">>>",path);
            storage.getReference().child("aplicacion").child("profiles").child( user.getId() ).putStream(fis).addOnCompleteListener(
                    task -> {
                        if(task.isSuccessful()){
                            loadPhoto();
                            user.setImage( path );
                            db.collection("users").document(user.getId()).set(user);
                            getFragmentManager().beginTransaction().remove(this).commit();
                        }
                    }
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadPhoto(){
        db.collection("users").document(user.getId()).get().addOnCompleteListener(
                task -> {
                    user = task.getResult().toObject(User.class);
                    if(user.getImage() != null){
                        storage.getReference().child("aplicacion").child("profiles").child( user.getId() ).getDownloadUrl().addOnCompleteListener(
                                urlTask->{
                                    String url = urlTask.getResult().toString();
                                    Glide.with(fotoPerfil).load(url).into(fotoPerfil);
                                }
                        );
                    }
                }
        );
    }

    public void cerrarSession(View v){
        Autenticacion.logout();
        FragmentUtil.startActivity(PreLogin.class);
    }
}