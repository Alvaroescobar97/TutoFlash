package co.ajeg.tutoflash.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.wear.widget.CircledImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.firebase.storage.StorageFirebase;
import co.ajeg.tutoflash.fragments.util.FragmentUtil;
import co.ajeg.tutoflash.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

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

        ImageView civ_home_header_imagen = view.findViewById(R.id.civ_home_header_imagen);
        TextView tv_home_header_username = view.findViewById(R.id.tv_home_header_username);

        User user = Autenticacion.user;



        if(user != null && user.getImage().equals("") == false){

            if (user.getImage().contains(DBROUTES.USERS_IMAGES)){

                StorageFirebase.gerUrlFile(this.getActivity(), new String[]{user.getImage()}, (url)->{
                    Toast.makeText(this.getActivity(), "" + url, Toast.LENGTH_SHORT).show();
                    this.getImageViewProfile(url,civ_home_header_imagen );
                });

            }else{
                String urlImage = user.getImage();
                this.getImageViewProfile(urlImage,civ_home_header_imagen );
            }
        }





        return view;
    }

    private void getImageViewProfile(String urlImage, ImageView circleImageView){

        Glide.with(circleImageView).load(urlImage).into(circleImageView);

        Toast.makeText(this.getActivity(), "url:¨ "+urlImage, Toast.LENGTH_SHORT).show();

        /*
          ImageView imageView = new ImageView(this.getActivity());
        Glide.with(this.getContext())
                .asBitmap()
                .load(urlImage)
                .centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                imageView.setImageBitmap(resource);
                circleImageView.setImageBitmap(resource);
                //circleImageView.setBackground(imageView.getDrawable());
            }
        });

         */
    }

    private void onClickVerPerfil(View v){
        FragmentUtil.getActivity((activity)->{
            FragmentUtil.replaceFragment(R.id.fragment_container, activity.perfilFragment);
        });
    }
}