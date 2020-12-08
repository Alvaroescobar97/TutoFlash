package co.ajeg.tutoflash.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseUser;
import co.ajeg.tutoflash.fragments.notificacion.NotificacionFragment;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.notificacion.Notificacion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalificacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalificacionFragment extends Fragment {

    private ImageView iv_calificacion_image;
    private TextView tv_calificacion_usuario;
    private TextView tv_calificacion_rol;
    private RatingBar rb_calificacion_calificacion;
    private Button btn_calificacion_calificar;
    private Button btn_calificacion_cancelar;

    private Notificacion notificacion;
    private User currentUser;

    private NotificacionFragment notificacionFragment;

    public CalificacionFragment(NotificacionFragment notificacionFragment) {
        // Required empty public constructor
        this.notificacionFragment = notificacionFragment;
    }

    public static CalificacionFragment newInstance(NotificacionFragment notificacionFragment) {
        CalificacionFragment fragment = new CalificacionFragment(notificacionFragment);
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calificacion, container, false);

        this.iv_calificacion_image = view.findViewById(R.id.iv_calificacion_image);
        this.tv_calificacion_usuario = view.findViewById(R.id.tv_calificacion_usuario);
        this.tv_calificacion_rol = view.findViewById(R.id.tv_calificacion_rol);
        this.rb_calificacion_calificacion = view.findViewById(R.id.rb_calificacion_calificacion);
        this.btn_calificacion_calificar = view.findViewById(R.id.btn_calificacion_calificar);
        this.btn_calificacion_cancelar = view.findViewById(R.id.btn_calificacion_cancelar);

        if(this.notificacion != null){
            String userId = this.notificacion.getDirDatabase().get(1);
            DatabaseUser.getRefUserId(userId, (userResult)->{
                this.currentUser = userResult;

                this.tv_calificacion_usuario.setText(userResult.getName());
                this.tv_calificacion_rol.setText(userResult.getCarrera());


                DatabaseUser.getImageUrlProfile(this, userResult.getImage(), (imageResult)->{
                    if(imageResult != null){
                        Glide.with(this.iv_calificacion_image)
                                .load(imageResult)
                                .apply(RequestOptions.circleCropTransform())
                                .into(this.iv_calificacion_image);
                    }
                });
            });


        }

        this.btn_calificacion_calificar.setOnClickListener(this::onClickCalificar);
        this.btn_calificacion_cancelar.setOnClickListener(this::onClickCancelar);

        return view;
    }

    public void onClickCalificar(View v){

        float numberCalificacion = rb_calificacion_calificacion.getRating();



        if(this.currentUser != null && this.notificacion != null){
            DatabaseUser.calificarUser(this, this.notificacion, this.currentUser, numberCalificacion, (userResult)->{
                if(userResult != null){
                    this.currentUser = null;
                    FragmentUtil.goToBackFragment();
                }else{

                }
            });
        }
    }

    public void onClickCancelar(View v){
        this.currentUser = null;
        FragmentUtil.goToBackFragment();
    }



    public void setCurrentNotificacion(Notificacion notificacion){
        this.notificacion = notificacion;
    }
}