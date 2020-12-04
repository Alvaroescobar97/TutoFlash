package co.ajeg.tutoflash.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.galeria.Galeria;
import co.ajeg.tutoflash.model.chat.ChatPerson;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }


    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
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
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        List<ChatPerson> chatsPersonas = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.rv_chat_personas);

        AdapterList<ChatPerson> adapterList = new AdapterList(recyclerView, chatsPersonas, R.layout.list_item_chat_persona, new AdapterManagerList<ChatPerson>() {

            /*
            private CircleImageView civ_item_notificaciones_notificacion_image;
            private TextView tv_item_notificaciones_notificacion_name;
            private TextView tv_item_notificaciones_notificacion_descripcion;
            private TextView tv_item_notificaciones_notificacion_fecha;


            private CircleImageView civ_item_materia_tutor_image;
            private TextView tv_item_materia_tutor_name;
            private TextView tv_item_materia_tutor_descripcion;
            private TextView tv_item_materia_tutor_calificacion;
            private RatingBar rb_item_materia_tutor_calificacion;
            private TextView tv_item_materia_tutor_precio;


            private CircleImageView civ_item_home_materia_image;
            private TextView tv_item_home_materia_name;
            private TextView tv_item_home_materia_fecha;


            private CircleImageView civ_item_home_tema_image;
            private TextView tv_item_home_tema_name;
            private TextView tv_item_home_tema_rol;
            private TextView tv_item_home_tema_fecha;


            private TextView tv_item_chat_dialogo_mensaje;
            private TextView tv_item_chat_dialogo_fecha;

             */


            private CircleImageView civ_item_chat_persona_image;
            private TextView tv_item_chat_persona_name;
            private TextView tv_item_chat_persona_rol;
            private TextView tv_item_chat_persona_fecha;





            @Override
            public void onCreateView(View v) {

                /*
                this.civ_item_notificaciones_notificacion_image = v.findViewById(R.id.civ_item_notificaciones_notificacion_image);
                this.tv_item_notificaciones_notificacion_name = v.findViewById(R.id.tv_item_notificaciones_notificacion_name);
                this.tv_item_notificaciones_notificacion_descripcion = v.findViewById(R.id.tv_item_notificaciones_notificacion_descripcion);
                this.tv_item_notificaciones_notificacion_fecha = v.findViewById(R.id.tv_item_notificaciones_notificacion_fecha);


                this.civ_item_materia_tutor_image = v.findViewById(R.id.civ_item_materia_tutor_image);
                this.tv_item_materia_tutor_name = v.findViewById(R.id.tv_item_materia_tutor_name);
                this.tv_item_materia_tutor_descripcion = v.findViewById(R.id.tv_item_materia_tutor_descripcion);
                this.tv_item_materia_tutor_calificacion = v.findViewById(R.id.tv_item_materia_tutor_calificacion);
                this.rb_item_materia_tutor_calificacion = v.findViewById(R.id.rb_item_materia_tutor_calificacion);
                this.tv_item_materia_tutor_precio = v.findViewById(R.id.tv_item_materia_tutor_precio);


                this.civ_item_home_materia_image = v.findViewById(R.id.civ_item_home_materia_image);
                this.tv_item_home_materia_name = v.findViewById(R.id.tv_item_home_materia_name);
                this.tv_item_home_materia_fecha = v.findViewById(R.id.tv_item_home_materia_fecha);


                this.civ_item_home_tema_image = v.findViewById(R.id.civ_item_home_tema_image);
                this.tv_item_home_tema_name = v.findViewById(R.id.tv_item_home_tema_name);
                this.tv_item_home_tema_rol = v.findViewById(R.id.tv_item_home_tema_rol);
                this.tv_item_home_tema_fecha = v.findViewById(R.id.tv_item_home_tema_fecha);


                this.tv_item_chat_dialogo_mensaje = v.findViewById(R.id.tv_item_chat_dialogo_mensaje);
                this.tv_item_chat_dialogo_fecha = v.findViewById(R.id.tv_item_chat_dialogo_fecha);

                 */

                this.civ_item_chat_persona_image = v.findViewById(R.id.civ_item_chat_persona_image);
                this.tv_item_chat_persona_name = v.findViewById(R.id.tv_item_chat_persona_name);
                this.tv_item_chat_persona_rol = v.findViewById(R.id.tv_item_chat_persona_rol);
                this.tv_item_chat_persona_fecha = v.findViewById(R.id.tv_item_chat_persona_fecha);

            }

            @Override
            public void onChangeView(ChatPerson elemnto, int position) {

            }
        });

        return view;
    }

}