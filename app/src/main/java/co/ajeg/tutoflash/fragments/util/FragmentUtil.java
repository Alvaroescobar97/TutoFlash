package co.ajeg.tutoflash.fragments.util;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;

public class FragmentUtil {

    static private MainActivity activity;

    public static int DRAWABLE_LEFT = 0;
    public static int DRAWABLE_TOP = 1;
    public static int DRAWABLE_RIGHT = 2;
    public static int DRAWABLE_BOTTOM = 3;
    public static ArrayList<Fragment> fragmentsNav;
    public static OnChangeFragmentNav onChangeFragmentNav;
    public static OnChangeFragmentNav onChangeFragmentNavActivity;
    public static Fragment currentFragment;

    public FragmentUtil(MainActivity activity){
        this.activity = activity;
        this.fragmentsNav = new ArrayList<>();
    }

    public static ArrayList<Fragment> getFragmentsNav(){
        return fragmentsNav;
    }
    public static void setOnChangeBackActivity(OnChangeFragmentNav onChange){
        onChangeFragmentNavActivity = onChange;
    }

    public static void setOnChangeFragmentNav(OnChangeFragmentNav onChange){
        onChangeFragmentNav = onChange;
        onChangeFragmentNav.onChangeFragmentNav(fragmentsNav);
    }

    public static void goToBackFragment(){
        if(fragmentsNav != null && fragmentsNav.size() > 0){
            int index = fragmentsNav.size() - 1;
            Fragment fragment = fragmentsNav.get(index);
            fragmentsNav.remove(index);

            if(onChangeFragmentNav != null){
                onChangeFragmentNav.onChangeFragmentNav(fragmentsNav);
            }

            if(onChangeFragmentNavActivity != null){
                onChangeFragmentNavActivity.onChangeFragmentNav(fragmentsNav);
            }

            replaceFragment(R.id.fragment_container, fragment);
        }
    }

    public static void replaceFragmentInMain(Fragment fragment){
        if(fragmentsNav != null){
            if(currentFragment != null){
                fragmentsNav.add(currentFragment);
            }

            if(onChangeFragmentNav != null){
                onChangeFragmentNav.onChangeFragmentNav(fragmentsNav);
            }

            if(onChangeFragmentNavActivity != null){
               // onChangeFragmentNavActivity.onChangeFragmentNav(fragmentsNav);
            }
        }
        replaceFragment(R.id.fragment_container, fragment);
    }

    public static void resetFragmentNav(){
        if(fragmentsNav != null){
            fragmentsNav.clear();

            if(onChangeFragmentNav != null){
                onChangeFragmentNav.onChangeFragmentNav(fragmentsNav);
            }

            if(onChangeFragmentNavActivity != null){
                onChangeFragmentNavActivity.onChangeFragmentNav(fragmentsNav);
            }
        }
    }

    public static void replaceFragment(int layout, Fragment fragment){
        FragmentActivity appCompatActivity = activity;
        appCompatActivity.runOnUiThread(()->{
            currentFragment = fragment;
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

    static public boolean onTouchEventIconDirectionUp(MotionEvent event, EditText editText, int direccion){
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[direccion].getBounds().width())) {
                // your action here
               return true;
            }
        }
        return false;
    }

    public static String stringFirtsUpperCase(String str) {
        if (str.isEmpty()) {
            return str;
        } else {
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
    }

    public interface OnGetActivityFrament{
        void onLoad(MainActivity mainActivity);
    }

    public interface OnChangeFragmentNav{
        void onChangeFragmentNav(ArrayList<Fragment> fragments);
    }
}
