package co.ajeg.tutoflash.firebase.database.manager;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.model.chat.ChatPerson;

public class DatabaseChat {

    public static void getAllChatsUser(OnCompleteListenerChats onCompleteListenerChats){
        List<ChatPerson> chatPersonList = new ArrayList<>();
        if(Autenticacion.user != null){

        }
    }

    public interface OnCompleteListenerChats{
        void onLoad(ChatPerson chatPerson);
    }
}
