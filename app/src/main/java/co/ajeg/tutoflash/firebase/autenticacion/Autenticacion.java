package co.ajeg.tutoflash.firebase.autenticacion;

import android.content.Intent;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import co.ajeg.tutoflash.R;
import co.ajeg.tutoflash.activities.LoginActivity;
import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.activities.PreLogin;
import co.ajeg.tutoflash.firebase.database.Database;
import co.ajeg.tutoflash.firebase.database.OnCompleteListenerDatabase;
import co.ajeg.tutoflash.model.User;

public class Autenticacion {

    private AppCompatActivity appCompatActivity;
    private GoogleSignInClient googleSignInClient;
    private int RC_SIGN_IN = 1;
    FirebaseAuth auth;

    static public User user;

    public Autenticacion(AppCompatActivity appCompatActivity){
        this.appCompatActivity = appCompatActivity;
        this.auth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.appCompatActivity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this.appCompatActivity, gso);

        Database.getCurrentUser(user ->{
          if(user != null){
              if(!appCompatActivity.getClass().getSimpleName().equals(PreLogin.class.getSimpleName())){
                  goToMainActivity();
              }

          }else{
              if(!appCompatActivity.getClass().getSimpleName().equals(LoginActivity.class.getSimpleName())){
                  goToLoginActivity();
              }
          }
        });
    }

    public void goToMainActivity(){
        this.appCompatActivity.runOnUiThread(()->{
            Intent intent = new Intent(this.appCompatActivity, MainActivity.class);
            this.appCompatActivity.startActivity(intent);
        });
    }

    public void goToLoginActivity(){
        this.appCompatActivity.runOnUiThread(()->{
            Intent intent = new Intent(this.appCompatActivity, LoginActivity.class);
            this.appCompatActivity.startActivity(intent);
        });
    }

    public static void logout(){
        user = null;
        FirebaseAuth.getInstance().signOut();
    }

    public void loginWithGoogle(){
        this.appCompatActivity.runOnUiThread(()->{

            Intent signInIntent = this.googleSignInClient.getSignInIntent();
            this.appCompatActivity.startActivityForResult(signInIntent, this.RC_SIGN_IN);
        });
    }


    public void login(String email, String pass, OnCompleteListenerUser onCompleteListenerUser){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        String id = task.getResult().getUser().getUid();
                        Database.getUserDatabase(id, onCompleteListenerUser);
                    }else{
                        onCompleteListenerUser.onLoad(null);
                    }
                });
    }

    static public void registro(String email, String pass, User user){
        registro(email, pass, user, null);
    }

    static public void registro(String email, String pass, User user, OnCompleteListenerUser onCompleteListenerUser){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        user.setId(task.getResult().getUser().getUid());
                        Database.createUser(user, onCompleteListenerUser);
                    }else{
                        if(onCompleteListenerUser != null){
                            onCompleteListenerUser.onLoad(null);
                        }
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, OnCompleteListenerUser onCompleteListenerUser) {
        this.appCompatActivity.runOnUiThread(()->{
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                if (task.isSuccessful()) {
                    try {
                        // Google Sign In was successful, authenticate with Firebase
                        GoogleSignInAccount account = task.getResult(ApiException.class);

                        firebaseAuthWithGoogle(account.getIdToken(), onCompleteListenerUser);
                    } catch (ApiException e) {
                        // Google Sign In fallido, actualizar GUI
                        onCompleteListenerUser.onLoad(null);
                    }
                } else {
                    Toast.makeText(this.appCompatActivity, "Ocurrio un error. " + task.getException().toString(),
                            Toast.LENGTH_LONG).show();
                    onCompleteListenerUser.onLoad(null);
                }
            }
        });

    }

    private void firebaseAuthWithGoogle(String idToken, OnCompleteListenerUser onCompleteListenerUser) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        this.auth.signInWithCredential(credential)
                .addOnCompleteListener(this.appCompatActivity, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            String name= user.getDisplayName();
                            String email = user.getEmail();
                            String carrera = "Estudiante";

                            User usuario = new User(user.getUid(), name, email, carrera);
                            Database.createUser(usuario);

                            onCompleteListenerUser.onLoad(usuario);
                        } else {

                            onCompleteListenerUser.onLoad(null);

                        }
                });
    }

    public void getCurrentUser(OnCompleteListenerUser onCompleteListenerUser){
        Database.getCurrentUser(onCompleteListenerUser);
    }

    public interface OnCompleteListenerUser {
        public void onLoad(User user);
    }

    public static User getUser() {
        return user;
    }
}
