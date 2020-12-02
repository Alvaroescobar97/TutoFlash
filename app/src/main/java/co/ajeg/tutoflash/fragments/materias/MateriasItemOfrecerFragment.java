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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MateriasItemOfrecerFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        return inflater.inflate(R.layout.fragment_materias_item_ofrecer, container, false);
    }
}