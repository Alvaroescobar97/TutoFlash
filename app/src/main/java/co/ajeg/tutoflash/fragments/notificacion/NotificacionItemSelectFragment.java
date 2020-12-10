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


            @Override
            public void onChangeView(MateriaTutor tutor, View view, int position) {

                CircleImageView civ_item_materia_tutor_image = view.findViewById(R.id.iv_item_materia_tutor_image);
                TextView tv_item_materia_tutor_name = view.findViewById(R.id.tv_item_materia_tutor_name);
                TextView tv_item_materia_tutor_descripcion = view.findViewById(R.id.tv_item_materia_tutor_descripcion);
                TextView tv_item_materia_tutor_calificacion = view.findViewById(R.id.tv_item_materia_tutor_calificacion);
                RatingBar rb_item_materia_tutor_calificacion = view.findViewById(R.id.rb_item_materia_tutor_calificacion);
                TextView tv_item_materia_tutor_precio = view.findViewById(R.id.tv_item_materia_tutor_precio);

            }
        });


        return view;
    }
}