package it.porting.android_is.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import it.porting.android_is.R;
import it.porting.android_is.firebaseArchive.bean.RequestBean;
import okhttp3.internal.Internal;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * Classe Adapter che consente la gestione della recyclerView nell'activity della segreteria
 */
public class RequestAdapterSegreteria extends RecyclerView.Adapter <RequestAdapterSegreteria.ViewHolder> {

    ArrayList<RequestBean> arrayList;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<String> idFields = new ArrayList<>();



    public RequestAdapterSegreteria(ArrayList<RequestBean> arrayList, ArrayList<String>idFields) {
        this.arrayList = arrayList;
        this.idFields = idFields;
    }


    /**
     * Inserisce nella recyclerview la formattazione di ogni riga
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item_segreteria, parent, false);

        return new ViewHolder(v);


    }


    /**
     * Inserisce in ogni campo il rispettivo valore
     *
     * @param holder   la recyclerview
     * @param position posizione della tupla
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        int formato = DateFormat.LONG;
        DateFormat dateFormat = DateFormat.getDateInstance(formato, Locale.ITALY);
        String dataRelease = dateFormat.format(arrayList.get(position).getRelease_date().toDate());
        String dataExpiry = dateFormat.format(arrayList.get(position).getExpiry_date().toDate());
        holder.idText.setText("ID richiesta: " + idFields.get(position));
        holder.livelloText.setText("Livello: " + arrayList.get(position).getLevel());
        holder.releaseText.setText("Rilascio: " + dataRelease);
        holder.expiryText.setText("Scadenza: " + dataExpiry);
        holder.annoText.setText("Anno: " + arrayList.get(position).getYear());
        holder.serialeText.setText("Seriale: " + Integer.toString(arrayList.get(position).getSerial()));
        holder.cfuText.setText("CFU: " + Integer.toString(arrayList.get(position).getValidated_cfu()));
        holder.utenteText.setText("Studente: " + arrayList.get(position).getUser_name() + " " + arrayList.get(position).getUser_surname());
        holder.emailText.setText("Email: " + arrayList.get(position).getUser_key());

        holder.btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = arrayList.get(position).getUser_key();
                int cfu = arrayList.get(position).getValidated_cfu();

                db.collection("utenti").document((email))
                        .update("cfu", cfu ).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("onClick", "DocumentSnapshot caricato correttamente");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Errore nel caricamento del documento", e);
                            }
                        });
            }
        });
    }




    /**
     * conteggio elementi
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    /**
     * classe "gestore" della recyclerview, in cui vengono inizializzati tutti i campi.
     */

    public class ViewHolder extends RecyclerView.ViewHolder {


        View root;
        TextView idText;
        TextView utenteText;
        TextView emailText;
        TextView livelloText;
        TextView releaseText;
        TextView expiryText;
        TextView annoText;
        TextView serialeText;
        TextView cfuText;
        Button btSend;


        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            idText = root.findViewById(R.id.idText);
            utenteText = root.findViewById(R.id.utenteText);
            emailText = root.findViewById(R.id.emailText);
            livelloText = root.findViewById(R.id.livelloText);
            releaseText = root.findViewById(R.id.releaseText);
            expiryText = root.findViewById(R.id.expiryText);
            annoText = root.findViewById(R.id.annoText);
            serialeText = root.findViewById(R.id.serialeText);
            cfuText = root.findViewById(R.id.cfuText);
            btSend = root.findViewById(R.id.btSend);


        }
    }

}