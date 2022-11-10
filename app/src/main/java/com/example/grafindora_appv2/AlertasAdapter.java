package com.example.grafindora_appv2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
            holder.txtDescript.setText(alertas.get(position).getDescripcion());
            holder.btnDelete.setOnClickListener(view -> {
                try {
                    SQLiteDatabase db = view.getContext().openOrCreateDatabase("DB_GRAFIN", Context.MODE_PRIVATE ,null);
                    String sql = "delete from alerta where id=?";
                    SQLiteStatement statement = db.compileStatement(sql);
                    statement.bindString(1,String.valueOf(alertas.get(position).getId()));
                    statement.executeUpdateDelete();

                    final Cursor c = db.rawQuery("select * from alerta", null);

                    int id = c.getColumnIndex("id");
                    int time = c.getColumnIndex("hora");
                    int des = c.getColumnIndex("descrip");
                    int nom = c.getColumnIndex("nombre");


                    if(c.moveToFirst())
                    {
                        alertas.clear();
                        do{
                            Alertas alert = new Alertas();

                            alert.hora = c.getString(time);
                            alert.descripcion = c.getString(des);
                            alert.nombre = c.getString(nom);
                            alert.id = c.getInt(id);

                            alertas.add(alert);
                        } while(c.moveToNext());

                        notifyDataSetChanged();
                    }
                }catch (Exception ex){
                    Log.d("DELETE", ex.getMessage());
                };



        });


        }

    @Override
    public int getItemCount() {
        return alertas.size();
    }

    public class AlertasViewHolder extends RecyclerView.ViewHolder{
            TextView txtNombre,txtDescript,txtHora;
            ImageButton btnDelete;

            public AlertasViewHolder(View v){
                super(v);
                txtHora= (TextView) v.findViewById(R.id.tvhoracard);
                txtNombre = (TextView) v.findViewById(R.id.tvnombrecard);
                txtDescript = (TextView) v.findViewById(R.id.tvdescriptcard);
                btnDelete = (ImageButton) v.findViewById(R.id.btndelete);
            }
    }

}
