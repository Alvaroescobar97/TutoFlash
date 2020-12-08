package co.ajeg.tutoflash.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import co.ajeg.tutoflash.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalificacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalificacionFragment extends Fragment {

    private ImageView iv_calificacion_image;
    private TextView tv_calificacion_usuario;
    private TextView tv_calificacion_rol;
    private RatingBar rb_calificacion_calificacion;
    private Button btn_calificacion_calificar;
    private Button btn_calificacion_cancelar;

    public CalificacionFragment() {
        // Required empty public constructor
    }

    public static CalificacionFragment newInstance() {
        CalificacionFragment fragment = new CalificacionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calificacion, container, false);

        this.iv_calificacion_image = view.findViewById(R.id.iv_calificacion_image);
        this.tv_calificacion_usuario = view.findViewById(R.id.tv_calificacion_usuario);
        this.tv_calificacion_rol = view.findViewById(R.id.tv_calificacion_rol);
        this.rb_calificacion_calificacion = view.findViewById(R.id.rb_calificacion_calificacion);
        this.btn_calificacion_calificar = view.findViewById(R.id.btn_calificacion_calificar);
        this.btn_calificacion_cancelar = view.findViewById(R.id.btn_calificacion_cancelar);




        return view;
    }
}