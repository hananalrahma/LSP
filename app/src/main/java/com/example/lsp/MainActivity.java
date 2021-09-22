package com.example.lsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // deklarasi komponen
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // asosiasikan dengan layout
        btn = findViewById(R.id.btn_pop);

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

    public void pindah_hal_2(){
        Intent i = new Intent(this, Halaman2.class); // pindah halaman ke Halaman2
        startActivity(i);;
    }
}