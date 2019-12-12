package it.porting.android_is.gestioneUtente;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

import it.porting.android_is.R;

public class EditActivityUtente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255,153,0)));
        actionBar.setTitle("Modifica Profilo");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email,cognome,nome,pssw;
            EditText nomeEt, cognomeEt, psswEt;
            TextView emailTv;
            nomeEt = findViewById(R.id.editText);
            nome= nomeEt.toString();
            email= user.getEmail();
            emailTv= findViewById(R.id.textView8);
            emailTv.setText(email);
            cognomeEt = findViewById(R.id.editText2);
            cognome= cognomeEt.toString();
            psswEt = findViewById(R.id.editText3);
            pssw= psswEt.toString();
           // user.updatePassword(pssw);
           //UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                 //   .setDisplayName(nome)
              //      .build();

            /*user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText((Context) this, "Modifica effettuata!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
*/
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

