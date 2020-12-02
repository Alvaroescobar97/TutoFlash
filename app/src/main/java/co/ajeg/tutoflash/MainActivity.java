package co.ajeg.tutoflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private CalendarFragment calendarFragment;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);

        homeFragment = HomeFragment.newInstance();
        calendarFragment = CalendarFragment.newInstance();

        navigation.setOnNavigationItemSelectedListener(
                menuItem ->{

                    switch (menuItem.getItemId()){
                        case R.id.homeItem:
                            showFragment(homeFragment);
                            break;
                        case R.id.calendarItem:
                            showFragment(calendarFragment);
                            break;
                    }

                    return true;
                }
        );
    }

    public void showFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

}