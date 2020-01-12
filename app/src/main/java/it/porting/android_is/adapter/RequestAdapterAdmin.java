package it.porting.android_is.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.BundleCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.grpc.android.BuildConfig;
import it.porting.android_is.R;
import it.porting.android_is.firebaseArchive.bean.RequestBean;
import it.porting.android_is.gestioneAdmin.MainActivityAdmin;

import static androidx.core.content.ContextCompat.createDeviceProtectedStorageContext;
import static androidx.core.content.ContextCompat.startActivity;


/**
 * Classe Adapter che consente la gestione della recyclerView nell'activity della segreteria
 */
public class RequestAdapterAdmin extends RecyclerView.Adapter <RequestAdapterAdmin.ViewHolder>{

    ArrayList<RequestBean> arrayList;
    Context context;


    public RequestAdapterAdmin(ArrayList<RequestBean> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }




    /**
     * Inserisce nella recyclerview la formattazione di ogni riga
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item_admin, parent, false);
        return new ViewHolder(v);


    }


    /**
     * Inserisce in ogni campo il rispettivo valore
     * @param holder la recyclerview
     * @param position posizione della tupla
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        int formato = DateFormat.LONG;


        DateFormat dateFormat = DateFormat.getDateInstance(formato, Locale.ITALY);
        String dataRelease = dateFormat.format(arrayList.get(position).getRelease_date().toDate());
        String dataExpiry = dateFormat.format(arrayList.get(position).getExpiry_date().toDate());
        holder.idText.setText("ID richiesta: "  + Integer.toString(arrayList.get(position).getId()));
        holder.livelloText.setText("Livello: " +  arrayList.get(position).getLevel());
        holder.releaseText.setText("Rilascio: " + dataRelease);
        holder.expiryText.setText("Scadenza: " + dataExpiry);
        holder.annoText.setText("Anno: " + arrayList.get(position).getYear());
        holder.serialeText.setText("Seriale: " + Integer.toString(arrayList.get(position).getSerial()));
        holder.cfuText.setText("CFU: " + Integer.toString(arrayList.get(position).getValidated_cfu()));
        holder.utenteText.setText("Studente: " + arrayList.get(position).getUser_name() + " " + arrayList.get(position).getUser_surname());
        holder.emailText.setText("Email: " + arrayList.get(position).getUser_key());
        holder.enteText.setText("Ente : " + arrayList.get(position).getEnte());
        holder.statoText.setText("Stato : " + arrayList.get(position).isStato());

        //Siti web enti
        holder.enteText.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                if(arrayList.get(position).getEnte().equals("Cambridge Assessment English")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cambridgeenglish.org/it/"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);

                }

                else if(arrayList.get(position).getEnte().equals("City and Guilds (Pitman)")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cityandguilds.com/"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);

                }

                else if(arrayList.get(position).getEnte().equals("Edexcel /Pearson Ltd")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://it.pearson.com/certificazioni-pearson/international-gcse.html"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);
                }

                else if(arrayList.get(position).getEnte().equals("Educational Testing Service (ETS)")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ef-italia.it/"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);
                }

                else if(arrayList.get(position).getEnte().equals("English Speaking Board (ESB)")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.esbitaly.org/"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);
                }

                else if(arrayList.get(position).getEnte().equals("International English Language Testing System (IELTS)")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ielts.org/"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);
                }

                else if(arrayList.get(position).getEnte().equals("Pearson - LCCI")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://it.pearson.com/certificazioni-pearson.html"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);
                }

                else if(arrayList.get(position).getEnte().equals("Pearson - EDI")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://qualifications.pearson.com/en/about-us/qualification-brands/edi.html"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);
                }

                else if(arrayList.get(position).getEnte().equals("Trinity College London (TCL)")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.trinitycollege.it/"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);
                }

                else if(arrayList.get(position).getEnte().equals("Department of English, Faculty of Arts - University of Malta")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.um.edu.mt/"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);
                }

                else if(arrayList.get(position).getEnte().equals("NQAI - ACELS")){

                    Intent siteEnte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.acels.ie/"));
                    siteEnte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, siteEnte, null);
                }

            }

        });
    }


    /**
     * conteggio elementi
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
        TextView enteText;
        TextView statoText;






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
            enteText = root.findViewById(R.id.enteText);
            statoText = root.findViewById(R.id.statoText);




        }
    }





}
