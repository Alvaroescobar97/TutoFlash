package co.ajeg.tutoflash.fragments.notificacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.firebase.database.manager.DatabaseNotificacion;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.notificacion.Notificacion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificacionItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionItemFragment extends Fragment {

    private Notificacion notificacion;
    private NotificacionFragment notificacionFragment;

    private Button btn_notificacion_item_eliminar;

    public NotificacionItemFragment(NotificacionFragment notificacionFragment) {
        // Required empty public
        this.notificacionFragment = notificacionFragment;
    }

    public static NotificacionItemFragment newInstance(NotificacionFragment notificacionFragment) {
        NotificacionItemFragment fragment = new NotificacionItemFragment(notificacionFragment);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificacion_item, container, false);

        this.btn_notificacion_item_eliminar = view.findViewById(R.id.btn_notificacion_item_eliminar);
        this.btn_notificacion_item_eliminar.setOnClickListener(this::onClickEliminarNotificacion);

        return view;
    }

    public void onClickEliminarNotificacion(View v){
        if(this.notificacion != null){
            DatabaseNotificacion.eliminarNotificacionId(this, notificacion, (no)->{
                if(no != null){
                    FragmentUtil.goToBackFragment();
                }else{

                }
            });
        }
    }

    public void setCurrentNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }
}