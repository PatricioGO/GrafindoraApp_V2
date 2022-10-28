package com.example.grafindora_appv2;

import android.content.Intent;
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

    private Button btnform;
    private TextInputEditText nombre;
    private TextInputEditText edad;
    private Spinner spraza;
    private RadioButton sexHem,sexMach;
    private String sexo;

    String[] raza = {"Labrador","Poodle","Bulldog","Golden Retriever", "Pastor Aleman", "Bulldog Frnaces", "Beagle","Rottweiler","otro"};

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
        sexHem = (RadioButton) view.findViewById(R.id.rdbthembra);
        sexMach = (RadioButton) view.findViewById(R.id.rdbtmacho);


        //click del boton registrar
        btnform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nombre.getText().toString().isEmpty() || !edad.getText().toString().isEmpty() ){

                   /* Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.putExtra("nombre",nombre.getText().toString());
                    startActivity(intent);*/

                    //Checkeando la seleccion del radio buton
                    if (sexHem.isChecked()==true){
                        sexo = "Hembra";
                    }else if (sexMach.isChecked()){
                        sexo = "Macho";
                    }

                    Bundle bundle = new Bundle();

                    bundle.putString("nombre", nombre.getText().toString());
                    bundle.putString("edad",edad.getText().toString());
                    bundle.putString("raza",spraza.getSelectedItem().toString());
                    bundle.putString("sexo", sexo);

                    Fragment primerfragmet = new PrimerFragment();
                    primerfragmet.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container,primerfragmet);
                    fragmentTransaction.commit();

                    Toast.makeText(getContext(),"Mascota Registrada",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(),"Debes Completar los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}