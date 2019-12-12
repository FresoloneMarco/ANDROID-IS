package it.porting.android_is.gestioneUtente;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.porting.android_is.R;

public class ViewActivityUtente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable (Color.rgb(255,153,0)));
        actionBar.setTitle("Il Mio Profilo");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email,cognome,nome;
            TextView emailTv,nomeTv,cognomeTv;
            String password;
            Button modifica;
            email= user.getEmail();
            nome= user.getDisplayName();

            emailTv= findViewById(R.id.textView8);
            emailTv.setText(email);
            nomeTv= findViewById(R.id.textView6);
            nomeTv.setText(nome);
            cognomeTv= findViewById(R.id.textView7);

        } else {
            Toast.makeText(this, "Non sei loggato!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
}
