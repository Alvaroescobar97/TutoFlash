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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseChat;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseUser;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.chat.ChatPerson;
import de.hdodenhof.circleimageview.CircleImageView;


public class ChatFragment extends Fragment {

    private MainActivity mainActivity;
    private DatabaseChat databaseChat;
    private List<ChatPerson> chatPersonList;
    private TextView tv_chat_no_chats;
    private AdapterList<ChatPerson> adapterList;

    public ChatFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.databaseChat = DatabaseChat.getInstance(this.mainActivity);
        this.chatPersonList = new ArrayList<>();

        this.databaseChat.getAllChatsUser((chatPersonList)->{
            this.chatPersonList = chatPersonList;
            this.updateListInformation();
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        this.mainActivity.headerFragment.changeTitleHeader("Chats");

        RecyclerView rv_chat_personas = view.findViewById(R.id.rv_chat_personas);
        rv_chat_personas.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.tv_chat_no_chats = view.findViewById(R.id.tv_chat_no_chats);

        if(this.chatPersonList.size() == 0){
            this.tv_chat_no_chats.setVisibility(View.VISIBLE);
        }else{
            this.tv_chat_no_chats.setVisibility(View.GONE);
        }

        this.adapterList = new AdapterList(rv_chat_personas, this.chatPersonList, R.layout.list_item_chat_persona, new AdapterManagerList<ChatPerson>() {

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


                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                Date date = new Date(elemnto.getDateLast());
                String strDate = dateFormat.format(date).toString();


                this.tv_item_chat_persona_fecha.setText(strDate);
                User user = Autenticacion.getUser();
                if(user != null){
                    String currentId = null;
                    if(elemnto.getSujectAId().equals(user.getId()) == false){
                        currentId = elemnto.getSujectAId();
                    } else if(elemnto.getSujectBId().equals(user.getId()) == false){
                        currentId = elemnto.getSujectBId();
                    }

                    if(currentId !=null){
                        DatabaseUser.getRefUserId(mainActivity, currentId, (userResult)->{


                            this.tv_item_chat_persona_name.setText(userResult.getName());
                            this.tv_item_chat_persona_rol.setText(userResult.getCarrera());


                            DatabaseUser.getImageUrlProfile(mainActivity, userResult.getImage(), (urlImageResult)->{
                                if(urlImageResult != null){
                                    Glide.with(this.civ_item_chat_persona_image)
                                            .load(urlImageResult)
                                            .apply(RequestOptions.circleCropTransform())
                                            .into(this.civ_item_chat_persona_image);
                                }

                            });

                        });
                    }


                }


                view.setOnClickListener( v -> {
                    FragmentUtil.getActivity((mainActivity)->{
                        mainActivity.chatItemFragment.changeCurrentChat(elemnto);
                        FragmentUtil.replaceFragment(R.id.fragment_container, mainActivity.chatItemFragment);
                    });
                });
            }

        });

        return view;
    }

    private void updateListInformation(){
        if(this.tv_chat_no_chats != null && this.adapterList != null){
            if(this.chatPersonList.size() == 0){
                this.tv_chat_no_chats.setVisibility(View.VISIBLE);
            }else{
                this.tv_chat_no_chats.setVisibility(View.GONE);
            }
            this.adapterList.onUpdateData(chatPersonList);
        }
    }

}