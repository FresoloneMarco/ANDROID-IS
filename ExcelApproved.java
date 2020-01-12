package it.porting.android_is.gestioneAdmin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

import it.porting.android_is.R;
import it.porting.android_is.adapter.RequestAdapterAdmin;
import it.porting.android_is.firebaseArchive.FireBaseArchive;
import it.porting.android_is.firebaseArchive.bean.RequestBean;
import it.porting.android_is.gestioneUtente.Guida;
import it.porting.android_is.gestioneUtente.ViewActivityUtente;

public class ExcelApproved extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private FireBaseArchive fireBaseArchive;
    private RequestAdapterAdmin requestAdapterAdmin;
    private ArrayList<RequestBean> requestBeans = new ArrayList<>();

    Button btApprova;
    Button btRifiuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Richieste Approvate");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 153, 0)));


        btApprova=findViewById(R.id.btApprova);
        btRifiuta=findViewById(R.id.btRifiuta);
        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        requestBeans=(ArrayList<RequestBean>)getIntent().getSerializableExtra("approvata");


                    requestAdapterAdmin = new RequestAdapterAdmin(requestBeans);
                     removeItem(requestAdapterAdmin);


                    recyclerView.setAdapter(requestAdapterAdmin);
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
        }
        return super.onOptionsItemSelected(item);

    }


    //trasferisce il controllo alla pagina di visualizzazione del profilo
    public void modpage() {
        Intent intent = new Intent(getApplicationContext(), ViewActivityUtente.class);
        startActivity(intent);
    }

    //visualizza la guida utente
    public void guida() {
        Intent intent = new Intent(getApplicationContext(), Guida.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu_admin, menu);

        return true;
    }


    private void removeItem(RequestAdapterAdmin itemView){
        btApprova.setEnabled(false);
        btRifiuta.setEnabled(false);

    }

}