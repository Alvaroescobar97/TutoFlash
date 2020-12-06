package co.ajeg.tutoflash.fragments.notificacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificacionTemaColaborarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionTemaColaborarFragment extends Fragment {

    private MainActivity mainActivity;
    private ImageView iv_notificacion_tema_colaborar_image;
    private TextView tv_notificacion_tema_colaborar_tema;
    private  TextView tv_notificacion_tema_colaborar_usuario;
    private TextView tv_notificacion_tema_colaborar_descripcion;
    private TextView tv_notificacion_tema_colaborar_tiempo;
    private Button btn_notificacion_tema_colaborar_desertar;

    public NotificacionTemaColaborarFragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity = mainActivity;
    }

    public static NotificacionTemaColaborarFragment newInstance(MainActivity mainActivity) {
        NotificacionTemaColaborarFragment fragment = new NotificacionTemaColaborarFragment(mainActivity);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_notificacion_tema_colaborar, container, false);

        this.iv_notificacion_tema_colaborar_image = view.findViewById(R.id.iv_notificacion_tema_colaborar_image);
        this.tv_notificacion_tema_colaborar_tema = view.findViewById(R.id.tv_notificacion_tema_colaborar_tema);
        this.tv_notificacion_tema_colaborar_usuario = view.findViewById(R.id.tv_notificacion_tema_colaborar_usuario);
        this.tv_notificacion_tema_colaborar_descripcion = view.findViewById(R.id.tv_notificacion_tema_colaborar_descripcion);
        this.tv_notificacion_tema_colaborar_tiempo = view.findViewById(R.id.tv_notificacion_tema_colaborar_tiempo);
        this.btn_notificacion_tema_colaborar_desertar = view.findViewById(R.id.btn_notificacion_tema_colaborar_desertar);


        return view;
    }
}