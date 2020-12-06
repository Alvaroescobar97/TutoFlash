package co.ajeg.tutoflash.fragments.notificacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.ajeg.tutoflash.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificacionTemaColaborarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionTemaColaborarFragment extends Fragment {


    public NotificacionTemaColaborarFragment() {
        // Required empty public constructor
    }

    public static NotificacionTemaColaborarFragment newInstance() {
        NotificacionTemaColaborarFragment fragment = new NotificacionTemaColaborarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_notificacion_tema_colaborar, container, false);



        return view;
    }
}