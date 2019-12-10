package it.porting.android_is.gestioneUtente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.porting.android_is.R;
import it.porting.android_is.gestioneStudente.MainActivityStudente;
import it.porting.android_is.gestioneStudente.Register;

public class LoginActivity extends AppCompatActivity {

    private Button bLogin;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvRegisterNow;
    private String email;
    private String password;
    private int flag;
    private Toast toast;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private SharedPreferences.Editor editor;
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Inizializzazione shared preferences
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit(); //editor
        //Fine inizializzazione shared preferences

        mAuth = FirebaseAuth.getInstance();
        bLogin = findViewById(R.id.bLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvRegisterNow = findViewById(R.id.register_now);
        tvRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login(){
        //verifico che l'utente abbia inserito qualcosa
        if(!String.valueOf(etEmail.getText()).equals("") && !String.valueOf(etPassword.getText()).equals("")) {
            email = String.valueOf(etEmail.getText());
            password = String.valueOf(etPassword.getText());

            //modulo autenticazione firebase
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        editor.putString("email", user.getEmail());
                        editor.commit();
                        flag = 1 ;
                        if(flag == 1){
                            Intent intent = new Intent(getApplicationContext(), MainActivityStudente.class);
                            startActivity(intent);
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        toast = Toast.makeText(getApplicationContext(), "I dati inseriti non sono corretti", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

            });
        } else {
            //se l'utente non ha inserito niente negli edit text
            toast = Toast.makeText(getApplicationContext(), "Dati non inseriti", Toast.LENGTH_LONG);
            toast.show();
        }


    }

    public void register(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
