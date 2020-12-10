package co.ajeg.tutoflash.fragments.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    public MainActivity mainActivity;

    private DatabaseChat databaseChat;
    private List<ChatPerson> chatPersonList;
    private TextView tv_chat_no_chats;
    private RecyclerView rv_chat_personas;
    private AdapterList<ChatPerson> adapterList;

    public ChatItemFragment chatItemFragment;

    public ChatFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.databaseChat = DatabaseChat.getInstance(this.mainActivity);
        this.chatPersonList = new ArrayList<>();

        chatItemFragment = ChatItemFragment.newInstance(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        this.mainActivity.headerFragment.changeTitleHeader("Chats");

        this.rv_chat_personas = view.findViewById(R.id.rv_chat_personas);
        this.rv_chat_personas.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.tv_chat_no_chats = view.findViewById(R.id.tv_chat_no_chats);

        if(this.chatPersonList.size() == 0){
            this.tv_chat_no_chats.setVisibility(View.VISIBLE);
        }else{
            this.tv_chat_no_chats.setVisibility(View.GONE);
        }

        this.adapterList = new AdapterList(this.rv_chat_personas, this.chatPersonList, R.layout.list_item_chat_persona, new AdapterManagerList<ChatPerson>() {




            @Override
            public void onCreateView(View v) {




            }

            @Override
            public void onChangeView(ChatPerson elemnto, View view, int position) {

                TextView tv_item_chat_persona_name;
                TextView tv_item_chat_persona_rol;
                TextView tv_item_chat_persona_fecha;

                tv_item_chat_persona_name = view.findViewById(R.id.tv_item_chat_persona_name);
                tv_item_chat_persona_rol = view.findViewById(R.id.tv_item_chat_persona_rol);
                tv_item_chat_persona_fecha = view.findViewById(R.id.tv_item_chat_persona_fecha);

                ImageView civ_item_chat_persona_image;
                ProgressBar pb_item_chat_persona_image;
                civ_item_chat_persona_image = view.findViewById(R.id.iv_item_chat_persona_image);
                pb_item_chat_persona_image = view.findViewById(R.id.pb_item_chat_persona_image);

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                Date date = new Date(elemnto.getDateLast());
                String strDate = dateFormat.format(date).toString();


                tv_item_chat_persona_fecha.setText(strDate);
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

                            tv_item_chat_persona_name.setText(userResult.getName());
                            tv_item_chat_persona_rol.setText(userResult.getCarrera());

                            DatabaseUser.getImageUrlProfile(mainActivity, userResult.getImage(), (urlImageResult)->{
                                if(urlImageResult != null){
                                    Glide.with(civ_item_chat_persona_image)
                                            .load(urlImageResult)
                                            .apply(RequestOptions.circleCropTransform())
                                            .into(civ_item_chat_persona_image);
                                }
                                pb_item_chat_persona_image.setVisibility(View.GONE);
                            });

                            view.setOnClickListener( v -> {
                                FragmentUtil.getActivity((mainActivity)->{
                                    chatItemFragment.changeCurrentChat(elemnto);
                                    chatItemFragment.setCurrentUsuario(userResult);
                                    FragmentUtil.replaceFragment(R.id.fragment_container, chatItemFragment);
                                });
                            });

                        });
                    }


                }



            }

        });

        this.databaseChat.getAllChatsUser((chatPersonList)->{
            this.chatPersonList = chatPersonList;
            this.updateListInformation();
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