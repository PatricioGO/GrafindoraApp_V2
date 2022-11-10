package com.example.grafindora_appv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Random;

public class SetAlarmaActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private TextView txtViewHora;
    private TextInputEditText descrip;
    private RadioButton paseo,comida;
    private String nombre;
    private Button btnAlarm, btnAtras;


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
        btnAtras = findViewById(R.id.btnatrasalarm);

        timePicker.setOnTimeChangedListener((timePicker1, i, i1) ->
                txtViewHora.setText(i+":"+i1));


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAlarmaActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

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


        try {
            String hora = txtViewHora.getText().toString();
            String desc = descrip.getText().toString();
            String nom = nombre;


            SQLiteDatabase db = openOrCreateDatabase("DB_GRAFIN", Context.MODE_PRIVATE ,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS alerta(id INTEGER PRIMARY KEY AUTOINCREMENT,hora VARCHAR, descrip VARCHAR,nombre VARCHAR)");

            String sql = "insert into alerta(hora,descrip,nombre)values(?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1,hora);
            statement.bindString(2,desc);
            statement.bindString(3,nom);
            statement.execute();

            Random rand = new Random();
            int upperbound = 100000;
            int int_random = rand.nextInt(upperbound);

            Calendar today = Calendar.getInstance();
            String[] splitHora = hora.split(":");

            today.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitHora[0]));
            today.set(Calendar.MINUTE, Integer.parseInt(splitHora[1]));
            today.set(Calendar.SECOND, 0);



            setAlarm(int_random, desc, today.getTimeInMillis(), SetAlarmaActivity.this);



            Toast.makeText(SetAlarmaActivity.this,"Nueva alerta creada",Toast.LENGTH_SHORT).show();

        }catch (Exception ex){
            Log.d("alerta", ex.getMessage());

            Toast.makeText(SetAlarmaActivity.this,"No hay alerta",Toast.LENGTH_SHORT).show();

        }


    }

    public static void setAlarm(int i,String descripcion, Long timestamp, Context ctx) {

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        alarmIntent.putExtra("descripcion",descripcion);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }
}