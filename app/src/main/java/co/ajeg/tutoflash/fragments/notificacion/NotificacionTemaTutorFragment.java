package co.ajeg.tutoflash.fragments.notificacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseMateria;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseUser;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.materia.MateriaTema;
import co.ajeg.tutoflash.model.materia.MateriaTutor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificacionTemaTutorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionTemaTutorFragment extends Fragment {

    private NotificacionFragment notificacionFragment;

    private ImageView iv_notificacion_tema_tutor_image;
    private TextView tv_notificacion_tema_tutor_usuario;
    private TextView tv_notificacion_tema_tutor_tema;
    private TextView tv_notificacion_tema_tutor_descripcion;
    private TextView tv_notificacion_tema_tutor_precio;
    private RecyclerView rv_notificacion_tema_tutor_horarios;
    private Button btn_notificacion_tema_tutor_seleccionar;
    private Button btn_notificacion_tema_tutor_cancelar;

    private User currentTutor;
    private MateriaTutor materiaTutor;
    private AdapterList<String> adapterList;
    private List<String> horarios = new ArrayList();
    private DatabaseMateria databaseMateria;
    private List<MateriaTutor> tutoresAll;

    private String currentMateriaName;
    private MateriaTema currentMateriaTema;

    private String horarioString;

    public NotificacionTemaTutorFragment(NotificacionFragment notificacionFragment) {
        // Required empty public constructor
        this.notificacionFragment = notificacionFragment;
        this.horarios = new ArrayList<>();
        this.databaseMateria = DatabaseMateria.getInstance(notificacionFragment.mainActivity);
    }


    public static NotificacionTemaTutorFragment newInstance(NotificacionFragment notificacionFragment) {
        NotificacionTemaTutorFragment fragment = new NotificacionTemaTutorFragment(notificacionFragment);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificacion_tema_tutor, container, false);

        this.iv_notificacion_tema_tutor_image = view.findViewById(R.id.iv_notificacion_tema_tutor_image);
        this.tv_notificacion_tema_tutor_usuario = view.findViewById(R.id.tv_notificacion_tema_tutor_usuario);
        this.tv_notificacion_tema_tutor_tema = view.findViewById(R.id.tv_notificacion_tema_tutor_tema);
        this.tv_notificacion_tema_tutor_descripcion = view.findViewById(R.id.tv_notificacion_tema_tutor_descripcion);
        this.tv_notificacion_tema_tutor_precio = view.findViewById(R.id.tv_notificacion_tema_tutor_precio);
        this.rv_notificacion_tema_tutor_horarios = view.findViewById(R.id.rv_notificacion_tema_tutor_horarios);
        this.rv_notificacion_tema_tutor_horarios.setLayoutManager(new LinearLayoutManager(this.getContext()));

        if (this.materiaTutor != null) {
            this.tv_notificacion_tema_tutor_descripcion.setText(this.materiaTutor.getDescripcion());
            this.tv_notificacion_tema_tutor_precio.setText(this.materiaTutor.getPrecio());
            this.horarios = this.materiaTutor.getFechas();
        }


        this.adapterList = new AdapterList<>(this.rv_notificacion_tema_tutor_horarios, this.horarios, R.layout.list_item_materia_select_horario, new AdapterManagerList<String>() {

            List<CheckBox> checkBoxs = new ArrayList<>();

            @Override
            public void onChangeView(String elemnto, View view, int position) {

                CheckBox cb_list_item_materia_solicitar_horario_fecha = view.findViewById(R.id.cb_list_item_materia_solicitar_horario_fecha);
                cb_list_item_materia_solicitar_horario_fecha.setText(elemnto);

                if(checkBoxs.indexOf(cb_list_item_materia_solicitar_horario_fecha) == -1){
                    checkBoxs.add(cb_list_item_materia_solicitar_horario_fecha);
                }

                cb_list_item_materia_solicitar_horario_fecha.setOnClickListener((v)->{
                    horarioString = elemnto;
                    for (CheckBox c : checkBoxs){
                        if(c != cb_list_item_materia_solicitar_horario_fecha){
                            c.setChecked(false);
                        }
                    }
                });


            }
        });

        this.btn_notificacion_tema_tutor_seleccionar = view.findViewById(R.id.btn_notificacion_tema_tutor_seleccionar);
        this.btn_notificacion_tema_tutor_cancelar = view.findViewById(R.id.btn_notificacion_tema_tutor_cancelar);

        if (this.currentTutor != null) {
            this.tv_notificacion_tema_tutor_usuario.setText(this.currentTutor.getName());
            this.tv_notificacion_tema_tutor_tema.setText(this.currentTutor.getCarrera());

            DatabaseUser.getImageUrlProfile(this.notificacionFragment.mainActivity, currentTutor.getImage(), (urlImage) -> {
                if (urlImage != null) {
                    Glide.with(this.iv_notificacion_tema_tutor_image)
                            .load(urlImage)
                            .apply(RequestOptions.circleCropTransform())
                            .into(this.iv_notificacion_tema_tutor_image);
                }

            });
        }


        this.btn_notificacion_tema_tutor_seleccionar.setOnClickListener(this::onClickSelecionarTutor);
        this.btn_notificacion_tema_tutor_cancelar.setOnClickListener(this::onClickCancelar);

        return view;
    }

    public void onClickSelecionarTutor(View v) {

        if (this.currentMateriaTema != null && this.currentMateriaName != null && this.tutoresAll != null && this.materiaTutor != null) {



            this.databaseMateria.seleccionarTutor(
                    this.currentMateriaName,
                    this.currentMateriaTema,
                    this.materiaTutor,
                    this.tutoresAll,
                    (tutor) -> {
                        if (tutor != null) {
                            if(this.horarioString != null){
                                this.databaseMateria.addHorario(this.materiaTutor.getAutorId(), this.horarioString, "Tutoria");
                                this.databaseMateria.addHorario(this.materiaTutor.getTutorId(), this.horarioString,"Tutoria");
                            }
                            FragmentUtil.resetFragmentNav();
                            FragmentUtil.replaceFragment(R.id.fragment_container, this.notificacionFragment.mainActivity.chatFragment);
                        } else {

                        }
                    });
        }

    }

    public void onClickCancelar(View v) {
        FragmentUtil.goToBackFragment();
    }

    public void setCurrentTutor(User user) {
        this.currentTutor = user;
    }

    public void setCurrentMateriaTutor(MateriaTutor materiaTutor) {
        this.materiaTutor = materiaTutor;
    }

    public void setCurrentMateriaTema(MateriaTema materiaTema) {
        this.currentMateriaTema = materiaTema;
    }

    public void setCurrentMateriaName(String currentMateriaName) {
        this.currentMateriaName = currentMateriaName;
    }

    public void setCurrentTutoresAll(List<MateriaTutor> materiaTutorList) {
        this.tutoresAll = materiaTutorList;
    }


}