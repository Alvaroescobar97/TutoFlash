package co.ajeg.tutoflash.fragments.util;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtil {

    private AppCompatActivity activity;

    public FragmentUtil(AppCompatActivity activity){
        this.activity = activity;

    }

    public void replaceFragment(int layout, Fragment fragment){
        this.activity.runOnUiThread(()->{
            FragmentManager fragmentManager = this.activity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(layout, fragment);
            transaction.commit();
        });

    }


}
