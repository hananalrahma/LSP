package com.example.lsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubmitData extends AppCompatActivity {
// deklarasi
    Button btn_kirim, btn_gps;
    EditText et_lat, et_lon, et_nama, et_kontributor, et_ket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_data);

// asosiasi
        btn_kirim = findViewById(R.id.btn_submit);
        et_lat = findViewById(R.id.et_lat);
        et_lon = findViewById(R.id.et_lon);
        et_nama = findViewById(R.id.et_nama);
        et_ket = findViewById(R.id.et_keterangan);
        et_kontributor = findViewById(R.id.et_kontributor);
        btn_gps = findViewById(R.id.btn_gps);

        //aktifkan btn gps
        btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                akses_gps();
            }
        });

        // aktifkan btn kirim data
        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim_data();
            }
        });
    }

    public void kirim_data(){
        // setup okhttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient browser = builder.build();

 // susun req body
        RequestBody body = new FormBody.Builder()
                .add("lat", et_lat.getText().toString())
                .add("lon", et_lon.getText().toString())
                .add("nama", et_nama.getText().toString())
                .add("keterangan",et_ket.getText().toString())
                .add("kontributor",et_kontributor.getText().toString())
                .add("aksi","simpan")
                .build();

        // full req
        Request req = new Request.Builder()
                .url("https://service.garasitekno.com/lokasi.php")
                .post(body)
                .addHeader("Content-type","application/x-www-form-urlencoded")
                .build();

// kirim request menggunakan browser
        browser.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("kirim server","gagal");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SubmitData.this, "gagal Submit Data", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i("kirim server","berhasil");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SubmitData.this, "Berhasil Submit Data", Toast.LENGTH_SHORT).show();
                        et_lat.setText("");
                        et_lon.setText("");
                        et_nama.setText("");
                        et_ket.setText("");
                        et_kontributor.setText("");
                    }
                });
            }
        });
    }

    // cek permission GPS
    public void cek_ijin_gps(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
            // semisal belum diijinkan atau ditolak, minta ijin
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//                    new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1); atau pake ini
        }else {
            Toast.makeText(this, "Akses GPS diijinkan", Toast.LENGTH_SHORT).show();
        }
    }

    //akses gps
    public void akses_gps(){
        //cek permission
        cek_ijin_gps();
        //class GPS
        GPS gps = new GPS(this);
        Location lokasi = gps.ambil_lokasi();
        if (lokasi != null){
            Double lat = lokasi.getLatitude();
            Double lon = lokasi.getLongitude();
            //isikan ke dalam text
            et_lat.setText(lat.toString());
            et_lon.setText(lon.toString());
        }
    }

         @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "GPS Diijinkan", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "GPS Ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

