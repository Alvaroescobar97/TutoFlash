package co.ajeg.tutoflash.fragments.materias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.firebase.storage.StorageFirebase;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
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

        TextView tv_header_title = this.getActivity().findViewById(R.id.tv_header_title);
        tv_header_title.setText("Inicio");

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

        ImageView iv_home_header_imagen = view.findViewById(R.id.iv_home_header_imagen);
        TextView tv_home_header_username = view.findViewById(R.id.tv_home_header_username);

        User user = Autenticacion.user;

        if(user != null ){
            if(user.getImage().equals("") == false){

                if (user.getImage().contains(DBROUTES.USERS_IMAGES)){

                    StorageFirebase.gerUrlFile(this.getActivity(), new String[]{user.getImage()}, (url)->{
                        Toast.makeText(this.getActivity(), "" + url, Toast.LENGTH_SHORT).show();
                        this.getImageViewProfile(url, iv_home_header_imagen );
                    });

                }else{
                    String urlImage = user.getImage();
                    this.getImageViewProfile(urlImage, iv_home_header_imagen );
                }
            }

            tv_home_header_username.setText(user.getName());
        }


        btn_home_agregar_materia.setOnClickListener(this::addMateriaListPrincipal);

        return view;
    }

    private void getImageViewProfile(String urlImage, ImageView circleImageView) {
        Glide.with(circleImageView)
                .load(urlImage)
                .apply(RequestOptions.circleCropTransform())
                .into(circleImageView);
    }


    public void addMateriaListPrincipal(View v){
        FragmentUtil.getActivity(activity->{
            FragmentUtil.replaceFragment(R.id.fragment_container, activity.materiasSolicitarFragment);
        });

    }
}