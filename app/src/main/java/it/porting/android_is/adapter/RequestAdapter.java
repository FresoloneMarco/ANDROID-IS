package it.porting.android_is.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.porting.android_is.R;

public class RequestAdapter extends RecyclerView.Adapter <RequestAdapter.ViewHolder>{

    ArrayList<String>arrayList;

    public RequestAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);

        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.idText.setText(arrayList.get(position));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        View root;
        TextView idText;
        TextView nomeText;


        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            idText = root.findViewById(R.id.idText);
            nomeText = root.findViewById(R.id.nomeText);
        }
    }





}
