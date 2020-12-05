package co.ajeg.tutoflash.fragments.materias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.ajeg.tutoflash.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MateriasItemOfrecerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MateriasItemOfrecerFragment extends Fragment {


    public MateriasItemOfrecerFragment() {
        // Required empty public constructor
    }

    public static MateriasItemOfrecerFragment newInstance() {
        MateriasItemOfrecerFragment fragment = new MateriasItemOfrecerFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materias_ofrecer, container, false);



        return view;
    }


}