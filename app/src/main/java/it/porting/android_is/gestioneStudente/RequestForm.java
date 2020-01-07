package it.porting.android_is.gestioneStudente;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.porting.android_is.R;
import it.porting.android_is.firebaseArchive.bean.RequestBean;
import it.porting.android_is.gestioneUtente.Guida;
import it.porting.android_is.gestioneUtente.LoginActivity;
import it.porting.android_is.gestioneUtente.ViewActivityUtente;
import it.porting.android_is.network.RetrofitSingleton;
import it.porting.android_is.utility.LazyInitializedSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestForm extends AppCompatActivity {
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private EditText et5;
    private EditText et6;
    private EditText et7;
    private EditText et8;
    private Button button;
    private TextView textView;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();


    RequestBean requestBean = new RequestBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 153, 0)));
        actionBar.setTitle("Inserisci richiesta");


        et1=findViewById(R.id.etAnno);
        et2=findViewById(R.id.etMatricola);
        et3=findViewById(R.id.etEnte);
        et4=findViewById(R.id.etRelease);
        et5=findViewById(R.id.et_scadenza);
        et6=findViewById(R.id.et_seriale);
        et7=findViewById(R.id.et_livello);
        et8=findViewById(R.id.et_cfu);
        textView=findViewById(R.id.boxContainer2);



    button=findViewById(R.id.btSendForm);
        button.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
                int seriale=Integer.parseInt(et6.getText().toString());
             int cfu=Integer.parseInt(et8.getText().toString());
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
             Date parsedDate = null;
             try {
                 parsedDate = dateFormat.parse(et4.getText().toString());
             } catch (ParseException e) {
                 e.printStackTrace();
             }

             Date parsedDate2 = null;
             try {
                 parsedDate2 = dateFormat.parse(et5.getText().toString());
             } catch (ParseException e) {
                 e.printStackTrace();
             }


             Timestamp timestamp1 = new Timestamp(parsedDate);
             Timestamp timestamp2 = new Timestamp(parsedDate2);
             requestBean.setYear(et1.getText().toString());
             requestBean.setMatricola(et2.getText().toString());
             requestBean.setEnte(et3.getText().toString());
             requestBean.setRelease_date(timestamp1);
             requestBean.setExpiry_date(timestamp2);
             requestBean.setSerial(seriale);
             requestBean.setLevel(et7.getText().toString());
             requestBean.setValidated_cfu(cfu);
             requestBean.setStato("Inviato");

             requestBean.setUser_key(LazyInitializedSingleton.getInstance().getUser().get("email").toString());

             db.collection("request").add(requestBean).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                 @Override
                 public void onSuccess(DocumentReference documentReference) {

                 }
             })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Log.w("DEBUG", "Error writing document", e);
                         }
                     });


             RetrofitSingleton.getInstance().performCreatePDF(requestBean,new Callback<Void>() {
                 @Override
                 public void onResponse(Call<Void> call, Response<Void> response) {
                     if (!response.isSuccessful()) {
                         textView.setText(("Code: " + response.code()));

                     }
                     textView.setText("ok");
                 }

                 @Override
                 public void onFailure(Call<Void> call, Throwable t) {
                     textView.setText((t.getMessage()));
                 }
             });

         }
        }
 );



    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                modpage();
                return true;
            case R.id.option2:
                guida();
                return true;
            case R.id.logout:
                logout();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    public void modpage() {
        Intent intent = new Intent(getApplicationContext(), ViewActivityUtente.class);
        startActivity(intent);
    }

    public void guida() {
        Intent intent = new Intent(getApplicationContext(), Guida.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


    public void logout() {
        //Logout dal modulo di autenticazione di firebase
        FirebaseAuth.getInstance().signOut();
        //elimino la "sessione"
        LazyInitializedSingleton lazyInitializedSingleton = LazyInitializedSingleton.getInstance();
        lazyInitializedSingleton.clearInstance();
        //ritorno alla pagina di login
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }




    }

