package co.ajeg.tutoflash.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.fragments.CalendarioFragment;
import co.ajeg.tutoflash.fragments.ChatFragment;
import co.ajeg.tutoflash.fragments.HeaderFragment;
import co.ajeg.tutoflash.fragments.materias.HomeFragment;
import co.ajeg.tutoflash.fragments.notificacion.NotificacionFragment;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private NotificacionFragment notificacionFragment;
    private CalendarioFragment calendarioFragment;
    private ChatFragment chatFragment;
    private BottomNavigationView navigationView;
    private HeaderFragment headerFragment;
    FragmentUtil fragmentUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentUtil = new FragmentUtil(this);

        navigationView = findViewById(R.id.navegacion_view);

        headerFragment = HeaderFragment.newInstance();
        homeFragment = HomeFragment.newInstance();
        notificacionFragment = NotificacionFragment.newInstance();
        calendarioFragment = CalendarioFragment.newInstance();
        chatFragment = ChatFragment.newInstance();


        fragmentUtil.replaceFragment(R.id.fragment_container, homeFragment);
        fragmentUtil.replaceFragment(R.id.fragment_container_header, headerFragment);


       // NotificacionUtil.createNotification(this, "prueba", "Esto es una prueba de notificacion");

        FirebaseMessaging.getInstance().subscribeToTopic("noticias").addOnCompleteListener((task)->{
            if(task.isSuccessful()){
                Log.e(">>>>", "Subscripcion exitosa");
            }else{
                Log.e(">>>>", "Subscripcion NO exitosa");
            }
        });

        navigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item){

        switch (item.getItemId()){
            case R.id.nav_item_inicio:
                fragmentUtil.replaceFragment(R.id.fragment_container, homeFragment);
                break;
            case R.id.nav_item_notificacion:
                fragmentUtil.replaceFragment(R.id.fragment_container, notificacionFragment);
                break;
            case R.id.nav_item_calendario:
                fragmentUtil.replaceFragment(R.id.fragment_container, calendarioFragment);
                break;
            case R.id.nav_item_chats:
                fragmentUtil.replaceFragment(R.id.fragment_container, chatFragment);
                break;
        }
        return true;
    }


}