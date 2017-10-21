package br.com.osmarjunior.demointent;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.osmarjunior.demointent.broadcastreceiver.AlarmeReceiver;
import br.com.osmarjunior.demointent.services.BoundService;
import br.com.osmarjunior.demointent.utils.Constantes;

public class MainActivity extends AppCompatActivity {

    private TextView tvLogin;
    private TextView tvPlacarHome;
    private TextView tvPlacarVisitante;

    private int placarHome = 0;
    private int placarVisitante = 0;

    BoundService mBoundService;
    boolean mServiceBound = false;
    private TextView tvTempoAtual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTempoAtual = (TextView)findViewById(R.id.tvTempoAtual);

        tvLogin = (TextView)findViewById(R.id.tvLogin);
        tvPlacarHome = (TextView)findViewById(R.id.tvPlacarHome);
        tvPlacarVisitante = (TextView)findViewById(R.id.tvPlacarVisitante);

        if(savedInstanceState != null){
            placarHome = savedInstanceState.getInt(Constantes.KEY_PLACAR_CASA);
            placarVisitante = savedInstanceState.getInt(Constantes.KEY_PLACAR_VISITANTE);
        }

        tvPlacarHome.setText(String.valueOf(placarHome));
        tvPlacarVisitante.setText(String.valueOf(placarVisitante));

        if(getIntent() != null){
            String user = getIntent().getStringExtra(Constantes.KEY_LOGIN);
            tvLogin.setText(user);
        }
    }
    public void golCasa(View v){
        placarHome++;
        tvPlacarHome.setText(String.valueOf(placarHome));
    }
    public void golVisitante(View v){
        placarVisitante++;
        tvPlacarVisitante.setText(String.valueOf(placarVisitante));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constantes.KEY_PLACAR_CASA, placarHome);
        outState.putInt(Constantes.KEY_PLACAR_VISITANTE, placarVisitante);
    }

    public void programarAlarme(View v){
        EditText text = (EditText)findViewById(R.id.etTempoJogo);
        int i = Integer.parseInt(text.getText().toString());

        Intent intent = new Intent(this, AlarmeReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(),0,intent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (i * 1000),
                pendingIntent);
        Toast.makeText(this, "Alarm set in " + i + "seconds", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BoundService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mServiceBound){
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        if(mServiceBound){
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
        Intent intent = new Intent(MainActivity.this, BoundService.class);
        stopService(intent);
        super.onDestroy();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyBinder myBinder = (BoundService.MyBinder) service;
            mBoundService = myBinder.getService();
            mServiceBound = true;
            tvTempoAtual.setText(mBoundService.getTimestamp());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };
}

