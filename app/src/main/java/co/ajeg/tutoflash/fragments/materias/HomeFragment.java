package co.ajeg.tutoflash.fragments.materias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import co.ajeg.tutoflash.firebase.storage.StorageFirebase;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.chat.ChatMensaje;
import co.ajeg.tutoflash.model.chat.ChatPerson;
import co.ajeg.tutoflash.model.materia.Materia;
import co.ajeg.tutoflash.model.materia.MateriaTema;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment implements DatabaseMateria.OnCompleteListenerAllMaterias {

    private EditText et_home_busqueda;
    private DatabaseMateria databaseMateria;
    private List<Materia> materiaList;
    private AdapterList<Materia> adapterList;
    private RecyclerView rv_home_materias;
    private MainActivity mainActivity;

    public HomeFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.materiaList = new ArrayList<>();
        this.databaseMateria = DatabaseMateria.getInstance(this.mainActivity);
        this.databaseMateria.getAllMaterias(this);
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
            FragmentUtil.replaceFragmentInMain(activity.materiasSolicitarFragment);
        });

    }

    public void onFindMateria(){
        if(this.et_home_busqueda != null){
            String materiaString = et_home_busqueda.getText().toString();
            this.databaseMateria.findMateriasForName(materiaString, (materiaList)->{
                this.adapterList.onUpdateData(materiaList);
            });
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