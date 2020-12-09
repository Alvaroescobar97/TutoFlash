package co.ajeg.tutoflash.fragments.calendario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.ArrayList;
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

    private MainActivity mainActivity;




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
        this.rv_calendario_eventos = view.findViewById(R.id.rv_calendario_eventos);
        this.rv_calendario_eventos.setLayoutManager(new LinearLayoutManager(this.getContext()));


        mainActivity = FragmentUtil.getActivity();
        mainActivity.headerFragment.changeTitleHeader("Calendario");
//
//        layoutManager= new LinearLayoutManager(mainActivity.getApplicationContext());
//        this.rv_calendario_eventos.setLayoutManager(layoutManager);


        this.btn_calendario_agregar.setOnClickListener(this::agregarEvento);

        adapterList = new AdapterList<>(this.rv_calendario_eventos, this.tutoriaList, R.layout.list_item_calendario, new AdapterManagerList<Tutoria>() {


            @Override
            public void onCreateView(View v) {

            }

            @Override
            public void onChangeView(Tutoria elemnto, View view, int position) {

            }
        });

        return view;
    }





    public void agregarEvento(View v){


    }

}