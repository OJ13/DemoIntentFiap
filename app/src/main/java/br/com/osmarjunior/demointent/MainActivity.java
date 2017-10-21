package br.com.osmarjunior.demointent;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.osmarjunior.demointent.utils.Constantes;

public class MainActivity extends AppCompatActivity {

    private TextView tvLogin;
    private TextView tvPlacarHome;
    private TextView tvPlacarVisitante;

    private int placarHome = 0;
    private int placarVisitante = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
