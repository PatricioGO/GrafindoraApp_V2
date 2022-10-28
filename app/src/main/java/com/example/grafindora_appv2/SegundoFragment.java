package com.example.grafindora_appv2;

import android.content.Intent;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SegundoFragment.
     */
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

        listaAlertas.add(new Alertas(nombre,descripcion,hora));



        /*Alertas alertas = new Alertas(nombre,descripcion,hora);

        alertas.setNombre(nombre);
        alertas.setDescripcion(descripcion);
        alertas.setHora(hora);

        listaAlertas.add(alertas);*/

    }
}