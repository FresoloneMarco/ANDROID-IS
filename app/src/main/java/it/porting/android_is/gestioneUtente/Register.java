package it.porting.android_is.gestioneUtente;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;




import it.porting.android_is.R;

public class Register extends AppCompatActivity {

    private  EditText etNome;
    private  EditText etCognome;
    private  EditText etEmail;
    private  EditText etPassword;
    private  EditText etVPassword;
    private  RadioGroup rdgroup_Sex;
    private  Button btReg;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int flag = 0;

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


        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });



    }

    public void register(){
        final  String nome      = String.valueOf(etNome.getText());
        final  String cognome   = String.valueOf(etCognome.getText());
        final  String email     = String.valueOf(etEmail.getText());
        final  String password  = String.valueOf(etPassword.getText());
        final  String vpassword = String.valueOf(etVPassword.getText());
        final  String sex = "M";
        final  Context context = this;
        if(nome.equals("") || cognome.equals("") || email.equals("") || password.equals("") || vpassword.equals("")){
            Toast.makeText(this, "Non hai compilato tutti i campi", Toast.LENGTH_LONG).show();
        }
        else if(!password.equals(vpassword)){
            Toast.makeText(this, "I campi password non corrispondono", Toast.LENGTH_LONG).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Registrazione avvenuta con successo !", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Errore durante la registrazione !", Toast.LENGTH_LONG).show();

                            }


                        }
                    });

        }


    }
}

