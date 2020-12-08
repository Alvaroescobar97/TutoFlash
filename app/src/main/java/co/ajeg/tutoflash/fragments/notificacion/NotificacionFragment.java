package co.ajeg.tutoflash.fragments.notificacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
import co.ajeg.tutoflash.firebase.database.manager.DatabaseNotificacion;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseUser;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.notificacion.Notificacion;


public class NotificacionFragment extends Fragment implements DatabaseNotificacion.OnCompleteListenerAllNotificaciones {

    public MainActivity mainActivity;
    private DatabaseNotificacion databaseNotificacion;
    private RecyclerView rv_notificaciones_lista;
    private List<Notificacion> notificacions;
    private AdapterList<Notificacion> adapterList;

    /*Fragments

     */

    public NotificacionTemaTutorFragment notificacionTemaTutorFragment;
    public NotificacionTemaCreateFragment notificacionTemaCreateFragment;
    public NotificacionTemaColaborarFragment notificacionTemaColaborarFragment;


    public NotificacionFragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity = mainActivity;
        this.databaseNotificacion = DatabaseNotificacion.getInstance(mainActivity);
        this.databaseNotificacion.getAllNotificaciones(this);

        notificacionTemaTutorFragment = new NotificacionTemaTutorFragment(this);
        notificacionTemaCreateFragment = NotificacionTemaCreateFragment.newInstance(this);
        notificacionTemaColaborarFragment = NotificacionTemaColaborarFragment.newInstance(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificacion, container, false);

        this.mainActivity.headerFragment.changeTitleHeader("Notificaciones");

        this.rv_notificaciones_lista = view.findViewById(R.id.rv_notificaciones_lista);
        this.rv_notificaciones_lista.setLayoutManager(new LinearLayoutManager(this.getContext()));

        if (this.notificacions == null) {
            this.notificacions = new ArrayList<>();
        }

        this.adapterList = new AdapterList(this.rv_notificaciones_lista, this.notificacions, R.layout.list_item_notificaciones_notificacion, new AdapterManagerList<Notificacion>() {


            private ImageView civ_item_notificaciones_notificacion_image;
            private TextView tv_item_notificaciones_notificacion_name;
            private TextView tv_item_notificaciones_notificacion_descripcion;
            private TextView tv_item_notificaciones_notificacion_fecha;


            @Override
            public void onCreateView(View v) {

                this.civ_item_notificaciones_notificacion_image = v.findViewById(R.id.iv_item_notificaciones_notificacion_image);
                this.tv_item_notificaciones_notificacion_name = v.findViewById(R.id.tv_item_notificaciones_notificacion_name);
                this.tv_item_notificaciones_notificacion_descripcion = v.findViewById(R.id.tv_item_notificaciones_notificacion_descripcion);
                this.tv_item_notificaciones_notificacion_fecha = v.findViewById(R.id.tv_item_notificaciones_notificacion_fecha);

            }

            @Override
            public void onChangeView(Notificacion notificacion, View view, int position) {

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                Date date = new Date(notificacion.getFecha());
                String strDate = dateFormat.format(date).toString();

                this.tv_item_notificaciones_notificacion_name.setText(notificacion.getTitle());
                this.tv_item_notificaciones_notificacion_descripcion.setText(notificacion.getDescripcion());
                this.tv_item_notificaciones_notificacion_fecha.setText(strDate);

                if(notificacion.getType().equals(DBROUTES.NOTIFICACION_TYPE_SOLICITUD_TUTOR)){
                    String imageUserActual = Autenticacion.getUser().getImage();
                    DatabaseUser.getImageUrlProfile(mainActivity, imageUserActual, (urlResul)->{
                        if(urlResul != null){
                            Glide.with(civ_item_notificaciones_notificacion_image)
                                    .load(urlResul)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(civ_item_notificaciones_notificacion_image);
                        }

                    });
                }

                view.setOnClickListener((v)->{
                    Fragment fragmentResult = getNotificacionType(notificacion);
                    if(fragmentResult != null){
                        FragmentUtil.replaceFragmentInMain(fragmentResult);
                    }
                });

            }

        });

        return view;
    }

    @Override
    public void onLoadAllNotificaciones(List<Notificacion> notificacions) {
        this.notificacions = notificacions;


        if (this.notificacions != null && this.adapterList != null && this.rv_notificaciones_lista != null) {
            //String id, String type, String title, String informacion, String descripcion, String imgUrl

            this.adapterList.onUpdateData(this.notificacions);
        }
    }

    public Fragment getNotificacionType(Notificacion notificacion){
        Fragment fragmentResult = null;
        if(notificacion.getType().equals(DBROUTES.NOTIFICACION_TYPE_SOLICITUD_TUTOR)){
            fragmentResult = this.notificacionTemaCreateFragment;
            this.notificacionTemaCreateFragment.setCurrentNotificacion(notificacion);
        }else if(notificacion.getType().equals(DBROUTES.NOTIFICACION_TYPE_SOLICITUD_TUTOR_DAR)){
            fragmentResult = this.notificacionTemaColaborarFragment;
            this.notificacionTemaColaborarFragment.setCurrentNotificacion(notificacion);
            this.notificacionTemaColaborarFragment.setCurrentNotificacion(notificacion);
        }
        return fragmentResult;
    }
}