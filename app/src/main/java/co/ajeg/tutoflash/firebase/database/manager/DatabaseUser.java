package co.ajeg.tutoflash.firebase.database.manager;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.firebase.storage.StorageFirebase;
import co.ajeg.tutoflash.model.User;

public class DatabaseUser {


    public static void getImageUrlProfile(Fragment fragment, String urlImage, OnCompleteImageProfile onCompleteImageProfile){

        fragment.getActivity().runOnUiThread(()->{
            if(urlImage.equals("") == false){

                if (urlImage.contains(DBROUTES.USERS_IMAGES)){

                    StorageFirebase.gerUrlFile(fragment.getActivity(), new String[]{urlImage}, (url)->{
                        onCompleteImageProfile.onLoadUrlImageProfile(url);
                    });

                }else{
                    String urlImageResult = urlImage;
                    onCompleteImageProfile.onLoadUrlImageProfile(urlImageResult);

                }

            }else{
                onCompleteImageProfile.onLoadUrlImageProfile(null);
            }
        });
    }

    public static void getRefUserId(String id,OnCompleteListenerUser onCompleeteListenerUser){
        FirebaseFirestore.getInstance().collection(DBROUTES.USERS)
                .document(id).get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot.exists()){
                            onCompleeteListenerUser.onLoadUser(documentSnapshot.toObject(User.class));
                        }
                        else {
                            onCompleeteListenerUser.onLoadUser(null);
                        }

                    }else {
                        onCompleeteListenerUser.onLoadUser(null);
                    }
                });
    }

    public interface OnCompleteListenerUser{
        void onLoadUser(User usuario);
    }

    public interface OnCompleteImageProfile{
        void onLoadUrlImageProfile(String urlImage);
    }

}
