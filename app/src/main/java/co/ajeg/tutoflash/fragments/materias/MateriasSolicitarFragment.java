package co.ajeg.tutoflash.fragments.materias;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseMateria;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.materia.Materia;
import co.ajeg.tutoflash.model.materia.MateriaTema;


public class MateriasSolicitarFragment extends Fragment {

    private MainActivity mainActivity;
    private DatabaseMateria databaseMateria;

    private AppCompatAutoCompleteTextView til_act_materias_ofrecer_list_opciones;
    private TextInputLayout til_materias_ofrecer_tema;
    private TextInputLayout til_materias_ofrecer_descripcion;
    private TextInputLayout til_materias_ofrecer_informacion;
    private TextInputLayout til_materias_ofrecer_tiempo;

    private Map<String, String> categoriasNameListAll;
    String[] categoriasNameList = {"Matematicas", "Fisica", "Literatura", "Ingles", "Programación", "Sistemas"};

    private ArrayList<String> listNamesMaterias;

    public MateriasSolicitarFragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity = mainActivity;
        this.listNamesMaterias = new ArrayList<>();
        this.databaseMateria = DatabaseMateria.getInstance(mainActivity);

        this.categoriasNameListAll = new HashMap<>();
        for (int i = 0; i < categoriasNameList.length; i++) {
            String nameCategoria = categoriasNameList[i];
            categoriasNameListAll.put(nameCategoria, nameCategoria);
        }

        databaseMateria.getAllMaterias((materiaList) -> {
            if (materiaList != null) {
                for (int i = 0; i < materiaList.size(); i++) {
                    String nameCategoria = materiaList.get(i).getName();
                    categoriasNameListAll.put(nameCategoria, nameCategoria);
                }
                this.getNameMaterias();
            } else {
                this.getNameMaterias();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materias_solicitar, container, false);

        this.mainActivity.headerFragment.changeTitleHeader("Solicitar tutor");

        til_materias_ofrecer_tema = view.findViewById(R.id.til_materias_ofrecer_tema);
        til_materias_ofrecer_descripcion = view.findViewById(R.id.til_materias_ofrecer_descripcion);
        til_materias_ofrecer_informacion = view.findViewById(R.id.til_materias_ofrecer_informacion);
        til_materias_ofrecer_tiempo = view.findViewById(R.id.til_materias_ofrecer_tiempo);

        Button btn_materias_ofrecer_solicitar = view.findViewById(R.id.btn_materias_ofrecer_solicitar);
        Button btn_materias_ofrecer_cancelar = view.findViewById(R.id.btn_materias_ofrecer_cancelar);

        //initiate an auto complete text view
        til_act_materias_ofrecer_list_opciones = view.findViewById(R.id.act_materias_ofrecer_list_opciones);

        this.getNameMaterias();

        FragmentUtil.setOnChangeBackActivity((fragments) -> {
            this.resetView();
        });


        btn_materias_ofrecer_solicitar.setOnClickListener(this::onClickSolicitar);
        btn_materias_ofrecer_cancelar.setOnClickListener(this::onClickCancelar);

        return view;
    }


    public void getNameMaterias() {
        listNamesMaterias.clear();
        for (Map.Entry<String, String> entry : categoriasNameListAll.entrySet()) {
            listNamesMaterias.add(entry.getValue());
        }
        if (til_act_materias_ofrecer_list_opciones != null) {
            ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, listNamesMaterias);

            til_act_materias_ofrecer_list_opciones.setAdapter(adapter);
            til_act_materias_ofrecer_list_opciones.setThreshold(1);//start searching from 1 character
            til_act_materias_ofrecer_list_opciones.setAdapter(adapter);   //set the adapter for displaying country name list;
        }
    }

    public void onClickSolicitar(View v) {
        String tema = this.til_materias_ofrecer_tema.getEditText().getText().toString();
        String descripcion = this.til_materias_ofrecer_descripcion.getEditText().getText().toString();
        String informacion = this.til_materias_ofrecer_informacion.getEditText().getText().toString();
        String tiempo = this.til_materias_ofrecer_tiempo.getEditText().getText().toString();
        String materiaName = this.til_act_materias_ofrecer_list_opciones.getText().toString();

        if (!tema.equals("")
                && !descripcion.equals("")
                && !informacion.equals("")
                && !tiempo.equals("")
                && !materiaName.equals("")
                && Autenticacion.getUser() != null
        ) {
            String uid = UUID.randomUUID().toString();
            String userId = Autenticacion.getUser().getId();
            long date = (new Date()).getTime();

            //String id, String autorId, String title, String descripcion, String informacion, String tiempo, String fecha
            MateriaTema materiaTema = new MateriaTema(uid, userId, tema, descripcion, informacion, tiempo, date);
            this.databaseMateria.createTema(materiaName, materiaTema, (materiaTemaDatabase) -> {
                if(materiaTemaDatabase != null){
                    this.resetView();
                    FragmentUtil.replaceFragmentInMain(this.mainActivity.homeFragment);
                    FragmentUtil.resetFragmentNav();
                }else{
                    Toast.makeText(mainActivity, "Ocurrio un error. Lo sentimos", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(mainActivity, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickCancelar(View v) {
        this.resetView();
        FragmentUtil.goToBackFragment();
    }

    public void resetView() {
        this.til_materias_ofrecer_tema.getEditText().setText("");
        this.til_materias_ofrecer_descripcion.getEditText().setText("");
        this.til_materias_ofrecer_informacion.getEditText().setText("");
        this.til_materias_ofrecer_tiempo.getEditText().setText("");
        this.til_act_materias_ofrecer_list_opciones.setText("");
    }
}