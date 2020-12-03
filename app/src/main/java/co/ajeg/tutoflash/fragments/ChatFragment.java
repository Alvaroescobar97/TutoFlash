package co.ajeg.tutoflash.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.galeria.Galeria;
import co.ajeg.tutoflash.model.chat.ChatPerson;

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

            @Override
            public void onCreateView(View v) {

            }

            @Override
            public void onChangeView(ChatPerson elemnto, int position) {

            }
        });

        return view;
    }

}