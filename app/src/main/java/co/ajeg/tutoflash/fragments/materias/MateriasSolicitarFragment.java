package co.ajeg.tutoflash.fragments.materias;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;

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


    String[] categoriasNameList = {"Matematicas", "Fisica", "Literatura", "Ingles", "Programación", "Sistemas"};

    private MainActivity mainActivity;
    private AppCompatAutoCompleteTextView til_act_materias_ofrecer_list_opciones;
    private TextInputLayout til_materias_ofrecer_tema;
    private TextInputLayout til_materias_ofrecer_descripcion;
    private TextInputLayout til_materias_ofrecer_tiempo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materias_solicitar, container, false);

        this.mainActivity = FragmentUtil.getActivity();
        this.mainActivity.headerFragment.changeTitleHeader("Solicitar tutor");


        til_materias_ofrecer_tema = view.findViewById(R.id.til_materias_ofrecer_tema);
        til_materias_ofrecer_descripcion = view.findViewById(R.id.til_materias_ofrecer_descripcion);
        til_materias_ofrecer_tiempo = view.findViewById(R.id.til_materias_ofrecer_tiempo);

        Button btn_materias_ofrecer_solicitar = view.findViewById(R.id.btn_materias_ofrecer_solicitar);
        Button btn_materias_ofrecer_cancelar = view.findViewById(R.id.btn_materias_ofrecer_cancelar);

        //initiate an auto complete text view
        til_act_materias_ofrecer_list_opciones = view.findViewById(R.id.act_materias_ofrecer_list_opciones);
        ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, categoriasNameList);

        til_act_materias_ofrecer_list_opciones.setAdapter(adapter);
        til_act_materias_ofrecer_list_opciones.setThreshold(1);//start searching from 1 character
        til_act_materias_ofrecer_list_opciones.setAdapter(adapter);   //set the adapter for displaying country name list

        btn_materias_ofrecer_solicitar.setOnClickListener(this::onClickSolicitar);
        btn_materias_ofrecer_cancelar.setOnClickListener(this::onClickCancelar);

        return view;
    }

    public void onClickSolicitar(View v){

    }

    public void onClickCancelar(View v){

    }
}