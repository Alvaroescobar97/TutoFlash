package co.ajeg.tutoflash.fragments.calendario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.Tutoria;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarioFragment extends Fragment {

    TextView tv_calendario_hoy;
    Button btn_calendario_agregar;

    CalendarView cv_calendario_calendar;
    RecyclerView rv_calendario_eventos;

    private List<Tutoria> tutoriaList;
    private AdapterList<Tutoria> adapterList;

    private EditText et_calendario_nueva_tutoria;
    private MainActivity mainActivity;
    private String selectedday;
    private String selectedMonth;
    private String selectedYear;




    public CalendarioFragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity = mainActivity;
        this.tutoriaList= new ArrayList<>();

    }


    public static CalendarioFragment newInstance(MainActivity mainActivity) {
        CalendarioFragment fragment = new CalendarioFragment(mainActivity);
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);

        this.tv_calendario_hoy = view.findViewById(R.id.tv_calendario_hoy);
        this.btn_calendario_agregar = view.findViewById(R.id.btn_calendario_agregar);
        this.cv_calendario_calendar = view.findViewById(R.id.cv_calendario_calendar);

        cv_calendario_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedday= dayOfMonth + "";
                selectedMonth= month +"";
                selectedYear= year +"";

            }
        });



        this.et_calendario_nueva_tutoria = view.findViewById(R.id.et_calendario_nuevaturoia);
        this.rv_calendario_eventos = view.findViewById(R.id.rv_calendario_eventos);
        this.rv_calendario_eventos.setLayoutManager(new LinearLayoutManager(this.getContext()));




        mainActivity = FragmentUtil.getActivity();
        mainActivity.headerFragment.changeTitleHeader("Calendario");
//
//        layoutManager= new LinearLayoutManager(mainActivity.getApplicationContext());
//        this.rv_calendario_eventos.setLayoutManager(layoutManager);


        adapterList = new AdapterList<>(this.rv_calendario_eventos, this.tutoriaList, R.layout.list_item_calendario, new AdapterManagerList<Tutoria>() {

            private TextView tv_list_item_calendario_descripcion;
            private TextView tv_list_item_calendario_fecha;
            private Button btn_list_item_calendario_erase;

            @Override
            public void onCreateView(View v) {
                tv_list_item_calendario_descripcion= v.findViewById(R.id.tutoria_item_Des);
                tv_list_item_calendario_fecha= v.findViewById(R.id.tutoria_Item_fecha);
                btn_list_item_calendario_erase= v.findViewById(R.id.btn_Calendario_Erase);


            }

            @Override
            public void onChangeView(Tutoria elemnto, View view, int position) {

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                Date fecha = new Date(elemnto.getFecha());
                String stringFecha= dateFormat.format(fecha).toString();
                tv_list_item_calendario_descripcion.setText(elemnto.getDescripcion());
                tv_list_item_calendario_fecha.setText(stringFecha);

                btn_list_item_calendario_erase.setOnClickListener(v ->{
                    adapterList.onRemoveItem(elemnto);
                        }
                );



            }
        });


        this.btn_calendario_agregar.setOnClickListener(this::agregarEvento);


        return view;
    }






    public void agregarEvento(View v){

        //openDialog();

        String descripcion = et_calendario_nueva_tutoria.getEditableText().toString();

        //fecha format dd/mm/yy
        String fecha = selectedday +"/" + selectedMonth + "/" + selectedYear;

        if(!descripcion.equals("")){

            Tutoria tutoria= new Tutoria(descripcion,fecha);
            adapterList.onAddItem(tutoria);
        }

    }

    public void openDialog(){
        CalendarioDialoq calendarioDialog = new CalendarioDialoq();
        calendarioDialog.show( getFragmentManager(),"dialog");


    }

}