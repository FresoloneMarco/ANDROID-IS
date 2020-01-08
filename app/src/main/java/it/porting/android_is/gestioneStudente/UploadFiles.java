package it.porting.android_is.gestioneStudente;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

import it.porting.android_is.R;
import it.porting.android_is.gestioneUtente.ViewActivityUtente;

public class UploadFiles extends AppCompatActivity implements View.OnClickListener {

    private static final int PICKFILE_REQUEST_CODE = 1024;
    private static final int PICKFILE_REQUEST_CODE2 = 1025;
    private Button scegli,scegli2,upload;
    private TextView text,text2;
    private Uri filepath,filepath2;
    private StorageReference storageReference;
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploads);
        storageReference= FirebaseStorage.getInstance().getReference();
        scegli=findViewById(R.id.chose);
        scegli2= findViewById(R.id.chose2);
        text=findViewById(R.id.testo1);
        text2=findViewById(R.id.testo2);
        upload=findViewById(R.id.butt);

        scegli.setOnClickListener(this);

        scegli2.setOnClickListener(this);



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
                Intent intent = new Intent(getApplicationContext(), MainActivityStudente.class);
                startActivity(intent);

            }
        });




    }


    @Override
    public void onClick(View view) {
        if(view==scegli){
            filecho();
        } else if(view==scegli2){
            filecho2();
        }

    }


    public void upload(){
        if(filepath!=null && filepath2!=null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Caricamento in corso...");
            progressDialog.show();

            String nomeF = "Certificato_"+user.getEmail() ;
            StorageReference Sr = storageReference.child(nomeF);
            Sr.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Caricamento completato", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress= (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage((int) progress + "% Caricati..");
                        }
                    })
            ;

            String nomeF2 = "Richiesta_Firmata_"+user.getEmail() ;
            StorageReference Sr2 = storageReference.child(nomeF2);
            Sr2.putFile(filepath2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Caricamento completato", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress= (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage((int) progress + "% Caricati..");
                        }
                    })
            ;
        }else{

        }

    }

    public void filecho(){
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleziona il pdf"),PICKFILE_REQUEST_CODE);
    }

    public void filecho2(){
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleziona il pdf"),PICKFILE_REQUEST_CODE2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICKFILE_REQUEST_CODE && resultCode==RESULT_OK && data != null && data.getData() != null){
            filepath = data.getData();
            text.setText(filepath.toString());
        }
        else{
            filepath2=data.getData();
            text2.setText(filepath2.toString());
        }
    }


}
