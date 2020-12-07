package co.ajeg.tutoflash.firebase.database.manager;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.chat.ChatMensaje;
import co.ajeg.tutoflash.model.chat.ChatPerson;

public class DatabaseChat {

    static private FragmentActivity activity;
    static private DatabaseChat thisClass;
    private ListenerRegistration listenerMensajes;

    private DatabaseChat(FragmentActivity activity){
        this.activity = activity;
    }

    static public DatabaseChat getInstance(FragmentActivity activity){
        activity = activity;
        if(thisClass == null){
            thisClass = new DatabaseChat(activity);
        }
        return thisClass;
    }

    public void getAllChatsUser(OnCompleteListenerChats onCompleteListenerChats){
        this.activity.runOnUiThread(()->{
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
        });

    }

    public void getChatAllMensaje(String chatId, OnCompleteListenerChatMensajes onCompleteListenerChatMensajes){
        this.activity.runOnUiThread(()->{
            if (this.listenerMensajes != null) {
                this.stopListenerMensajes();
            }
            this.listenerMensajes = getReferenceChatMensajes(chatId).orderBy("date", Query.Direction.ASCENDING).addSnapshotListener((value, error)->{
                List<ChatMensaje> mensajes = value.toObjects(ChatMensaje.class);
                onCompleteListenerChatMensajes.onLoad(mensajes);
            });
        });
    }

    public void createNewChat(ChatPerson chatPerson, OnCompleteListenerChat onCompleteListenerChat){
        this.activity.runOnUiThread(()->{
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
        });

    }

    public void sendMensaje(String chatid, ChatMensaje mensaje){
        this.activity.runOnUiThread(()->{
            getReferenceChatMensajes(chatid).document(mensaje.getId()).set(mensaje);
        });

    }

    public void stopListenerMensajes() {
        if (this.listenerMensajes != null) {
            this.listenerMensajes.remove();
            this.listenerMensajes = null;
        }
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

    public static CollectionReference getCollectionsChats(){
        CollectionReference documentReference = FirebaseFirestore
                .getInstance()
                .collection(DBROUTES.CHATS);

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

    public interface OnCompleteListenerChatMensajes{
        void onLoad(List<ChatMensaje> chatMensajeList);
    }
}
