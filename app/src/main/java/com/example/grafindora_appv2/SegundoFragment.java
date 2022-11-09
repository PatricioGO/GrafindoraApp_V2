package com.example.grafindora_appv2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SegundoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SegundoFragment extends Fragment {

    RecyclerView recyclerAlertas;
    ArrayList<Alertas> listaAlertas = new ArrayList<Alertas>();




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String nombre,descripcion,hora;
    private String mParam1;
    private String mParam2;

    public SegundoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SegundoFragment newInstance(String param1, String param2) {
        SegundoFragment fragment = new SegundoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle recepDatos = getArguments();

        if(recepDatos != null) {
            nombre = getArguments().getString("nombre");
            descripcion = getArguments().getString("descripcion");
            hora = getArguments().getString("hora");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_segundo, container, false);

        Button button = vista.findViewById(R.id.btndelete);
        listaAlertas = new ArrayList<>();
        recyclerAlertas=(RecyclerView) vista.findViewById(R.id.recyclerid);
        recyclerAlertas.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarListaAlertas();



        AlertasAdapter adapter = new AlertasAdapter(listaAlertas);
        recyclerAlertas.setAdapter(adapter);


        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = view.findViewById(R.id.btnalert);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SetAlarmaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void llenarListaAlertas() {

        try {
            SQLiteDatabase db = getContext().openOrCreateDatabase("DB_GRAFIN", Context.MODE_PRIVATE ,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS alerta(id INTEGER PRIMARY KEY AUTOINCREMENT,hora VARCHAR,descrip VARCHAR,nombre VARCHAR)");

            final Cursor c = db.rawQuery("select * from alerta",null);

            int id = c.getColumnIndex("id");
            int time = c.getColumnIndex("hora");
            int des = c.getColumnIndex("descrip");
            int nom = c.getColumnIndex("nombre");

            if(c.moveToFirst())
            {
                do{
                    Alertas alert = new Alertas();

                    alert.hora = c.getString(time);
                    alert.descripcion = c.getString(des);
                    alert.nombre = c.getString(nom);
                    alert.id = c.getInt(id);

                    listaAlertas.add(alert);
                } while(c.moveToNext());
            }
        }catch (Exception ex){ };
    }

    public void eliminar() {
            try {
                SQLiteDatabase db = getContext().openOrCreateDatabase("DB_GRAFIN", Context.MODE_PRIVATE ,null);

            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}