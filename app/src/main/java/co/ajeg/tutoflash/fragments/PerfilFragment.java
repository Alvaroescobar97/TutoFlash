package co.ajeg.tutoflash.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.LoginActivity;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {


    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    private Button btn_perfil_cerrar_session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);


        btn_perfil_cerrar_session = view.findViewById(R.id.btn_perfil_cerrar_session);
        btn_perfil_cerrar_session.setOnClickListener(this::cerrarSession);

        return view;
    }

    public void cerrarSession(View v){
        Autenticacion.logout();
        FragmentUtil.startActivity(LoginActivity.class);
    }
}