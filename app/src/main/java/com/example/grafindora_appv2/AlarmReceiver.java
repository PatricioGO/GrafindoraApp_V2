package com.example.grafindora_appv2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class AlarmReceiver extends BroadcastReceiver {

    Bundle bundle ;
    String desc;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, NotificationService.class);
        desc = intent.getStringExtra("descripcion");
        service1.putExtra("descripcion",desc);
        service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        ContextCompat.startForegroundService(context, service1 );
        Log.d("Grafindora", " Alarma Creada!!!");
    }
}

