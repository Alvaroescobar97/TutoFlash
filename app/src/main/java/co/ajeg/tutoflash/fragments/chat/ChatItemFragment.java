package co.ajeg.tutoflash.fragments.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.Database;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseChat;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.chat.ChatMensaje;
import co.ajeg.tutoflash.model.chat.ChatPerson;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatItemFragment extends Fragment {

    List<ChatMensaje> chatMensajes;
    AdapterList<ChatMensaje> adapterList;
    MainActivity mainActivity;
    DatabaseChat databaseChat;
    ChatPerson chatPerson;

    public static ChatItemFragment newInstance() {
        ChatItemFragment fragment = new ChatItemFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.databaseChat = DatabaseChat.getInstance(this.getActivity());
        this.mainActivity = FragmentUtil.getActivity();

        View view = inflater.inflate(R.layout.fragment_chat_item, container, false);

        TextInputLayout til_chat_item_mensaje = view.findViewById(R.id.til_chat_item_mensaje);

        RecyclerView rv_chat_item_dialogos = view.findViewById(R.id.rv_chat_item_dialogos);
        rv_chat_item_dialogos.setLayoutManager(new LinearLayoutManager(this.getContext()));


        if (this.chatMensajes == null) {
            this.chatMensajes = new ArrayList<>();
        }

        adapterList = new AdapterList(rv_chat_item_dialogos, this.chatMensajes, R.layout.list_item_chat_dialogo, new AdapterManagerList<ChatMensaje>() {

            private TextView tv_item_chat_dialogo_mensaje;
            private TextView tv_item_chat_dialogo_fecha;


            @Override
            public void onCreateView(View v) {

                this.tv_item_chat_dialogo_mensaje = v.findViewById(R.id.tv_item_chat_dialogo_mensaje);
                this.tv_item_chat_dialogo_fecha = v.findViewById(R.id.tv_item_chat_dialogo_fecha);

            }

            @Override
            public void onChangeView(ChatMensaje mensaje, View view, int position) {

            }

        });


        EditText editText = til_chat_item_mensaje.getEditText();
        editText.setOnTouchListener((v, event) -> {
            boolean stateClick = FragmentUtil.onTouchEventIconDirectionUp(event, editText, FragmentUtil.DRAWABLE_RIGHT);

            if(stateClick){
                String mensajeString = editText.getText().toString();
                if(mensajeString.equals("") == false){
                    String id = UUID.randomUUID().toString();
                    String autorId = Autenticacion.getUser().getId();

                    String fecha = (new Date()).toString();

                    ChatMensaje mensaje = new ChatMensaje(id, autorId, mensajeString, fecha);

                    String chatId = this.chatPerson.getId();
                    this.databaseChat.sendMensaje(chatId, mensaje);
                    editText.setText("");
                }
            }

            return stateClick;
        });


        return view;

    }


    public void changeCurrentChat(ChatPerson chatPerson) {
        this.databaseChat = DatabaseChat.getInstance(this.getActivity());
        this.chatPerson = chatPerson;
        this.databaseChat.getChatAllMensaje(chatPerson.getId(), chatMensajeList -> {
            this.chatMensajes = chatMensajeList;
            /*
            chatMensajeList.add(new ChatMensaje("s", "A", "Menssaje", "10 sep"));
            chatMensajeList.add(new ChatMensaje("s", "A", "Menssaje", "10 sep"));
            chatMensajeList.add(new ChatMensaje("s", "A", "Menssaje", "10 sep"));
            chatMensajeList.add(new ChatMensaje("s", "A", "Menssaje", "10 sep"));
            chatMensajeList.add(new ChatMensaje("s", "A", "Menssaje", "10 sep"));

             */
            if (adapterList != null) {
                adapterList.onUpdateData(chatMensajeList);
            }
        });
    }
}