package co.ajeg.tutoflash.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;

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


    public CalendarioFragment() {
        // Required empty public constructor
    }

    public static CalendarioFragment newInstance() {
        CalendarioFragment fragment = new CalendarioFragment();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);

        this.tv_calendario_hoy = view.findViewById(R.id.tv_calendario_hoy);
        this.btn_calendario_agregar = view.findViewById(R.id.btn_calendario_agregar);
        this.cv_calendario_calendar = view.findViewById(R.id.cv_calendario_calendar);
        this.rv_calendario_eventos = view.findViewById(R.id.rv_calendario_eventos);

        mainActivity = FragmentUtil.getActivity();
        mainActivity.headerFragment.changeTitleHeader("Calendario");

        this.btn_calendario_agregar.setOnClickListener(this::agregarEvento);

        return view;
    }

    public void agregarEvento(View v){

    }
}