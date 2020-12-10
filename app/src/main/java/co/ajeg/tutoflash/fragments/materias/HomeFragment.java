package co.ajeg.tutoflash.fragments.materias;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseMateria;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseUser;
import co.ajeg.tutoflash.firebase.storage.StorageFirebase;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.materia.Materia;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment implements DatabaseMateria.OnCompleteListenerAllMaterias {

    public MainActivity mainActivity;

    private EditText et_home_busqueda;
    private static DatabaseMateria databaseMateria;
    private List<Materia> materiaList;
    private AdapterList<Materia> adapterList;
    private RecyclerView rv_home_materias;


    /*Fragments*/
    public MateriasItemFragment materiasItemFragment;
    public MateriasItemOfrecerFragment materiasItemOfrecerFragment;
    public MateriasSolicitarFragment materiasSolicitarFragment;

    public HomeFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.materiaList = new ArrayList<>();
        this.databaseMateria = DatabaseMateria.getInstance(this.mainActivity);

        this.materiasItemFragment = new MateriasItemFragment(this);
        this.materiasItemOfrecerFragment = new MateriasItemOfrecerFragment(this);
        this.materiasSolicitarFragment = new MateriasSolicitarFragment(this);
    }

    public static HomeFragment newInstance(MainActivity mainActivity) {
        HomeFragment fragment = new HomeFragment(mainActivity);
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.mainActivity.headerFragment.changeTitleHeader("Inicio");

        TextInputLayout til_home_busqueda = view.findViewById(R.id.til_home_busqueda);
        this.et_home_busqueda = til_home_busqueda.getEditText();

        this.et_home_busqueda.setOnTouchListener((v, event) -> {
            boolean stateClick = FragmentUtil.onTouchEventIconDirectionUp(event, et_home_busqueda, FragmentUtil.DRAWABLE_RIGHT);
            if(stateClick){
                this.onFindMateria();
            }
            return stateClick;
        });

        Button btn_home_agregar_materia = view.findViewById(R.id.btn_home_agregar_materia);

        this.rv_home_materias = view.findViewById(R.id.rv_home_materias);
        this.rv_home_materias.setLayoutManager(new LinearLayoutManager(this.getContext()));


        adapterList = new AdapterList(this.rv_home_materias, this.materiaList, R.layout.list_item_home_materia, new AdapterManagerList<Materia>() {


            private TextView tv_item_home_materia_name;
            private TextView tv_item_home_materia_fecha;

            @Override
            public void onCreateView(View v) {


                tv_item_home_materia_name = v.findViewById(R.id.tv_item_home_materia_name);
                tv_item_home_materia_fecha = v.findViewById(R.id.tv_item_home_materia_fecha);

            }

            @Override
            public void onChangeView(Materia materia, View view, int position) {

                ImageView iv_item_home_materia_image;
                iv_item_home_materia_image = view.findViewById(R.id.iv_item_home_materia_image);
                Log.e(">>",materia.getName());
                String nameMaterias = materia.getName().toLowerCase();
                if(nameMaterias.contains("matematicas")){
                    iv_item_home_materia_image.setImageDrawable(getResources().getDrawable(R.drawable.math,getContext().getTheme()));
                }
                Toast.makeText(getContext(), "Imagen: " +nameMaterias, Toast.LENGTH_SHORT).show();
                if(nameMaterias.contains("fisica")){
                    iv_item_home_materia_image.setImageDrawable(getResources().getDrawable(R.drawable.fisica,getContext().getTheme()));
                }
                if(nameMaterias.contains("literatura")){
                    iv_item_home_materia_image.setImageDrawable(getResources().getDrawable(R.drawable.literatura,getContext().getTheme()));
                }
                if(nameMaterias.contains("ingles")){
                    iv_item_home_materia_image.setImageDrawable(getResources().getDrawable(R.drawable.ingles,getContext().getTheme()));
                }
                if(nameMaterias.contains("programación")){
                    iv_item_home_materia_image.setImageDrawable(getResources().getDrawable(R.drawable.programacion,getContext().getTheme()));
                }if(nameMaterias.contains("sistemas")){
                    iv_item_home_materia_image.setImageDrawable(getResources().getDrawable(R.drawable.sistemas,getContext().getTheme()));
                }

                int nEntradas = materia.getnEntradas();
                tv_item_home_materia_name.setText(ucFirst(nameMaterias));
                tv_item_home_materia_fecha.setText(nEntradas + " entradas");

                view.setOnClickListener((v)->{
                    materiasItemFragment.setCurrentMateria(materia);
                    FragmentUtil.replaceFragmentInMain(materiasItemFragment);
                });

            }

            public String ucFirst(String str) {
                if (str == null || str.isEmpty()) return str;
                else return str.substring(0, 1).toUpperCase() + str.substring(1);
            }

        });

        ImageView iv_home_header_imagen = view.findViewById(R.id.iv_home_header_imagen);
        TextView tv_home_header_username = view.findViewById(R.id.tv_home_header_username);

        ProgressBar pb_home_header_imagen = view.findViewById(R.id.pb_home_header_imagen);


        User user = Autenticacion.user;
        if(user != null ){
            DatabaseUser.getImageUrlProfile(mainActivity,user.getImage(), (urlImage)->{
                if(urlImage != null){
                    this.getImageViewProfile(urlImage, iv_home_header_imagen );
                }
                pb_home_header_imagen.setVisibility(View.GONE);
            });
            tv_home_header_username.setText(user.getName());

        }

        btn_home_agregar_materia.setOnClickListener(this::addMateriaListPrincipal);

        databaseMateria.getAllMaterias(this);

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
            FragmentUtil.replaceFragmentInMain(materiasSolicitarFragment);
        });
    }

    public void onFindMateria(){
        if(this.et_home_busqueda != null){

            String materiaString = et_home_busqueda.getText().toString();
            if(materiaString.equals("")){
                this.adapterList.onUpdateData(this.materiaList);
            }else{
                Toast.makeText(mainActivity, "Click, csdksldkl", Toast.LENGTH_SHORT).show();
                this.databaseMateria.findMateriasForName(materiaString, (materiaList)->{
                    this.adapterList.onUpdateData(materiaList);
                });
            }

        }
    }

    @Override
    public void onLoadAllMaterias(List<Materia> materiaList){
        this.materiaList = materiaList;
        if(this.adapterList != null && this.materiaList != null && this.rv_home_materias != null){
            this.adapterList.onUpdateData(this.materiaList);
        }
    }
}