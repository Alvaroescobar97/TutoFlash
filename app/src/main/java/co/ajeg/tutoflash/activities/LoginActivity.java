package co.ajeg.tutoflash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.UUID;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout userET;
    private Button loginBtn;
    private ImageView banner;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Andres

        userET = findViewById(R.id.userET);
        loginBtn = findViewById(R.id.loginBtn);
        banner = findViewById(R.id.banner);

        loginBtn.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:

                String userName = userET.getEditText().getText().toString();
                Log.e(">>>", userName);

                User user = new User(UUID.randomUUID().toString(),userName);

                CollectionReference trainerRef = db.collection("users");
                Query query = trainerRef.whereEqualTo("userName",userName);

                query.get().addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){

                                if(task.getResult().size() > 0){
                                    for(QueryDocumentSnapshot doc : task.getResult()){
                                        User dbUser = doc.toObject(User.class);
                                        launchHome(dbUser);
                                        break;
                                    }
                                }else{
                                    db.collection("users").document(user.getId()).set(user);
                                    launchHome(user);
                                }

                            }
                        }
                );


                break;
        }
    }

    public void launchHome(User user){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("myUser",user);
        startActivity(i);
    }
}