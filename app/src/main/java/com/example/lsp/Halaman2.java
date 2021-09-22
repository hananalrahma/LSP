package com.example.lsp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class Halaman2 extends AppCompatActivity {
    // deklarasi
    Button btn_playback;
    MediaPlayer mp;
    SeekBar seekBar;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman2);
        // asosiasi
        btn_playback = findViewById(R.id.btn_playback);
        mp = MediaPlayer.create(this, R.raw.lagu);
        seekBar = findViewById(R.id.seekBar);
        handler = new Handler();

        // singkronisasi durasi
        seekBar.setMax(mp.getDuration());

        // aktifkan seekbar
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // set durasi terkini
                mp.seekTo(seekBar.getProgress());
                return false;
            }
        });

        // aktifkan, dan putar lagu
        btn_playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // putar lagu
//                mp.start();
                playback();
            }
        });
    }

    // fungsi updater seekbar
    public void sb_updater(){
        seekBar.setProgress(mp.getCurrentPosition()); // ambil progress sekarang
        if(mp.isPlaying()){ // looping
            // lakukan update
            Runnable proses = new Runnable() {
                @Override
                public void run() {
                    sb_updater();
                }
            };
            handler.postDelayed(proses, 1000);
        }
    }

    public void playback(){
        if(btn_playback.getText().equals(getString(R.string.tombol_play))){
            mp.start();
            btn_playback.setText(getString(R.string.tombol_pause));
            sb_updater();
        }else {
            mp.pause();
            btn_playback.setText(getString(R.string.tombol_play));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "fase onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "fase onPause", Toast.LENGTH_SHORT).show();
        mp.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "fase onStop", Toast.LENGTH_SHORT).show();
    }
}