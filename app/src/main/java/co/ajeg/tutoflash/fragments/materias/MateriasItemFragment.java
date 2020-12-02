package co.ajeg.tutoflash.fragments.materias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.ajeg.tutoflash.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MateriasItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MateriasItemFragment extends Fragment {

    public MateriasItemFragment() {
        // Required empty public constructor
    }

    public static MateriasItemFragment newInstance() {
        MateriasItemFragment fragment = new MateriasItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materias_item, container, false);
    }
}