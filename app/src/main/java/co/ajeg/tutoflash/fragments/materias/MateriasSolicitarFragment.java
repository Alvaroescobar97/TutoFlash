package co.ajeg.tutoflash.fragments.materias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.ajeg.tutoflash.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MateriasSolicitarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MateriasSolicitarFragment extends Fragment {

    public MateriasSolicitarFragment() {
        // Required empty public constructor
    }
    
    public static MateriasSolicitarFragment newInstance() {
        MateriasSolicitarFragment fragment = new MateriasSolicitarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materias_solicitar, container, false);
    }
}