package it.porting.android_is.gestioneUtente;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import it.porting.android_is.R;
import it.porting.android_is.adapter.RequestAdapter;
import it.porting.android_is.firebaseArchive.FireBaseArchive;
import it.porting.android_is.firebaseArchive.bean.RequestBean;
import it.porting.android_is.firebaseArchive.bean.UtenteBean;
import it.porting.android_is.utility.LazyInitializedSingleton;

public class ViewActivityUtente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        ActionBar actionBar = getSupportActionBar();
        Button btmod = findViewById(R.id.button);
        btmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modpage();
            }
        });
        actionBar.setBackgroundDrawable(new ColorDrawable (Color.rgb(255,153,0)));
        actionBar.setTitle("Il Mio Profilo");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String email;
            TextView emailTv;
            String password;
            Button modifica;
            email= user.getEmail();
            //inizializzo un riferimento all'oggetto che si interfaccia con firebase
            FireBaseArchive fireBaseArchive = new FireBaseArchive();

            //prelevo tutte le request da inserire nella recyclerview
            fireBaseArchive.getUserByKey(email, new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        UtenteBean utenteBean = task.getResult().toObject(UtenteBean.class);
                        String cognome,nome;
                        TextView nomeTv,cognomeTv;

                        cognome=utenteBean.getCognome();
                        nome=utenteBean.getNome();
                        nomeTv= findViewById(R.id.textView6);
                        nomeTv.setText(nome);
                        cognomeTv= findViewById(R.id.textView7);
                        cognomeTv.setText(cognome);
                    }
                    else{
                        Log.d("Errore nella query","ERRORE");
                    }
                }
            });


            emailTv= findViewById(R.id.textView8);
            emailTv.setText(email);



        } else {
            Toast.makeText(this, "Non sei loggato!", Toast.LENGTH_SHORT).show();
        }
    }

    public void modpage(){
        Intent intent = new Intent(getApplicationContext(), EditActivityUtente.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
}



