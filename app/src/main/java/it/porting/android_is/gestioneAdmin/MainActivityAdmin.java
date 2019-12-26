package it.porting.android_is.gestioneAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.porting.android_is.R;
import it.porting.android_is.adapter.RequestAdapterAdmin;
import it.porting.android_is.firebaseArchive.FireBaseArchive;
import it.porting.android_is.firebaseArchive.bean.RequestBean;
import it.porting.android_is.gestioneUtente.Guida;
import it.porting.android_is.gestioneUtente.LoginActivity;
import it.porting.android_is.gestioneUtente.ViewActivityUtente;
import it.porting.android_is.utility.LazyInitializedSingleton;

import static android.graphics.pdf.PdfDocument.*;

public class MainActivityAdmin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RequestAdapterAdmin requestAdapterAdmin;
    private FireBaseArchive fireBaseArchive;
    private ArrayList<RequestBean> requestBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home Admin");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255,153,0)));


        //individuo la recyclerview
        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        //inizializzo un riferimento all'oggetto che si interfaccia con firebase
        fireBaseArchive = new FireBaseArchive();
        //prelevo tutte le request da inserire nella recyclerview
        fireBaseArchive.getAllRequests(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    //Se il task ha successo, salvo ogni "tupla" all'interno dell ArrayList
                    for(QueryDocumentSnapshot req : task.getResult()){
                        RequestBean requestBean = req.toObject(RequestBean.class);
                        requestBeans.add(requestBean);
                    }

                    requestAdapterAdmin = new RequestAdapterAdmin(requestBeans);
                    recyclerView.setAdapter(requestAdapterAdmin);
                }
                else{
                    Log.d("Errore nella query","ERRORE");
                }
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.option1:  modpage();
                return true;
            case R.id.option2:  guida();
                return true;
            case R.id.logout: logout();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    public void modpage(){
        Intent intent = new Intent(getApplicationContext(), ViewActivityUtente.class);
        startActivity(intent);
    }

    public void guida(){
        Intent intent = new Intent(getApplicationContext(), Guida.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        return true;
    }


    public void logout(){
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
