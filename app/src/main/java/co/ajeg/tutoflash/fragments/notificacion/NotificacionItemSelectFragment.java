package co.ajeg.tutoflash.fragments.notificacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.model.chat.ChatPerson;
import co.ajeg.tutoflash.model.materia.MateriaTutor;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificacionItemSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionItemSelectFragment extends Fragment {

    public NotificacionItemSelectFragment() {
        // Required empty public constructor
    }

    public static NotificacionItemSelectFragment newInstance() {
        NotificacionItemSelectFragment fragment = new NotificacionItemSelectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificacion_item_select, container, false);

        RecyclerView rv_chat_personas = view.findViewById(R.id.rv_notificaciones_lista);

        List<MateriaTutor> materiaTutors = new ArrayList<>();


        AdapterList<MateriaTutor> adapterList = new AdapterList(rv_chat_personas, materiaTutors, R.layout.list_item_materia_tutor, new AdapterManagerList<MateriaTutor>() {


            private CircleImageView civ_item_materia_tutor_image;
            private TextView tv_item_materia_tutor_name;
            private TextView tv_item_materia_tutor_descripcion;
            private TextView tv_item_materia_tutor_calificacion;
            private RatingBar rb_item_materia_tutor_calificacion;
            private TextView tv_item_materia_tutor_precio;


            @Override
            public void onCreateView(View v) {

                this.civ_item_materia_tutor_image = v.findViewById(R.id.civ_item_materia_tutor_image);
                this.tv_item_materia_tutor_name = v.findViewById(R.id.tv_item_materia_tutor_name);
                this.tv_item_materia_tutor_descripcion = v.findViewById(R.id.tv_item_materia_tutor_descripcion);
                this.tv_item_materia_tutor_calificacion = v.findViewById(R.id.tv_item_materia_tutor_calificacion);
                this.rb_item_materia_tutor_calificacion = v.findViewById(R.id.rb_item_materia_tutor_calificacion);
                this.tv_item_materia_tutor_precio = v.findViewById(R.id.tv_item_materia_tutor_precio);


            }

            @Override
            public void onChangeView(MateriaTutor tutor, View view, int position) {

            }
        });


        return view;
    }
}