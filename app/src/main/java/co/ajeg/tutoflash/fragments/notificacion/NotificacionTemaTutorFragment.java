package co.ajeg.tutoflash.fragments.notificacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificacionTemaTutorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionTemaTutorFragment extends Fragment {

    private MainActivity mainActivity;

    public NotificacionTemaTutorFragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity = mainActivity;
    }


    public static NotificacionTemaTutorFragment newInstance(MainActivity mainActivity) {
        NotificacionTemaTutorFragment fragment = new NotificacionTemaTutorFragment(mainActivity);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notificacion_tema_tutor, container, false);

        return view;
    }
}