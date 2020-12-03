package co.ajeg.tutoflash.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeaderFragment extends Fragment {

    private MainActivity appCompatActivity;

    public HeaderFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HeaderFragment newInstance() {
        HeaderFragment fragment = new HeaderFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    private ImageView btn_image_home_perfil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_header, container, false);

        btn_image_home_perfil = view.findViewById(R.id.btn_image_home_perfil);

        btn_image_home_perfil.setOnClickListener(this::onClickVerPerfil);

        return view;
    }

    private void onClickVerPerfil(View v){
        FragmentUtil.getActivity((activity)->{
            FragmentUtil.replaceFragment(R.id.fragment_container, activity.perfilFragment);
        });
    }
}