package co.ajeg.tutoflash.firebase.storage;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StorageFirebase {

    static public void uploadFile(String[] ruta, String path, OnCompleteListenerStorage onCompleteListenerStorage){
        new Thread(()->{
            FirebaseStorage fs = FirebaseStorage.getInstance();
            String url = getRoutes(ruta);
            if(url != null){
                try {
                    FileInputStream fileInputStream = new FileInputStream(new File(path));
                    fs.getReference().child(url).putStream(fileInputStream).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            onCompleteListenerStorage.onLoad(url);
                        }else{
                            onCompleteListenerStorage.onLoad(null);
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    static public void gerUrlFile(String[] ruta, OnCompleteListenerStorage onCompleteListenerStorage){
        new Thread(()->{
            FirebaseStorage fs = FirebaseStorage.getInstance();
            String url = getRoutes(ruta);
            if(url != null) {
                fs.getReference().child(url).getDownloadUrl().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        String urlFirebaseStorege = task.getResult().toString();
                        onCompleteListenerStorage.onLoad(urlFirebaseStorege);
                    }else{
                        onCompleteListenerStorage.onLoad(null);
                    }
                });
            }


        }).start();
    }

    static private String getRoutes(String[] urls) {
        String  url = null;
        for (int i = 0; i < urls.length; i++) {
            String u = urls[i];
            if (u != null && u.equals("") == false) {
                if (i == 0) {
                    url = u;
                } else {
                    url = url + "/" + u;
                }
            } else {
                url = null;
                Log.e("No existe un valor", u);
                i = urls.length;
            }
        }
        return url;
    }

    public interface OnCompleteListenerStorage{
        void onLoad(String result);
    }

}
