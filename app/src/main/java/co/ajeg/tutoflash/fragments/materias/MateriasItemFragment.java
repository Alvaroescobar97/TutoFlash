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
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseMateria;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseNotificacion;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseUser;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.materia.Materia;
import co.ajeg.tutoflash.model.materia.MateriaTema;
import co.ajeg.tutoflash.model.notificacion.Notificacion;


public class MateriasItemFragment extends Fragment {

    private HomeFragment homeFragment;

    private List<MateriaTema> materiasTemas;
    private AdapterList<MateriaTema> adapterList;
    private RecyclerView rv_home_materias_item_lista;
    private TextInputLayout til_home_materias_item_buscar;
    private Button btn_home_materias_item_agregar;
    private DatabaseMateria databaseMateria;
    private Materia materia;

    public MateriasItemFragment(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        materiasTemas = new ArrayList<>();
        databaseMateria = DatabaseMateria.getInstance(homeFragment.mainActivity);
    }

    public void setCurrentMateria(Materia materia){
        this.materia = materia;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materias_item, container, false);

        this.btn_home_materias_item_agregar = view.findViewById(R.id.btn_home_materias_item_agregar);
        this.til_home_materias_item_buscar = view.findViewById(R.id.til_home_materias_item_buscar);
        this.rv_home_materias_item_lista = view.findViewById(R.id.rv_home_materias_item_lista);
        this.rv_home_materias_item_lista.setLayoutManager(new LinearLayoutManager(this.getContext()));

        this.btn_home_materias_item_agregar.setOnClickListener(this::onClickAgregarTema);


        Fragment thisFragment = this;
        this.adapterList = new AdapterList(rv_home_materias_item_lista, this.materiasTemas, R.layout.list_item_home_tema, new AdapterManagerList<MateriaTema>() {

            @Override
            public void onCreateView(View v) {
            }

            @Override
            public void onChangeView(MateriaTema tema, View view, int position) {

                ImageView iv_item_home_tema_image = view.findViewById(R.id.iv_item_home_tema_image);
                TextView tv_item_home_tema_name = view.findViewById(R.id.tv_item_home_tema_name);;
                TextView tv_item_home_tema_rol = view.findViewById(R.id.tv_item_home_tema_rol);;
                TextView tv_item_home_tema_username = view.findViewById(R.id.tv_item_home_tema_username);;
                TextView tv_item_home_tema_fecha = view.findViewById(R.id.tv_item_home_tema_fecha);;

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                Date date = new Date(tema.getDate());
                String strDate = dateFormat.format(date).toString();
                tv_item_home_tema_name.setText(tema.getTitle());
                tv_item_home_tema_rol.setText(tema.getDescripcion());
                tv_item_home_tema_fecha.setText(strDate);

                MainActivity mainActivity = homeFragment.mainActivity;

                databaseMateria.getNameUserId(tema.getAutorId(), (autor)->{
                    if(autor != null){
                        view.setOnClickListener(v->{
                            if(tema.getAutorId().equals(Autenticacion.getUser().getId())){

                                DatabaseNotificacion.getMyNotificacion(mainActivity, tema.getId(), (notificacion)->{
                                    if(notificacion != null){
                                        Fragment fragmentResult = mainActivity.notificacionFragment.getNotificacionType(notificacion);
                                        if(fragmentResult != null){
                                            FragmentUtil.replaceFragmentInMain(fragmentResult);
                                        }
                                    }
                                });

                            }else{
                                homeFragment.materiasItemOfrecerFragment.setAutorId(autor);
                                homeFragment.materiasItemOfrecerFragment.setCurrentTema(tema, materia.getName());
                                FragmentUtil.replaceFragmentInMain(homeFragment.materiasItemOfrecerFragment);
                            }

                        });

                        tv_item_home_tema_username.setText(autor.getName());
                        DatabaseUser.getImageUrlProfile(thisFragment, autor.getImage(), (urlImage)->{
                            if(urlImage != null){
                                getImageViewProfile(urlImage, iv_item_home_tema_image);
                            }
                        });

                    }else{
                        view.setOnClickListener(v->{
                            FragmentUtil.replaceFragmentInMain(homeFragment.materiasItemOfrecerFragment);
                        });
                    }
                });
            }

            private void getImageViewProfile(String urlImage, ImageView imageView) {
                Glide.with(imageView)
                        .load(urlImage)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageView);
            }
        });

        if(this.materia != null){
            this.databaseMateria.getAllSolicitudes(this.materia.getName(), (solicitudes)->{
                this.materiasTemas = solicitudes;
                this.adapterList.onUpdateData(solicitudes);
            });
        }

        int nFragments = FragmentUtil.getFragmentsNav().size();
        FragmentUtil.setOnChangeBackActivity((fragments)->{
            if(fragments.size() < nFragments){
                this.databaseMateria.stopListenerMaterias();
                this.materia = null;
            }
        });

        this.til_home_materias_item_buscar.getEditText().setOnTouchListener((v, event) -> {
            boolean stateClick = FragmentUtil.onTouchEventIconDirectionUp(event, til_home_materias_item_buscar.getEditText(), FragmentUtil.DRAWABLE_RIGHT);
            if(stateClick){
                this.onFindMateria();
            }
            return stateClick;
        });

        return view;
    }

    public void onFindMateria(){

        if(this.til_home_materias_item_buscar != null){
            String materiaString = til_home_materias_item_buscar.getEditText().getText().toString();
            if(materiaString.equals("")){
               // Toast.makeText(this.getContext(), "Todos " + this.materiasTemas.size(), Toast.LENGTH_SHORT).show();
                this.adapterList.onUpdateData(this.materiasTemas);
            }else{
               // Toast.makeText(this.getContext(), "Buscando", Toast.LENGTH_SHORT).show();
                this.databaseMateria.findMateriasTemaForName(materia.getName(), materiaString, (materiaList)->{
                    this.adapterList.onUpdateData(materiaList);
                });
            }

        }
    }

    public void onClickAgregarTema(View v){
        if(this.materia != null){
            homeFragment.materiasSolicitarFragment.setCurrentMateria(this.materia);
            FragmentUtil.replaceFragmentInMain(homeFragment.materiasSolicitarFragment);
        }
    }
}