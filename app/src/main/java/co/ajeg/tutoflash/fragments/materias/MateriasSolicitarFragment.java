package co.ajeg.tutoflash.fragments.materias;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    private HomeFragment homeFragment;
    private DatabaseMateria databaseMateria;

    private AppCompatAutoCompleteTextView act_materias_solicitar_list_opciones;
    private TextInputLayout til_materias_solicitar_tema;
    private TextInputLayout til_materias_solicitar_descripcion;
    private TextInputLayout til_materias_solicitar_informacion;
    private TextInputLayout til_materias_solicitar_tiempo;

    private Materia currentMateria;

    private Map<String, String> categoriasNameListAll;
    String[] categoriasNameList = {"Matematicas", "Fisica", "Literatura", "Ingles", "Programación", "Sistemas"};

    private ArrayList<String> listNamesMaterias;

    public MateriasSolicitarFragment(HomeFragment homeFragment) {
        // Required empty public constructor
        this.homeFragment = homeFragment;
        this.listNamesMaterias = new ArrayList<>();
        this.databaseMateria = DatabaseMateria.getInstance(homeFragment.mainActivity);

        this.categoriasNameListAll = new HashMap<>();
        for (int i = 0; i < categoriasNameList.length; i++) {
            String nameCategoria = categoriasNameList[i];
            categoriasNameListAll.put(nameCategoria, nameCategoria);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materias_solicitar, container, false);

        this.homeFragment.mainActivity.headerFragment.changeTitleHeader("Solicitar tutor");


        this.til_materias_solicitar_tema = view.findViewById(R.id.til_materias_solicitar_tema);
        this.til_materias_solicitar_descripcion = view.findViewById(R.id.til_materias_solicitar_descripcion);
        this.til_materias_solicitar_informacion = view.findViewById(R.id.til_materias_solicitar_informacion);
        this.til_materias_solicitar_tiempo = view.findViewById(R.id.til_materias_solicitar_tiempo);


        Button btn_materias_ofrecer_solicitar = view.findViewById(R.id.btn_materias_solicitar_solicitar);
        Button btn_materias_ofrecer_cancelar = view.findViewById(R.id.btn_materias_solicitar_cancelar);

        //initiate an auto complete text view
        this.act_materias_solicitar_list_opciones = view.findViewById(R.id.act_materias_solicitar_list_opciones);

        btn_materias_ofrecer_solicitar.setOnClickListener(this::onClickSolicitar);
        btn_materias_ofrecer_cancelar.setOnClickListener(this::onClickCancelar);

        FragmentUtil.setOnChangeBackActivity((fragments) -> {
            this.resetView();
        });

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

        if(this.currentMateria != null){
            this.act_materias_solicitar_list_opciones.postDelayed(()->{
                    this.act_materias_solicitar_list_opciones.setText(FragmentUtil.stringFirtsUpperCase(this.currentMateria.getName()));

            },100);

        }


        return view;
    }


    public void getNameMaterias() {
        this.listNamesMaterias.clear();
        for (Map.Entry<String, String> entry : categoriasNameListAll.entrySet()) {
            this.listNamesMaterias.add(entry.getValue());
        }
        if (this.act_materias_solicitar_list_opciones != null) {
            ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, listNamesMaterias);

            act_materias_solicitar_list_opciones.setThreshold(1);//start searching from 1 character
            act_materias_solicitar_list_opciones.setAdapter(adapter);
        }
    }

    public void onClickSolicitar(View v) {
        String tema = this.til_materias_solicitar_tema.getEditText().getText().toString();
        String descripcion = this.til_materias_solicitar_descripcion.getEditText().getText().toString();
        String informacion = this.til_materias_solicitar_informacion.getEditText().getText().toString();
        String tiempo = this.til_materias_solicitar_tiempo.getEditText().getText().toString();
        String materiaName = this.act_materias_solicitar_list_opciones.getText().toString();

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
                    FragmentUtil.replaceFragmentInMain(this.homeFragment.mainActivity.homeFragment);
                    FragmentUtil.resetFragmentNav();
                }else{
                    Toast.makeText(this.homeFragment.mainActivity, "Ocurrio un error. Lo sentimos", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(this.homeFragment.mainActivity, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickCancelar(View v) {
        this.resetView();
        FragmentUtil.goToBackFragment();
    }

    public void resetView() {
        this.til_materias_solicitar_tema.getEditText().setText("");
        this.til_materias_solicitar_descripcion.getEditText().setText("");
        this.til_materias_solicitar_informacion.getEditText().setText("");
        this.til_materias_solicitar_tiempo.getEditText().setText("");
        this.act_materias_solicitar_list_opciones.setText("");
        this.currentMateria = null;
    }

    public void setCurrentMateria(Materia materia){
        this.currentMateria = materia;
    }
}