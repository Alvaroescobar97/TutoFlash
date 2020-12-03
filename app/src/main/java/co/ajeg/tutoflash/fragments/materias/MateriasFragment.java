package co.ajeg.tutoflash.fragments.materias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.ajeg.tutoflash.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MateriasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MateriasFragment extends Fragment {


    public MateriasFragment() {
        // Required empty public constructor
    }


    public static MateriasFragment newInstance() {
        MateriasFragment fragment = new MateriasFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materias, container, false);



        return view;
    }
}