package com.example.grafindora_appv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PrimerFragment primerFragment = new PrimerFragment();
    SegundoFragment segundoFragment = new SegundoFragment();
    TercerFragment tercerFragment = new TercerFragment();
    private String hora, descrip, nombre;
    final int CAPTURA_IMAGEN = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        cargarFragment(primerFragment);
        recibirData();

       // Button btncamara= findViewById(R.id.btncam);

        /*btncamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAPTURA_IMAGEN);
            }
        });*/



    }



    public void recibirData() {

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            hora = bundle.getString("hora");
            descrip = bundle.getString("descripcion");
            nombre = bundle.getString("nombre");
        }
    }

    public  void enviarData(){

        Bundle arg = new Bundle();

        arg.putString("hora",hora);
        arg.putString("descripcion",descrip);
        arg.putString("nombre",nombre);



        Fragment segundoFragment = new SegundoFragment();
        segundoFragment.setArguments(arg);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,segundoFragment);
        fragmentTransaction.commit();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.primerFragment:
                    cargarFragment(primerFragment);
                    return true;

                case R.id.segundoFragment:
                    cargarFragment(segundoFragment);
                    enviarData();
                    return true;

                case R.id.tercerFragment:
                    cargarFragment(tercerFragment);
                    return true;
            }
            return false;
        }
    };

    public void cargarFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.commit();
    }
}