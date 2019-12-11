package it.porting.android_is.firebaseArchive;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.porting.android_is.firebaseArchive.bean.RequestBean;


public class FireBaseArchive {

    FirebaseFirestore db;

    public FireBaseArchive(){
        db = FirebaseFirestore.getInstance();
    }

    public ArrayList<RequestBean> getAllRequests(){

        CollectionReference collectionReference = db.collection("request");
        final ArrayList <RequestBean> requestBeans = new ArrayList<RequestBean>();
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot req : task.getResult()){
                        RequestBean requestBean = req.toObject(RequestBean.class);
                        System.out.println("inarchive" + requestBean.toString());
                        requestBeans.add(requestBean);

                    }
                }
                else{
                    Log.d("Errore nella query","ERRORE");
                }
            }
        });
        return requestBeans;

    }

}
