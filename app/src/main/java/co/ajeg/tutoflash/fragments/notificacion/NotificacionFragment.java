package co.ajeg.tutoflash.fragments.notificacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.model.chat.ChatPerson;
import co.ajeg.tutoflash.model.notificacion.Notificacion;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionFragment extends Fragment {

    public NotificacionFragment() {
        // Required empty public constructor
    }


    public static NotificacionFragment newInstance() {
        NotificacionFragment fragment = new NotificacionFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificacion, container, false);

        TextView tv_header_title = this.getActivity().findViewById(R.id.tv_header_title);
        tv_header_title.setText("Notificaciones");

        RecyclerView rv_notificaciones_lista = view.findViewById(R.id.rv_notificaciones_lista);

        List<Notificacion> chatsPersonas = new ArrayList<>();

        AdapterList<Notificacion> adapterList = new AdapterList(rv_notificaciones_lista, chatsPersonas, R.layout.list_item_notificaciones_notificacion, new AdapterManagerList<Notificacion>() {


            private CircleImageView civ_item_notificaciones_notificacion_image;
            private TextView tv_item_notificaciones_notificacion_name;
            private TextView tv_item_notificaciones_notificacion_descripcion;
            private TextView tv_item_notificaciones_notificacion_fecha;


            @Override
            public void onCreateView(View v) {


                this.civ_item_notificaciones_notificacion_image = v.findViewById(R.id.civ_item_notificaciones_notificacion_image);
                this.tv_item_notificaciones_notificacion_name = v.findViewById(R.id.tv_item_notificaciones_notificacion_name);
                this.tv_item_notificaciones_notificacion_descripcion = v.findViewById(R.id.tv_item_notificaciones_notificacion_descripcion);
                this.tv_item_notificaciones_notificacion_fecha = v.findViewById(R.id.tv_item_notificaciones_notificacion_fecha);


            }

            @Override
            public void onChangeView(Notificacion notificacion, View view, int position) {

            }
        });

        return view;
    }
}