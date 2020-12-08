package co.ajeg.tutoflash.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.firebase.storage.StorageFirebase;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeaderFragment extends Fragment {

    private MainActivity mainActivity;



    public HeaderFragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity = mainActivity;
    }

    // TODO: Rename and change types and number of parameters
    public static HeaderFragment newInstance(MainActivity mainActivity) {
        HeaderFragment fragment = new HeaderFragment(mainActivity);
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    private TextView tv_header_title;
    private ImageView btn_image_home_perfil;
    private ImageView btn_header_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_header, container, false);

        this.tv_header_title = view.findViewById(R.id.tv_header_title);
        this.tv_header_title.setText("Inicio");

        this.btn_header_back = view.findViewById(R.id.btn_header_back);
        this.btn_header_back.setOnClickListener(this::onClickGoToBack);

        btn_image_home_perfil = view.findViewById(R.id.btn_image_home_perfil);

        btn_image_home_perfil.setOnClickListener(this::onClickVerPerfil);

        FragmentUtil.setOnChangeFragmentNav((fragments)->{
            if(fragments.size() == 0){
                this.btn_header_back.setVisibility(View.INVISIBLE);
            }else{
                this.btn_header_back.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void onClickGoToBack(View v){
        FragmentUtil.goToBackFragment();
    }

    private void onClickVerPerfil(View v){
        FragmentUtil.getActivity((activity)->{
            if(FragmentUtil.getFragmentCurrent() != this.mainActivity.perfilFragment){
                FragmentUtil.replaceFragmentInMain(activity.perfilFragment);
            }

        });
    }

    public void changeTitleHeader(String title){
       if(this.tv_header_title != null){
           this.tv_header_title.setText(title);
       }
    }
}