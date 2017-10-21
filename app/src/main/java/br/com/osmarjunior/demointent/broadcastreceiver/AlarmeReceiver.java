package br.com.osmarjunior.demointent.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import br.com.osmarjunior.demointent.R;

public class AlarmeReceiver extends BroadcastReceiver {
    private MediaPlayer mp = null;

    public AlarmeReceiver(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        mp = MediaPlayer.create(context, R.raw.hadouken);
        mp.start();

        Toast.makeText(context, "Alarm...", Toast.LENGTH_LONG).show();
    }
}
