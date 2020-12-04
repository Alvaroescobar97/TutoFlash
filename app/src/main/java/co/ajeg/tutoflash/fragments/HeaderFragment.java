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

        return view;
    }

    private void getImageViewProfile(String urlImage, ImageView circleImageView) {
        Glide.with(circleImageView)
                .load(urlImage)
                .apply(RequestOptions.circleCropTransform())
                .into(circleImageView);
    }

    private void onClickVerPerfil(View v){
        FragmentUtil.getActivity((activity)->{
            FragmentUtil.replaceFragment(R.id.fragment_container, activity.perfilFragment);
        });
    }
}