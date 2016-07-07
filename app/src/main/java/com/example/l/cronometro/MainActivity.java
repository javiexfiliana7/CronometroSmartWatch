package com.example.l.cronometro;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {
    private long time = 0;
    private Chronometer chronometer;
    private boolean iniciado = false;
    private RelativeLayout fondo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {


                chronometer = (Chronometer) findViewById(R.id.chronometer);
                chronometer.setText("00:00:00");
                fondo=(RelativeLayout)findViewById(R.id.fondo);

                fondo.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        // TODO Auto-generated method stub

                        if (!iniciado) { //para poder reiniciarlo, antes debe estar parado
                            chronometer.stop();
                            chronometer.setText("00:00:00");
                            time = 0;
                            fondo.setBackgroundColor(Color.parseColor("#58ACFA"));
                            return true;
                        }else
                            return false;

                    }

                });

                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
                    @Override
                    public void onChronometerTick(Chronometer cArg) {
                        long time = SystemClock.elapsedRealtime() - cArg.getBase();

                        int h   = (int)(time /3600000)%60;
                        int m = (int)(time/60000)%60;
                        int s= (int)(time/1000)%60;


                        String hh = h < 10 ? "0"+h: h+""; //si es menor de 10 le añadimos 0 a la izquierda
                        String mm = m < 10 ? "0"+m: m+"";
                        String ss = s < 10 ? "0"+s: s+"";
                        cArg.setText(hh+":"+mm+":"+ss);
                    }
                });


            }
        });
    }


    public void startButtonClick(View v) {

        if (iniciado){// //Si ya está iniciado, paramos el cronómetro
            time = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
            iniciado=false;
            fondo.setBackgroundColor(Color.parseColor("#FE2E2E"));

        }
        else {
            //iniciar cronómetro
            chronometer.setBase(SystemClock.elapsedRealtime() + time);
            chronometer.start();

            iniciado=true;
            fondo.setBackgroundColor(Color.parseColor("#2EFE2E"));

        }

    }


}
