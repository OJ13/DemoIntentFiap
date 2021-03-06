package br.com.osmarjunior.demointent.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.widget.Chronometer;

import br.com.osmarjunior.demointent.utils.Constantes;

public class BoundService extends Service{
    private IBinder mBinder = new MyBinder();
    private Chronometer mChronometer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(Constantes.LOG_TAG, "in onBind");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(Constantes.LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(Constantes.LOG_TAG, "in onUnbind");
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(Constantes.LOG_TAG, "in onCreate");
        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(Constantes.LOG_TAG, "in onDestroy");
    }

    public String getTimestamp(){
        long elapsedMillis = SystemClock.elapsedRealtime()
                - mChronometer.getBase();
        int hours = (int)(elapsedMillis/ 3600000);
        int minutes = (int)(elapsedMillis - hours * 3600000)/60000;
        int seconds = (int)(elapsedMillis - hours * 3600000 - minutes * 60000)/1000;
        int millis = (int)(elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000);
        return hours + ":" + minutes + ":" + seconds + ":" + millis;
    }

    public class MyBinder extends Binder{
        public BoundService getService(){
            return BoundService.this;
        }
    }
}
