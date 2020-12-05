package co.ajeg.tutoflash.fragments.materias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.chat.ChatPerson;
import co.ajeg.tutoflash.model.materia.Materia;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    MainActivity mainActivity;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.mainActivity = FragmentUtil.getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btn_home_agregar_materia = view.findViewById(R.id.btn_home_agregar_materia);

        RecyclerView rv_home_materias = view.findViewById(R.id.rv_home_materias);
        rv_home_materias.setLayoutManager(new LinearLayoutManager(this.getContext()));

        List<Materia> materias = new ArrayList<>();


        AdapterList<Materia> adapterList = new AdapterList(rv_home_materias, materias, R.layout.list_item_home_materia, new AdapterManagerList<Materia>() {

            private CircleImageView civ_item_home_materia_image;
            private TextView tv_item_home_materia_name;
            private TextView tv_item_home_materia_fecha;

            @Override
            public void onCreateView(View v) {

                this.civ_item_home_materia_image = v.findViewById(R.id.civ_item_home_materia_image);
                this.tv_item_home_materia_name = v.findViewById(R.id.tv_item_home_materia_name);
                this.tv_item_home_materia_fecha = v.findViewById(R.id.tv_item_home_materia_fecha);

            }

            @Override
            public void onChangeView(Materia materia, View view, int position) {

            }
        });

        btn_home_agregar_materia.setOnClickListener(this::addMateriaListPrincipal);

        return view;
    }

    public void addMateriaListPrincipal(View v){
        FragmentUtil.getActivity(activity->{
            FragmentUtil.replaceFragment(R.id.fragment_container, activity.materiasItemOfrecerFragment);
        });

    }
}