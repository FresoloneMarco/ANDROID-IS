package it.porting.android_is.gestioneStudente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import it.porting.android_is.R;
import it.porting.android_is.firebaseArchive.bean.RequestBean;
import it.porting.android_is.firebaseArchive.bean.UtenteBean;
import it.porting.android_is.gestioneUtente.Guida;
import it.porting.android_is.gestioneUtente.LoginActivity;
import it.porting.android_is.gestioneUtente.ViewActivityUtente;
import it.porting.android_is.network.Network;
import it.porting.android_is.utility.LazyInitializedSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class MainActivityStudente extends AppCompatActivity {


    private TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_studente);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 153, 0)));
        actionBar.setTitle("Home");
        res = findViewById(R.id.res);

        //baseUrl = vostro ip Locale con porta 3000 (la porta riservata al server node)
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.7:3000")
                .addConverterFactory(GsonConverterFactory.create()).build();
        Network network = retrofit.create(Network.class);

        RequestBean requestBean = new RequestBean();
        requestBean.setEnte("Cambridge English School");
        requestBean.setLevel("A1");
        requestBean.setSerial(1234);
        Call<Void> call = network.createPDF(requestBean);
        call.enqueue(new Callback<Void>() {
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
        });

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
