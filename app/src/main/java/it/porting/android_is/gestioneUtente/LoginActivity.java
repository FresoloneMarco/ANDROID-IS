package it.porting.android_is.gestioneUtente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import it.porting.android_is.R;
import it.porting.android_is.gestioneAdmin.MainActivityAdmin;
import it.porting.android_is.gestioneSegreteria.MainActivitySegreteria;
import it.porting.android_is.gestioneStudente.MainActivityStudente;
import it.porting.android_is.gestioneStudente.Register;
import it.porting.android_is.utility.LazyInitializedSingleton;

public class LoginActivity extends AppCompatActivity {

    private Button bLogin;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvRegisterNow;
    private ProgressBar progressBar;
    private String email;
    private String password;
    private Toast toast;
    private Boolean exit = false;

    // START FIRESTORE DECLARATION
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    //END FIRESTORE DECLARATION

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        mAuth = FirebaseAuth.getInstance();
        bLogin = findViewById(R.id.bLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvRegisterNow = findViewById(R.id.register_now);
        progressBar = findViewById(R.id.progressBar);

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

        progressBar.setVisibility(View.VISIBLE);
        /*
        TOGLIERE I COMMENTI QUANDO RIUSCIREMO A RISOLVERE IL PROBLEMA DI TOCCARE 'ACCEDI' 2 VOLTE

        SERVONO PER BLOCCARE L'ACTIVITY MENTRE CI STA LA PROGRESSBAR
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        */
        //verifico che l'utente abbia inserito qualcosa
        if(String.valueOf(etEmail.getText()).equals("") && String.valueOf(etPassword.getText()).equals("")) {

            //se l'utente non ha inserito niente negli edit text
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            toast = Toast.makeText(getApplicationContext(), "Dati non inseriti", Toast.LENGTH_LONG);
            toast.show();

        } else {
            //Se l'utente ha compilato tutti i campi

            email = String.valueOf(etEmail.getText());
            password = String.valueOf(etPassword.getText());

            //modulo autenticazione firebase
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                     /* Se task.isSuccessful() ritorna true significa che l'utente è riuscito a loggare con successo
                        A questo punto quindi prendiamo dal CloudFirestore dalla collezione 'utenti' il documento che
                        ha come id l'email dell'utente appena loggato, così da poter avere più informazioni riguardanti l'utente
                        tra le quali il ruolo che ha all'interno del sistema
                      */
                        DocumentReference docRef = db.collection("utenti").document(email);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    // Facciamo il retrieve del documento e lo salviamo nel singleton, N.B: sarà salvato sottoforma di HASHMAP
                                    LazyInitializedSingleton.getInstance().setUser(document.getData());
                                    progressBar.setVisibility(View.GONE);
                                    redirect();

                                }

                                else{
                                    progressBar.setVisibility(View.GONE);
                                    toast = Toast.makeText(getApplicationContext(), "I dati inseriti non sono stati caricati in sessione", Toast.LENGTH_LONG);
                                    toast.show();

                                }
                            }
                        });
                    } else {
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        toast = Toast.makeText(getApplicationContext(), "I dati inseriti non sono corretti", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

            });
        }


    }

    public void register(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


    public void redirect(){
        // INZIO A VERIFICARE QUALE ACTIVITY LANCIARE DOPO IL LOGIN

                            /*
                            PRENDO IL DOCUMENTO DAL SINGLETON INSTANZIATO AL MOMENTO DEL LOGIN

                            CASO 1 : lazyInizializedSingleton.getInstance().getUser().get("ruolo") restituisce un utente con ruolo "studente"
                            CASO 2 : lazyInizializedSingleton.getInstance().getUser().get("ruolo") restituisce un utente con ruolo "segretario"
                            CASO 3 : lazyInizializedSingleton.getInstance().getUser().get("ruolo") restituisce un utente con ruolo "admin"

                            */


        // START CASO 1
        if(String.valueOf(LazyInitializedSingleton.getInstance().getUser().get("ruolo")).equals("studente")){
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            Intent intent = new Intent(getApplicationContext(), MainActivityStudente.class);
            startActivity(intent);
        }
        // END CASO 1

        // START CASO 2
        else if(String.valueOf(LazyInitializedSingleton.getInstance().getUser().get("ruolo")).equals("segretario")){
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            Intent intent = new Intent(getApplicationContext(), MainActivitySegreteria.class);
            startActivity(intent);
        }
        //END CASO 2

        // START CASO 3
        else if(String.valueOf(LazyInitializedSingleton.getInstance().getUser().get("ruolo")).equals("admin")){
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            Intent intent = new Intent(getApplicationContext(), MainActivityAdmin.class);
            startActivity(intent);
        }
        //END CASO 3
    }




    @Override
    public void onBackPressed() {

        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        moveTaskToBack(false);

    }
}
