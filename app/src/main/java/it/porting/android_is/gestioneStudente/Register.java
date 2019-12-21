package it.porting.android_is.gestioneStudente;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;


import java.util.HashMap;
import java.util.Map;

import it.porting.android_is.R;
import it.porting.android_is.gestioneUtente.LoginActivity;

public class Register extends AppCompatActivity {

    private  EditText etNome;
    private  EditText etCognome;
    private  EditText etEmail;
    private  EditText etPassword;
    private  EditText etVPassword;
    private  RadioGroup rdgroup_Sex;
    private ProgressBar progressBar;
    private  Button btReg;
    private  FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        etNome      = findViewById(R.id.etNome);
        etCognome   = findViewById(R.id.etCognome);
        etEmail     = findViewById(R.id.etEmail);
        etPassword  = findViewById(R.id.etPassword);
        etVPassword = findViewById(R.id.etV_password);
        rdgroup_Sex = findViewById(R.id.radio_Sex);
        btReg       = findViewById(R.id.btReg);
        progressBar = findViewById(R.id.progressBar);



        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });



    }

    public void register(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        final  String nome      = String.valueOf(etNome.getText());
        final  String cognome   = String.valueOf(etCognome.getText());
        final  String email     = String.valueOf(etEmail.getText());
        final  String password  = String.valueOf(etPassword.getText());
        final  String vpassword = String.valueOf(etVPassword.getText());
        final  String sex =  "M";
        final  Context context = this;



       if(nome.equals("") || cognome.equals("") || email.equals("") ||  password.equals("") || vpassword.equals("")){
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Toast.makeText(this, "Non hai compilato tutti i campi", Toast.LENGTH_LONG).show();
        }

       else if (sex.length()>2) {

           Toast.makeText(this, "Il campo non è stato compilato correttamente", Toast.LENGTH_SHORT).show();
       }

       else if(rdgroup_Sex.getCheckedRadioButtonId() == -1){
           progressBar.setVisibility(View.GONE);
           getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
           Toast.makeText(this, "Non hai inserito il sesso", Toast.LENGTH_LONG).show();
       }

       else if(password.length() <  8){
           progressBar.setVisibility(View.GONE);
           getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

           Toast.makeText(this, "La password non rispetta la lunghezza minima", Toast.LENGTH_LONG).show();

       }

        else if(!password.equals(vpassword)){
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            Toast.makeText(this, "I campi password non corrispondono", Toast.LENGTH_LONG).show();
        }
        else {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("nome", nome);
                                    user.put("cognome", cognome);
                                    user.put("email", email);
                                    user.put("password", password);
                                    user.put("sesso", sex);
                                    user.put("ruolo", "studente");

                                    db.collection("utenti").document(email)
                                            .set(user, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("DEBUG", "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("DEBUG", "Error writing document", e);
                                                }
                                            });
                                    progressBar.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                    Toast.makeText(context, "Registrazione avvenuta con successo !", Toast.LENGTH_LONG).show();
                                    Intent intent =  new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);

                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                    Toast.makeText(context, "Errore durante la registrazione !", Toast.LENGTH_LONG).show();

                                }


                            }
                        });

            }


        if(!nome.isEmpty()) {

            for (int i = 0; i < nome.length(); i++) {

                char ch = nome.charAt(i);

                if (ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5'
                        || ch == '6' || ch == '7' || ch == '8' || ch == '9') {

                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    Toast.makeText(context, "Errore durante la registrazione ! Il campo Nome non rispetta il formato desiderato", Toast.LENGTH_LONG).show();
                }

            }
        }

        else if(!cognome.isEmpty()) {

            for (int i = 0; i < cognome.length(); i++) {

                char ch = cognome.charAt(i);

                if (ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5'
                        || ch == '6' || ch == '7' || ch == '8' || ch == '9') {

                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    Toast.makeText(context, "Errore durante la registrazione ! Il campo Cognome non rispetta il formato desiderato", Toast.LENGTH_LONG).show();
                }

            }
        }

        else if(!email.isEmpty()) {

            String inziale = email.substring(0);


            String punto = email.substring(1);
            String centrale = email.substring(2, cognome.length());

            if (!(email.endsWith("@studenti.unisa.it"))) {

                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Toast.makeText(context, "Errore durante la registrazione ! Il campo email non rispetta il formato desiderato", Toast.LENGTH_LONG).show();


            } else  if ((!inziale.equals(nome.charAt(0)))) {

                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        Toast.makeText(context, "Errore durante la registrazione ! Il campo email non rispetta il formato desiderato", Toast.LENGTH_LONG).show();

            } else if (((!punto.equals(".")))) {

                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        Toast.makeText(context, "Errore durante la registrazione ! Il campo email non rispetta il formato desiderato", Toast.LENGTH_LONG).show();

            } else if ((!centrale.equals(cognome))) {

                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        Toast.makeText(context, "Errore durante la registrazione ! Il campo email non rispetta il formato desiderato", Toast.LENGTH_LONG).show();

            }



        }


    }
}



