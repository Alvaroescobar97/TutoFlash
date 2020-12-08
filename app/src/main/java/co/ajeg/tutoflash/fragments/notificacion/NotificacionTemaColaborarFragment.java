package co.ajeg.tutoflash.fragments.notificacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseMateria;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseUser;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.materia.MateriaTema;
import co.ajeg.tutoflash.model.notificacion.Notificacion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificacionTemaColaborarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionTemaColaborarFragment extends Fragment {

    private NotificacionFragment notificacionFragment;
    private ImageView iv_notificacion_tema_colaborar_image;
    private TextView tv_notificacion_tema_colaborar_tema;
    private  TextView tv_notificacion_tema_colaborar_usuario;
    private TextView tv_notificacion_tema_colaborar_descripcion;
    private TextView tv_notificacion_tema_colaborar_tiempo;
    private Button btn_notificacion_tema_colaborar_desertar;
    private Notificacion notificacion;
    private DatabaseMateria databaseMateria;
    private MateriaTema materiaTema;

    public NotificacionTemaColaborarFragment(NotificacionFragment notificacionFragment) {
        // Required empty public constructor
        this.notificacionFragment = notificacionFragment;
        this.databaseMateria = DatabaseMateria.getInstance(notificacionFragment.mainActivity);
    }

    public static NotificacionTemaColaborarFragment newInstance(NotificacionFragment notificacionFragment) {
        NotificacionTemaColaborarFragment fragment = new NotificacionTemaColaborarFragment(notificacionFragment);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_notificacion_tema_colaborar, container, false);

        MainActivity mainActivity = this.notificacionFragment.mainActivity;

        this.iv_notificacion_tema_colaborar_image = view.findViewById(R.id.iv_notificacion_tema_colaborar_image);
        this.tv_notificacion_tema_colaborar_tema = view.findViewById(R.id.tv_notificacion_tema_colaborar_tema);
        this.tv_notificacion_tema_colaborar_usuario = view.findViewById(R.id.tv_notificacion_tema_colaborar_usuario);
        this.tv_notificacion_tema_colaborar_descripcion = view.findViewById(R.id.tv_notificacion_tema_colaborar_descripcion);
        this.tv_notificacion_tema_colaborar_tiempo = view.findViewById(R.id.tv_notificacion_tema_colaborar_tiempo);
        this.btn_notificacion_tema_colaborar_desertar = view.findViewById(R.id.btn_notificacion_tema_colaborar_desertar);


        if (this.notificacion != null) {
            String nameMateria = this.notificacion.getDirDatabase().get(1);
            this.databaseMateria.getSolicitudMateriaTema(nameMateria, notificacion.getId(),
                    (tema) -> {
                        if (tema != null) {
                            this.materiaTema = tema;
                            User user = Autenticacion.getUser();
                            if(user != null && tema.getAutorId().equals(user.getId())){
                                DatabaseUser.getImageUrlProfile(mainActivity, user.getImage(), (urlImage)->{
                                    if(urlImage != null){
                                        Glide.with(this.iv_notificacion_tema_colaborar_image)
                                                .load(urlImage)
                                                .apply(RequestOptions.circleCropTransform())
                                                .into(this.iv_notificacion_tema_colaborar_image);
                                    }
                                });
                                this.tv_notificacion_tema_colaborar_usuario.setText(user.getName());
                            }else {
                                DatabaseUser.getRefUserId(tema.getAutorId(), (usuarioAutor)->{
                                    DatabaseUser.getImageUrlProfile(mainActivity, usuarioAutor.getImage(), (urlImage)->{
                                        if(urlImage != null){
                                            Glide.with(this.iv_notificacion_tema_colaborar_image)
                                                    .load(urlImage)
                                                    .apply(RequestOptions.circleCropTransform())
                                                    .into(this.iv_notificacion_tema_colaborar_image);
                                        }
                                    });
                                    this.tv_notificacion_tema_colaborar_usuario.setText(usuarioAutor.getName());
                                });
                            }

                            this.tv_notificacion_tema_colaborar_tema.setText(tema.getTitle());

                            this.tv_notificacion_tema_colaborar_descripcion.setText(tema.getDescripcion());
                            this.tv_notificacion_tema_colaborar_tiempo.setText(tema.getTiempo());

                            this.btn_notificacion_tema_colaborar_desertar.setOnClickListener(this::onDesertarDelTema);

                        }
                    }

                    , null);
        }

        return view;
    }

    public void onDesertarDelTema(View v){
        User user =Autenticacion.getUser();
        if(this.materiaTema != null && this.notificacion != null && user != null){
            String nameMateria = this.notificacion.getDirDatabase().get(1);
            this.databaseMateria.deteletMateriaTutor(nameMateria, this.materiaTema, user.getId(), (materiaTemaResult)->{
                if(materiaTemaResult != null){
                    FragmentUtil.goToBackFragment();
                }
            });
        }

    }

    public void setCurrentNotificacion(Notificacion notificacion){
        this.notificacion = notificacion;
    }
}