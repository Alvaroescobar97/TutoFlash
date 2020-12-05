package co.ajeg.tutoflash.fragments.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseChat;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.chat.ChatPerson;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    MainActivity mainActivity;
    DatabaseChat databaseChat;

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
        this.mainActivity = FragmentUtil.getActivity();
        this.databaseChat = DatabaseChat.getInstance(this.getActivity());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView rv_chat_personas = view.findViewById(R.id.rv_chat_personas);
        rv_chat_personas.setLayoutManager(new LinearLayoutManager(this.getContext()));

        TextView tv_chat_no_chats= view.findViewById(R.id.tv_chat_no_chats);

        AdapterList<ChatPerson> adapterList = new AdapterList(rv_chat_personas, new ArrayList(), R.layout.list_item_chat_persona, new AdapterManagerList<ChatPerson>() {

            private ImageView civ_item_chat_persona_image;
            private TextView tv_item_chat_persona_name;
            private TextView tv_item_chat_persona_rol;
            private TextView tv_item_chat_persona_fecha;

            @Override
            public void onCreateView(View v) {

                this.civ_item_chat_persona_image = v.findViewById(R.id.iv_item_chat_persona_image);
                this.tv_item_chat_persona_name = v.findViewById(R.id.tv_item_chat_persona_name);
                this.tv_item_chat_persona_rol = v.findViewById(R.id.tv_item_chat_persona_rol);
                this.tv_item_chat_persona_fecha = v.findViewById(R.id.tv_item_chat_persona_fecha);

            }

            @Override
            public void onChangeView(ChatPerson elemnto, View view, int position) {

                view.setOnClickListener( v -> {
                    FragmentUtil.getActivity((mainActivity)->{
                        mainActivity.chatItemFragment.changeCurrentChat(elemnto);
                        FragmentUtil.replaceFragment(R.id.fragment_container, mainActivity.chatItemFragment);
                    });
                });
            }

        });



        this.databaseChat.getAllChatsUser((chatPersonList)->{
            /*
            chatPersonList.add(new ChatPerson("a", "a", "b", "ayer"));
            chatPersonList.add(new ChatPerson("a", "a", "b", "ayer"));
            chatPersonList.add(new ChatPerson("a", "a", "b", "ayer"));
            chatPersonList.add(new ChatPerson("a", "a", "b", "ayer"));
            chatPersonList.add(new ChatPerson("a", "a", "b", "ayer"));
            chatPersonList.add(new ChatPerson("a", "a", "b", "ayer"));
            chatPersonList.add(new ChatPerson("a", "a", "b", "ayer"));
            chatPersonList.add(new ChatPerson("a", "a", "b", "ayer"));

             */

            if(chatPersonList.size() == 0){
                tv_chat_no_chats.setVisibility(View.VISIBLE);
            }else{
                tv_chat_no_chats.setVisibility(View.GONE);
            }

            adapterList.onUpdateData(chatPersonList);
        });





        return view;
    }

}