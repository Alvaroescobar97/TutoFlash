package co.ajeg.tutoflash.fragments.chat;

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
import co.ajeg.tutoflash.model.chat.ChatMensaje;
import co.ajeg.tutoflash.model.chat.ChatPerson;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatItemFragment extends Fragment {

    public static ChatItemFragment newInstance() {
        ChatItemFragment fragment = new ChatItemFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat_item, container, false);

        RecyclerView rv_chat_item_dialogos = view.findViewById(R.id.rv_chat_item_dialogos);

        List<ChatMensaje> mensajes = new ArrayList<>();

        AdapterList<ChatMensaje> adapterList = new AdapterList(rv_chat_item_dialogos, mensajes, R.layout.list_item_chat_dialogo, new AdapterManagerList<ChatMensaje>() {

            private TextView tv_item_chat_dialogo_mensaje;
            private TextView tv_item_chat_dialogo_fecha;


            @Override
            public void onCreateView(View v) {

                this.tv_item_chat_dialogo_mensaje = v.findViewById(R.id.tv_item_chat_dialogo_mensaje);
                this.tv_item_chat_dialogo_fecha = v.findViewById(R.id.tv_item_chat_dialogo_fecha);

            }

            @Override
            public void onChangeView(ChatMensaje mensaje, int position) {

            }
        });
        return view;

    }
}