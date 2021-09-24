package com.example.lsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // deklarasi komponen
    Button btn, btn_akses, btn_kirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // asosiasikan dengan layout
        btn = findViewById(R.id.btn_pop);
        btn_akses = findViewById(R.id.btn_akses_api);
        btn_kirim = findViewById(R.id.btn_kirim_data);

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ke hal kirim
                ke_hal_kirim();
            }
        });

        btn_akses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ke_hal_akses();
            }
        });

        // aktifkan, setOnClick
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menjalankan aksi saat tombo0l ditekan
                Toast.makeText(MainActivity.this, "Cek Tombol",
                        Toast.LENGTH_SHORT).show();
                pindah_hal_2();
            }
        });
    }

    public void ke_hal_kirim(){
        Intent i = new Intent(this, SubmitData.class); // pindah halaman ke Halaman2
        startActivity(i);;
    }

    public void pindah_hal_2(){
        Intent i = new Intent(this, Halaman2.class); // pindah halaman ke Halaman2
        startActivity(i);;
    }

    public void ke_hal_akses(){
        Intent i = new Intent(this, AksesApi.class); // pindah halaman ke Halaman2
        startActivity(i);;
    }
}