package it.porting.android_is.gestioneStudente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import it.porting.android_is.R;
import it.porting.android_is.gestioneUtente.Guida;
import it.porting.android_is.gestioneUtente.LoginActivity;
import it.porting.android_is.gestioneUtente.ViewActivityUtente;
import it.porting.android_is.utility.LazyInitializedSingleton;
import it.porting.android_is.utility.MyDialogFragment;

public class MainActivityStudente extends AppCompatActivity {


    private TextView res;
    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_studente);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 153, 0)));
        actionBar.setTitle("Home");
        res = findViewById(R.id.res);

        //Inizializzazione shared preferences ed editor, saranno utilizzate per verificare
        //se l'utente ha associato l'account all'impronta digitale
        preferences = this.getSharedPreferences(
                "myPref", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //Controlliamo se fingerSaved è uguale a 0, se è 0 vuol dire che l'utente non ha
        //associato la sua impronta e visualizziamo quindi il dialogFragment dove gli chiediamo se
        //vuole memorizzarla o meno
        if(preferences.getInt("fingerSaved", 0) == 0){
            MyDialogFragment dialogFragment = new MyDialogFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            dialogFragment.show(ft, "dialog");
        }


        //baseUrl = vostro ip Locale con porta 3000 (la porta riservata al server node)


       /* RequestBean requestBean = new RequestBean();
        requestBean.setEnte("Cambridge English School");
        requestBean.setLevel("A1");
        requestBean.setSerial(1234);
        requestBean.setUser_key(LazyInitializedSingleton.getInstance().getUser().get("email").toString());

        RetrofitSingleton.getInstance().performCreatePDF(requestBean,new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    res.setText(("Code: " + response.code()));
                }
                res.setText("ok");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                res.setText((t.getMessage()));
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                modpage();
                return true;

            case R.id.option2:
                reqForm();
                return true;

            case R.id.option3:
                guida();
                return true;

            case R.id.option4:
                downl();
                return true;

            case R.id.option5:
                upl();
                return true;

            case R.id.logout:
                logout();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    public void downl(){
        Intent intent = new Intent(getApplicationContext(), DownloadPDF.class);
        startActivity(intent);
    }

    public void upl(){
        Intent intent = new Intent(getApplicationContext(), UploadFiles.class);
        startActivity(intent);
    }

    public void modpage() {
        Intent intent = new Intent(getApplicationContext(), ViewActivityUtente.class);
        startActivity(intent);
    }

    public void reqForm() {
        Intent intent = new Intent(getApplicationContext(), UploadFiles.RequestForm.class);
        startActivity(intent);
    }

    public void guida() {
        Intent intent = new Intent(getApplicationContext(), Guida.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu_studente, menu);

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
