package co.ajeg.tutoflash.fragments.materias;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseMateria;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.materia.MateriaTema;
import co.ajeg.tutoflash.model.materia.MateriaTutor;


public class MateriasItemOfrecerFragment extends Fragment {

    private MainActivity mainActivity;
    private User autor;
    private String materiaName;
    private MateriaTema materiaTema;
    private int dia, mes, year;
    private List<String> horariosDisponibles;
    private AdapterList<String> adapterList;
    private DatabaseMateria databaseMateria;

    private TextInputLayout til_materias_ofrecer_descripcion;
    private TextInputLayout til_materias_ofrecer_precio;

    private EditText et_materias_ofrecer_fecha;
    private Button btn_materias_ofrecer_add_fecha;
    private RecyclerView rv_materias_ofrecer_fechas;

    private Button btn_materias_ofrecer_ofrecer;
    private Button btn_materias_ofrecer_cancelar;

    public MateriasItemOfrecerFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.horariosDisponibles = new ArrayList<>();
        this.databaseMateria = DatabaseMateria.getInstance(mainActivity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materias_ofrecer, container, false);

        this.til_materias_ofrecer_descripcion = view.findViewById(R.id.til_materias_ofrecer_descripcion);
        this.til_materias_ofrecer_precio = view.findViewById(R.id.til_materias_ofrecer_precio);

        this.et_materias_ofrecer_fecha = view.findViewById(R.id.et_materias_ofrecer_fecha);
        this.btn_materias_ofrecer_add_fecha = view.findViewById(R.id.btn_materias_ofrecer_add_fecha);
        this.rv_materias_ofrecer_fechas = view.findViewById(R.id.rv_materias_ofrecer_fechas);
        this.rv_materias_ofrecer_fechas.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.btn_materias_ofrecer_ofrecer = view.findViewById(R.id.btn_materias_ofrecer_ofrecer);
        this.btn_materias_ofrecer_cancelar = view.findViewById(R.id.btn_materias_ofrecer_cancelar);

        this.adapterList = new AdapterList<>(this.rv_materias_ofrecer_fechas, this.horariosDisponibles, R.layout.list_item_materia_solicitar_horario, new AdapterManagerList<String>() {

            TextView tv_list_item_materia_solicitar_horario_fecha;
            ImageView iv_list_item_materia_solicitar_horario_eliminar;


            @Override
            public void onCreateView(View v) {
                tv_list_item_materia_solicitar_horario_fecha = v.findViewById(R.id.tv_list_item_materia_solicitar_horario_fecha);
                iv_list_item_materia_solicitar_horario_eliminar = v.findViewById(R.id.iv_list_item_materia_solicitar_horario_eliminar);
            }

            @Override
            public void onChangeView(String elemnto, View view, int position) {

                tv_list_item_materia_solicitar_horario_fecha.setText(elemnto);

                iv_list_item_materia_solicitar_horario_eliminar.setOnClickListener(v->{
                    adapterList.onRemoveItem(elemnto);
                });

            }
        });

        this.btn_materias_ofrecer_ofrecer.setOnClickListener(this::onClickOfrecerAyuda);
        this.btn_materias_ofrecer_cancelar.setOnClickListener(this::onClickCancelar);



        FragmentUtil.setOnChangeBackActivity((fragments)->{
            this.resetView();
        });


        this.btn_materias_ofrecer_add_fecha.setOnClickListener(this::openDatePicker);
        return view;
    }

    private void onClickOfrecerAyuda(View v){

        String descripcion =  this.til_materias_ofrecer_descripcion.getEditText().getText().toString();
        String precio =  this.til_materias_ofrecer_precio.getEditText().getText().toString();
        List<String> fechas = this.adapterList.getListActual();

        if(this.materiaTema != null &&
                !descripcion.equals("") &&
                !precio.equals("") &&
                fechas.size() > 0
        ){
            String idTutor = Autenticacion.getUser().getId();
            String uid = idTutor;
            String publicacionId = this.materiaTema.getId();

            String autorId = this.autor.getId();

            //String id, String autorId, String publicacionId, String tutorId, String descripcion, String precio, List<String> fechas
            MateriaTutor materiaTutor = new MateriaTutor(uid, autorId, publicacionId, idTutor, descripcion, precio, fechas);

            this.databaseMateria.createMateriaTutor(this.materiaName, materiaTutor, (materiaTutorDatabase)->{
                if(materiaTutor != null){
                    FragmentUtil.resetFragmentNav();

                    FragmentUtil.replaceFragment(R.id.fragment_container, mainActivity.homeFragment);
                }else{

                }
            });
        }


    }

    private void onClickCancelar(View v){
        FragmentUtil.goToBackFragment();
    }

    private void resetView(){

    }


    private void openDatePicker(View v){
        Calendar calendar = Calendar.getInstance();
        this.dia = calendar.get(Calendar.DAY_OF_MONTH);
        this.mes = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int mes, int dia) {
                String fecha = dia + "/" + (mes + 1) +"/" + year;
                et_materias_ofrecer_fecha.setText(fecha);
                adapterList.onAddItem(fecha);
            }
        },this.year , this.mes, this.dia);
        datePickerDialog.show();

    }

    public void setCurrentTema(MateriaTema materiaTema, String materiaName){
        this.materiaTema = materiaTema;
        this.materiaName = materiaName;
    }

    public void setAutorId(User autor) {
        this.autor = autor;
    }
}