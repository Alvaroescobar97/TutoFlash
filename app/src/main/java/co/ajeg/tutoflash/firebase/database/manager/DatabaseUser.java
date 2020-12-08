package co.ajeg.tutoflash.firebase.database.manager;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.firebase.storage.StorageFirebase;
import co.ajeg.tutoflash.model.Calificacion;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.notificacion.Notificacion;

public class DatabaseUser {

    public static void calificarUser(Fragment fragment, Notificacion notificacion, User currentUser, float nCalificacion, OnCompleteListenerUser onCompleteListenerUser){
        fragment.getActivity().runOnUiThread(()->{

            User user = Autenticacion.getUser();
            if(user != null){
                getMyRefUser().collection(DBROUTES.USERS_NOTIFICACIONES).document(notificacion.getId()).delete().addOnCompleteListener((task)->{
                    if(task.isSuccessful()){
                        //String uid, String userId, String tema, int value
                        Calificacion calificacion = new Calificacion(UUID.randomUUID().toString(), user.getId(), nCalificacion);
                        getRefUser(currentUser.getId()).collection(DBROUTES.USERS_CALIFICACIONES).document(calificacion.getUid()).set(calificacion);
                        onCompleteListenerUser.onLoadUser(currentUser);
                    }else{

                    }
                });

            }
        });
    }


    public static void getImageUrlProfile(AppCompatActivity activity, String urlImage, OnCompleteImageProfile onCompleteImageProfile) {

        activity.runOnUiThread(() -> {
            if (urlImage.equals("") == false) {

                if (urlImage.contains(DBROUTES.USERS_IMAGES)) {

                    StorageFirebase.gerUrlFile(activity, new String[]{urlImage}, (url) -> {
                        onCompleteImageProfile.onLoadUrlImageProfile(url);
                    });

                } else {
                    String urlImageResult = urlImage;
                    onCompleteImageProfile.onLoadUrlImageProfile(urlImageResult);

                }
            } else {
                onCompleteImageProfile.onLoadUrlImageProfile(null);
            }
        });
    }

    public static void getImageUrlProfile(Fragment fragment, String urlImage, OnCompleteImageProfile onCompleteImageProfile) {
        fragment.getActivity().runOnUiThread(() -> {
            if (urlImage.equals("") == false) {
                if (urlImage.contains(DBROUTES.USERS_IMAGES)) {

                    StorageFirebase.gerUrlFile(fragment.getActivity(), new String[]{urlImage}, (url) -> {
                        onCompleteImageProfile.onLoadUrlImageProfile(url);
                    });

                } else {
                    String urlImageResult = urlImage;
                    onCompleteImageProfile.onLoadUrlImageProfile(urlImageResult);
                }
            } else {
                onCompleteImageProfile.onLoadUrlImageProfile(null);
            }
        });
    }

    public static void getRefUserId(AppCompatActivity appCompatActivity, String id, OnCompleteListenerUser onCompleeteListenerUser ){
        appCompatActivity.runOnUiThread(()->{
            getRefUserId(id, onCompleeteListenerUser);
        });
    }

    public static void getRefUserId(String id, OnCompleteListenerUser onCompleeteListenerUser) {
        FirebaseFirestore.getInstance().collection(DBROUTES.USERS)
                .document(id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    onCompleeteListenerUser.onLoadUser(documentSnapshot.toObject(User.class));
                } else {
                    onCompleeteListenerUser.onLoadUser(null);
                }

            } else {
                onCompleeteListenerUser.onLoadUser(null);
            }
        });
    }

    public static DocumentReference getRefUser(String userId) {
        return FirebaseFirestore.getInstance().collection(DBROUTES.USERS)
                .document(userId);
    }

    public static DocumentReference getMyRefUser() {
        DocumentReference documentReference = null;
        User user =  Autenticacion.getUser();
        if(user != null){
            documentReference = FirebaseFirestore.getInstance().collection(DBROUTES.USERS).document(Autenticacion.getUser().getId());
        }
        return documentReference;
    }


    public interface OnCompleteListenerUser {
        void onLoadUser(User usuario);
    }

    public interface OnCompleteImageProfile {
        void onLoadUrlImageProfile(String urlImage);
    }

}
