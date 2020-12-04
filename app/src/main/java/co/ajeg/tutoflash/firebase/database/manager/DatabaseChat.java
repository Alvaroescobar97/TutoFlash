package co.ajeg.tutoflash.firebase.database.manager;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.chat.ChatMensaje;
import co.ajeg.tutoflash.model.chat.ChatPerson;

public class DatabaseChat {

    public static void getAllChatsUser(OnCompleteListenerChats onCompleteListenerChats){
        CollectionReference collectionReference = getReferenceCollectionUserChats();
        if(collectionReference != null){
            collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    List<ChatPerson> listaChatsDatabase = value.toObjects(ChatPerson.class);
                    onCompleteListenerChats.onLoad(listaChatsDatabase);
                }
            });
        }else{
            onCompleteListenerChats.onLoad(new ArrayList<>());
        }
    }

    public static void createNewChat(ChatPerson chatPerson, OnCompleteListenerChat onCompleteListenerChat){
        CollectionReference collectionReference = getReferenceCollectionUserChats();
        if(collectionReference != null){
            collectionReference.document(chatPerson.getId()).set(chatPerson).addOnCompleteListener((task)->{
                if(task.isSuccessful()){
                    onCompleteListenerChat.onLoad(chatPerson);
                }else{
                    onCompleteListenerChat.onLoad(null);
                }
            });
        }else{
            onCompleteListenerChat.onLoad(null);
        }
    }

    public static void sendMensaje(String chatid, ChatMensaje mensaje){
        getReferenceChatMensajes(chatid).document(mensaje.getId()).set(mensaje);
    }

    private static CollectionReference getReferenceChatMensajes(String uidChat){

        CollectionReference collectionReference = getReferenceChat(uidChat)
                .collection(DBROUTES.CHATS_MENSAJES);

        return collectionReference;
    }

    private static DocumentReference getReferenceChat(String uidChat){
        User user = Autenticacion.getUser();
        DocumentReference documentReference = null;
            documentReference = FirebaseFirestore
                    .getInstance()
                    .collection(DBROUTES.CHATS)
                    .document(uidChat);

        return documentReference;
    }


    private static CollectionReference getReferenceCollectionUserChats(){
        User user = Autenticacion.getUser();
        CollectionReference collectionReference = null;
        if(user != null){
            collectionReference = FirebaseFirestore
                    .getInstance()
                    .collection(DBROUTES.USERS)
                    .document(user.getId())
                    .collection(DBROUTES.USERS_CHATS);
        }

        return collectionReference;
    }


    public interface OnCompleteListenerChats{
        void onLoad(List<ChatPerson> chatPersonList);
    }

    public interface OnCompleteListenerChat{
        void onLoad(ChatPerson chatPersonList);
    }
}
