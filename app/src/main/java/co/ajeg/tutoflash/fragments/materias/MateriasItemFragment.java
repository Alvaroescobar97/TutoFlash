package co.ajeg.tutoflash.fragments.materias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.model.chat.ChatPerson;
import co.ajeg.tutoflash.model.materia.Materia;
import co.ajeg.tutoflash.model.materia.MateriaTema;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MateriasItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MateriasItemFragment extends Fragment {

    public MateriasItemFragment() {
        // Required empty public constructor
    }

    public static MateriasItemFragment newInstance() {
        MateriasItemFragment fragment = new MateriasItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materias_item, container, false);

        RecyclerView rv_home_materias_item_lista = view.findViewById(R.id.rv_home_materias_item_lista);
        List<MateriaTema> materiasTemas = new ArrayList<>();


        AdapterList<MateriaTema> adapterList = new AdapterList(rv_home_materias_item_lista, materiasTemas, R.layout.list_item_home_tema, new AdapterManagerList<MateriaTema>() {



            private CircleImageView civ_item_home_tema_image;
            private TextView tv_item_home_tema_name;
            private TextView tv_item_home_tema_rol;
            private TextView tv_item_home_tema_fecha;


            @Override
            public void onCreateView(View v) {

                this.civ_item_home_tema_image = v.findViewById(R.id.civ_item_home_tema_image);
                this.tv_item_home_tema_name = v.findViewById(R.id.tv_item_home_tema_name);
                this.tv_item_home_tema_rol = v.findViewById(R.id.tv_item_home_tema_rol);
                this.tv_item_home_tema_fecha = v.findViewById(R.id.tv_item_home_tema_fecha);

            }

            @Override
            public void onChangeView(MateriaTema tema, int position) {

            }
        });


        return view;
    }
}