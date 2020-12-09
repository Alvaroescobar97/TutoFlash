package co.ajeg.tutoflash.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import co.ajeg.tutoflash.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_calendario_newitem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_calendario_newitem extends Fragment {

    private EditText description;
    private EditText fecha;
    private Button agregar;
    private Button salir;

    public fragment_calendario_newitem() {
        // Required empty public constructor
    }

    public static fragment_calendario_newitem newInstance() {
        fragment_calendario_newitem fragment = new fragment_calendario_newitem();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_calendario_newitem, container, false);

        this.description = view.findViewById(R.id.textDescrip);
        this.fecha= view.findViewById(R.id.editTextDate);
        this.agregar= view.findViewById(R.id.btn_calendario_Agregar);
        this.salir= view.findViewById(R.id.btn_calendario_Salir);

        this.agregar.setOnClickListener(this::agregar);
        this.salir.setOnClickListener(this::salir);
        
        return view;
    }

    private void salir(View view) {


    }

    private void agregar(View view) {

    }
}