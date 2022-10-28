package com.example.grafindora_appv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlertasAdapter extends RecyclerView.Adapter<AlertasAdapter.AlertasViewHolder> {


        ArrayList<Alertas> alertas;

        public AlertasAdapter(ArrayList<Alertas> alertas){
            this.alertas = alertas;
        }
    @NonNull
    @Override
    public AlertasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alertas_card,null,false);

            return new AlertasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertasViewHolder holder, int position) {
            holder.txtHora.setText(alertas.get(position).getHora());
            holder.txtNombre.setText(alertas.get(position).getNombre());
            holder.txtDescript.setText(alertas.get(position).getDescripcion());}

    @Override
    public int getItemCount() {
        return alertas.size();
    }

    public class AlertasViewHolder extends RecyclerView.ViewHolder{
            TextView txtNombre,txtDescript,txtHora;

            public AlertasViewHolder(View v){
                super(v);
                txtHora= (TextView) v.findViewById(R.id.tvhoracard);
                txtNombre = (TextView) v.findViewById(R.id.tvnombrecard);
                txtDescript = (TextView) v.findViewById(R.id.tvdescriptcard);
            }
    }
}
