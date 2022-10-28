package com.example.grafindora_appv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SetAlarmaActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private TextView txtViewHora;
    private TextInputEditText descrip;
    private RadioButton paseo,comida;
    private String nombre;
    private Button btnAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarma);

        timePicker = findViewById(R.id.timepickerid);
        txtViewHora = findViewById(R.id.tvtime);
        descrip = findViewById(R.id.tvdescrpalarm);
        paseo = findViewById(R.id.rdbtnpaseoalarm);
        comida = findViewById(R.id.rdbtncomidaalarm);
        btnAlarm = findViewById(R.id.btnguardaralarm);


        timePicker.setOnTimeChangedListener((timePicker1, i, i1) ->
                txtViewHora.setText(i+":"+i1));


        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtViewHora.getText().equals("hora") ){
                    Toast.makeText(SetAlarmaActivity.this,"Selecciona una hora",Toast.LENGTH_SHORT).show();
                }else if (paseo.isChecked()){
                    nombre = "Paseo";
                    crearAlerta();
                }else if(comida.isChecked()) {
                    nombre = "Comida";
                    crearAlerta();
                }else if (!paseo.isChecked() || !comida.isChecked()){
                    Toast.makeText(SetAlarmaActivity.this,"Debes seleccionar una opción",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void crearAlerta(){
        Intent intent = new Intent(SetAlarmaActivity.this,MainActivity.class);
        intent.putExtra("hora",txtViewHora.getText().toString());
        intent.putExtra("descripcion",descrip.getText().toString());
        intent.putExtra("nombre",nombre);
        startActivity(intent);

        Toast.makeText(SetAlarmaActivity.this,"Nueva alerta creada",Toast.LENGTH_SHORT).show();

        /* Fragment segundoFragment = new SegundoFragment();
                segundoFragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container,segundoFragment);
                fragmentTransaction.commit();*/
    }
}