package co.ajeg.tutoflash.fragments.util;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import co.ajeg.tutoflash.activities.MainActivity;

public class FragmentUtil {

    static private MainActivity activity;

    public FragmentUtil(MainActivity activity){
        this.activity = activity;
    }

    public static void replaceFragment(int layout, Fragment fragment){
        FragmentActivity appCompatActivity = activity;
        appCompatActivity.runOnUiThread(()->{
            FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(layout, fragment);
            transaction.commit();
        });
    }

    public static void startActivity(Class classObjetivo){
        activity.runOnUiThread(()->{
            Intent intent = new Intent(activity, classObjetivo);
            activity.startActivity(intent);
        });

    }

    public static MainActivity getActivity() {
        return getActivity(null);
    }

    public static MainActivity getActivity(OnGetActivityFrament onGetActivityFrament) {
        if(onGetActivityFrament !=null){
            if(activity != null){
                onGetActivityFrament.onLoad(activity);
            }else{

            }
        }
        return activity;
    }

    public interface OnGetActivityFrament{
        void onLoad(MainActivity mainActivity);
    }
}
