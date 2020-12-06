package co.ajeg.tutoflash.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.fragments.CalendarioFragment;
import co.ajeg.tutoflash.fragments.chat.ChatFragment;
import co.ajeg.tutoflash.fragments.HeaderFragment;
import co.ajeg.tutoflash.fragments.PerfilFragment;
import co.ajeg.tutoflash.fragments.chat.ChatItemFragment;
import co.ajeg.tutoflash.fragments.materias.HomeFragment;
import co.ajeg.tutoflash.fragments.materias.MateriasItemFragment;
import co.ajeg.tutoflash.fragments.materias.MateriasItemOfrecerFragment;
import co.ajeg.tutoflash.fragments.materias.MateriasSolicitarFragment;
import co.ajeg.tutoflash.fragments.notificacion.NotificacionFragment;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.galeria.Galeria;

public class MainActivity extends AppCompatActivity {

    public HomeFragment homeFragment;
    public MateriasItemFragment materiasItemFragment;
    public MateriasItemOfrecerFragment materiasItemOfrecerFragment;
    public MateriasSolicitarFragment materiasSolicitarFragment;

    public NotificacionFragment notificacionFragment;
    public CalendarioFragment calendarioFragment;

    public ChatFragment chatFragment;
    public ChatItemFragment chatItemFragment;

    public PerfilFragment perfilFragment;

    public HeaderFragment headerFragment;

    Autenticacion autenticacion;
    FragmentUtil fragmentUtil;

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentUtil = new FragmentUtil(this);

        navigationView = findViewById(R.id.navegacion_view);

        headerFragment = HeaderFragment.newInstance();

        homeFragment = HomeFragment.newInstance(this);
        materiasItemFragment = new MateriasItemFragment(this);
        materiasItemOfrecerFragment = new MateriasItemOfrecerFragment(this);
        materiasSolicitarFragment = new MateriasSolicitarFragment(this);

        notificacionFragment = new NotificacionFragment(this);
        calendarioFragment = CalendarioFragment.newInstance();

        chatFragment = new ChatFragment(this);
        chatItemFragment = ChatItemFragment.newInstance();

        perfilFragment = PerfilFragment.newInstance();

        fragmentUtil.replaceFragment(R.id.fragment_container_header, headerFragment);
        fragmentUtil.replaceFragment(R.id.fragment_container, homeFragment);



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

        FragmentUtil.resetFragmentNav();

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

    @Override
    protected void onStart() {
        super.onStart();
        this.autenticacion = new Autenticacion(this);
    }

}