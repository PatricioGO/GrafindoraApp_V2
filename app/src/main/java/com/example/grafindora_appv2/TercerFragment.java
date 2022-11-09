package com.example.grafindora_appv2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TercerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class TercerFragment extends Fragment {

    private Button btnform,btnform2;
    private TextInputEditText nombre;
    private TextInputEditText edad;
    private Spinner spraza;
    private RadioButton sexHem,sexMach;
    private String sexo;

    String[] raza = {"Labrador","Poodle","Bulldog","Golden Retriever", "Pastor Aleman", "Bulldog Frances", "Beagle","Rottweiler","otro"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TercerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TercerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TercerFragment newInstance(String param1, String param2) {

        TercerFragment fragment = new TercerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tercer, container, false);

        //Definicion del Spinner
        Spinner spinner = view.findViewById(R.id.spnrazaform) ;
        ArrayAdapter ad = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,raza);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        //Instancia de objetos en las variables declaradas
        nombre = (TextInputEditText) view.findViewById(R.id.tvnombreform);
        edad = (TextInputEditText) view.findViewById(R.id.tvedadform);
        spraza = (Spinner) view.findViewById(R.id.spnrazaform);
        btnform = (Button) view.findViewById(R.id.btnform);
        btnform2 = (Button) view.findViewById(R.id.btnform2);
        sexHem = (RadioButton) view.findViewById(R.id.rdbthembra);
        sexMach = (RadioButton) view.findViewById(R.id.rdbtmacho);


        //click del boton registrar
        btnform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nombre.getText().toString().isEmpty() || !edad.getText().toString().isEmpty() ){

                    //Checkeando la seleccion del radio buton
                    if (sexHem.isChecked()==true){
                        sexo = "Hembra";
                    }
                    else if (sexMach.isChecked()){
                        sexo = "Macho";
                    }

                    insertar();


                }else{
                    Toast.makeText(getContext(),"Debes Completar los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnform2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 editar();
             }
        });

        return view;
    }

    public void insertar(){

        try {
            String nom = nombre.getText().toString();
            String ed = edad.getText().toString();
            String raz = spraza.getSelectedItem().toString();
            String sex = sexo;

            SQLiteDatabase db = getContext().openOrCreateDatabase("DB_GRAFIN", Context.MODE_PRIVATE ,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS mascota(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre VARCHAR,edad VARCHAR,raza VARCHAR,sexo VARCHAR)");

            String sql = "insert into mascota(nombre,edad,raza,sexo)values(?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1,nom);
            statement.bindString(2,ed);
            statement.bindString(3,raz);
            statement.bindString(4,sex);
            statement.execute();

            nombre.setText("");
            edad.setText("");

            Toast.makeText(getContext(),"Mascota Registrada",Toast.LENGTH_SHORT).show();

        }catch (Exception ex){
            Toast.makeText(getContext(),"Error, no se pudieron guardar los datos.",Toast.LENGTH_LONG).show();
        }
    }

    public void editar()
    {
        try
        {
            String nomb = nombre.getText().toString();
            String edd = edad.getText().toString();
            String razz = spraza.getSelectedItem().toString();
            String sexx = sexo;
            int id = 1;

            SQLiteDatabase db = getContext().openOrCreateDatabase("DB_GRAFIN", Context.MODE_PRIVATE ,null);

            String sql = " update mascota set nombre = ?,edad=?,raza=?, sexo=? where id= ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,nomb);
            statement.bindString(2,edd);
            statement.bindString(3,razz);
            statement.bindString(4,sexx);
            statement.bindLong(5,id);


            statement.execute();
            Toast.makeText(getContext(),"Datos actualizados satisfactoriamente en la base de datos.",Toast.LENGTH_LONG).show();


        }
        catch (Exception ex)
        {
            Toast.makeText(getContext(),"Error no se pudieron guardar los datos.",Toast.LENGTH_LONG).show();
        }
    }





}