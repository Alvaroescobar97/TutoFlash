package co.ajeg.tutoflash.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.firebase.FirebaseMensajes;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.fragments.CalendarioFragment;
import co.ajeg.tutoflash.fragments.chat.ChatFragment;
import co.ajeg.tutoflash.fragments.HeaderFragment;
import co.ajeg.tutoflash.fragments.PerfilFragment;
import co.ajeg.tutoflash.fragments.materias.HomeFragment;
import co.ajeg.tutoflash.fragments.notificacion.NotificacionFragment;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.galeria.Galeria;


public class MainActivity extends AppCompatActivity {

    public HomeFragment homeFragment;


    public NotificacionFragment notificacionFragment;


    public CalendarioFragment calendarioFragment;

    public ChatFragment chatFragment;

    public PerfilFragment perfilFragment;

    public HeaderFragment headerFragment;

    public Autenticacion autenticacion;
    FragmentUtil fragmentUtil;

    public Galeria galeria;

    private BottomNavigationView navigationView;

    public FirebaseMensajes firebaseMensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseMensajes = FirebaseMensajes.getInstance(this);

        this.galeria = new Galeria(this);

        fragmentUtil = new FragmentUtil(this);

        navigationView = findViewById(R.id.navegacion_view);

        headerFragment = HeaderFragment.newInstance(this);

        homeFragment = HomeFragment.newInstance(this);

        notificacionFragment = new NotificacionFragment(this);

        calendarioFragment = CalendarioFragment.newInstance(this);

        chatFragment = new ChatFragment(this);


        perfilFragment = PerfilFragment.newInstance(this);

        fragmentUtil.replaceFragment(R.id.fragment_container_header, headerFragment);
        fragmentUtil.replaceFragment(R.id.fragment_container, homeFragment);



       firebaseMensajes.suscribir((task)->{
           if(task){

           }else{

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.galeria.onActivityResult(requestCode, resultCode, data);
    }

}