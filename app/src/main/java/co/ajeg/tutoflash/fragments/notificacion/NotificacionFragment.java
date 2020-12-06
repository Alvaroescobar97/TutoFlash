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

import java.util.ArrayList;
import java.util.List;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.adapter.AdapterList;
import co.ajeg.tutoflash.adapter.AdapterManagerList;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseNotificacion;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.notificacion.Notificacion;


public class NotificacionFragment extends Fragment implements DatabaseNotificacion.OnCompleteListenerAllNotificaciones {

    private MainActivity mainActivity;
    private DatabaseNotificacion databaseNotificacion;
    private RecyclerView rv_notificaciones_lista;
    private List<Notificacion> notificacions;
    private AdapterList<Notificacion> adapterList;

    public NotificacionFragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity = mainActivity;
        this.databaseNotificacion = DatabaseNotificacion.getInstance(mainActivity);
        this.databaseNotificacion.getAllNotificaciones(this);
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
}