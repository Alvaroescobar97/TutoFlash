package co.ajeg.tutoflash.fragments.notificacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseMateria;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseUser;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.materia.MateriaTutor;
import co.ajeg.tutoflash.model.notificacion.Notificacion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificacionTemaCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionTemaCreateFragment extends Fragment {

    private MainActivity mainActivity;
    private ImageView iv_notificacion_tema_create_image;
    private TextView tv_notificacion_tema_create_tema;
    private TextView tv_notificacion_tema_create_usuario;
    private TextView tv_notificacion_tema_create_descripcion;
    private TextView tv_notificacion_tema_create_tiempo;
    private Button btn_notificacion_tema_create_eliminar;
    private RecyclerView rv_notificacion_tema_create_tutores;
    private AdapterList<MateriaTutor> adapterList;
    private ArrayList<MateriaTutor> materiaTutorsList;
    private Notificacion notificacion;

    private DatabaseMateria databaseMateria;

    public NotificacionTemaCreateFragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity = mainActivity;
        this.materiaTutorsList = new ArrayList<>();
        this.databaseMateria = DatabaseMateria.getInstance(mainActivity);
    }

    public static NotificacionTemaCreateFragment newInstance(MainActivity mainActivity) {
        NotificacionTemaCreateFragment fragment = new NotificacionTemaCreateFragment(mainActivity);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificacion_tema_create, container, false);

        this.iv_notificacion_tema_create_image = view.findViewById(R.id.iv_notificacion_tema_create_image);
        this.tv_notificacion_tema_create_tema = view.findViewById(R.id.tv_notificacion_tema_create_tema);
        this.tv_notificacion_tema_create_usuario = view.findViewById(R.id.tv_notificacion_tema_create_usuario);
        this.tv_notificacion_tema_create_descripcion = view.findViewById(R.id.tv_notificacion_tema_create_descripcion);
        this.tv_notificacion_tema_create_tiempo = view.findViewById(R.id.tv_notificacion_tema_create_tiempo);
        this.btn_notificacion_tema_create_eliminar = view.findViewById(R.id.btn_notificacion_tema_create_eliminar);
        this.rv_notificacion_tema_create_tutores = view.findViewById(R.id.rv_notificacion_tema_create_tutores);
        this.rv_notificacion_tema_create_tutores.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.adapterList = new AdapterList<>(this.rv_notificacion_tema_create_tutores, this.materiaTutorsList, R.layout.list_item_materia_tutor, new AdapterManagerList<MateriaTutor>() {

            private ImageView iv_item_materia_tutor_image;
            private TextView tv_item_materia_tutor_name;
            private TextView tv_item_materia_tutor_descripcion;
            private TextView tv_item_materia_tutor_precio;
            private TextView tv_item_materia_tutor_calificacion;
            private RatingBar rb_item_materia_tutor_calificacion;

            @Override
            public void onCreateView(View v) {
                this.iv_item_materia_tutor_image = v.findViewById(R.id.iv_item_materia_tutor_image);
                this.tv_item_materia_tutor_name = v.findViewById(R.id.tv_item_materia_tutor_name);
                this.tv_item_materia_tutor_descripcion = v.findViewById(R.id.tv_item_materia_tutor_descripcion);
                this.tv_item_materia_tutor_precio = v.findViewById(R.id.tv_item_materia_tutor_precio);
                this.tv_item_materia_tutor_calificacion = v.findViewById(R.id.tv_item_materia_tutor_calificacion);
                this.rb_item_materia_tutor_calificacion = v.findViewById(R.id.rb_item_materia_tutor_calificacion);
            }

            @Override
            public void onChangeView(MateriaTutor tutor, View view, int position) {

                DatabaseUser.getRefUserId(tutor.getTutorId(), (userTutor)->{
                    if(userTutor != null){
                        DatabaseUser.getImageUrlProfile(mainActivity, userTutor.getImage(), (urlImage)->{
                            Glide.with(this.iv_item_materia_tutor_image)
                                    .load(urlImage)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(this.iv_item_materia_tutor_image);
                        });
                        this.tv_item_materia_tutor_name.setText(userTutor.getName());
                        /*
                        this.tv_item_materia_tutor_calificacion;
                        this.rb_item_materia_tutor_calificacion;
                         */
                    }
                });


                this.tv_item_materia_tutor_descripcion.setText(tutor.getDescripcion());
                this.tv_item_materia_tutor_precio.setText("$ " + tutor.getPrecio());

            }


        });

        if (this.notificacion != null) {
            String nameMateria = this.notificacion.getDirDatabase().get(1);
            this.databaseMateria.getSolicitudMateriaTema(nameMateria, notificacion.getRefId(),
                    (tema) -> {
                        if (tema != null) {
                            User user = Autenticacion.getUser();
                            if(user != null && tema.getAutorId().equals(user.getId())){
                                DatabaseUser.getImageUrlProfile(mainActivity, user.getImage(), (urlImage)->{
                                    Glide.with(this.iv_notificacion_tema_create_image)
                                            .load(urlImage)
                                            .apply(RequestOptions.circleCropTransform())
                                            .into(this.iv_notificacion_tema_create_image);
                                });
                                this.tv_notificacion_tema_create_usuario.setText(user.getName());
                            }else {
                                DatabaseUser.getRefUserId(tema.getId(), (usuarioAutor)->{
                                    DatabaseUser.getImageUrlProfile(mainActivity, usuarioAutor.getImage(), (urlImage)->{
                                        Glide.with(this.iv_notificacion_tema_create_image)
                                                .load(urlImage)
                                                .apply(RequestOptions.circleCropTransform())
                                                .into(this.iv_notificacion_tema_create_image);
                                    });
                                    this.tv_notificacion_tema_create_usuario.setText(usuarioAutor.getName());
                                });
                            }

                            this.tv_notificacion_tema_create_tema.setText(tema.getTitle());

                            this.tv_notificacion_tema_create_descripcion.setText(tema.getDescripcion());
                            this.tv_notificacion_tema_create_tiempo.setText(tema.getTiempo());

                            this.tv_notificacion_tema_create_tema.setText(tema.getTitle());

                            this.btn_notificacion_tema_create_eliminar.setOnClickListener(this::onDeletePublicacionTema);

                        }
                    }

                    , (tutores) -> {
                        this.adapterList.onUpdateData(tutores);

                    });
        }


        return view;
    }

    private void onDeletePublicacionTema(View v){

    }

    public void setCurrentNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }
}