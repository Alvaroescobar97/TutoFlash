package co.ajeg.tutoflash.galeria;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;


public class Galeria {

    private AppCompatActivity activity;
    public static final int PERMISSIONS_CALLBACK =11;
    public static final int CAMERA_CALLBACK = 12;
    public static final int GALLERY_CALLBACK = 13;
    private File file;

    public Galeria (AppCompatActivity activity){

        this.activity = activity;


        this.activity.runOnUiThread(()->{
            ActivityCompat.requestPermissions(this.activity, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            },  PERMISSIONS_CALLBACK);
        });

    }

    public void openCamera(){
        this.activity.runOnUiThread(()->{
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = new File(this.activity.getExternalFilesDir(null) + "/photo.png");
            Uri uri = FileProvider.getUriForFile(this.activity, this.activity.getPackageName(), file);

            i.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            this.activity.startActivityForResult(i, CAMERA_CALLBACK);
        });
    }

    public void openGaleria(){
        this.activity.runOnUiThread(()->{
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.setType("image/*");

            this.activity.startActivityForResult(i, GALLERY_CALLBACK);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == CAMERA_CALLBACK && resultCode == this.activity.RESULT_OK){
            Bitmap image = BitmapFactory.decodeFile(file.getPath());
            Bitmap thumbnail = Bitmap.createScaledBitmap(image, image.getWidth()/4, image.getHeight()/4, true);
        }

        if(resultCode == CAMERA_CALLBACK && resultCode == this.activity.RESULT_OK){
            Uri uri = data.getData();
            String path = UtilDomi.getPath(this.activity, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        this.activity.runOnUiThread(()->{
            if(requestCode == PERMISSIONS_CALLBACK){
                boolean allGrant = true;
                for (int i = 0; i < grantResults.length; i++){
                    if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                        allGrant = false;
                        break;
                    }
                }
                if(allGrant){
                    //Todos los permisos concedidos

                }else{
                    //NO todos los permisos concedidos
                }

            }
        });

    }
}
