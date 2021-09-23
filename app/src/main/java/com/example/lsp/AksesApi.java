package com.example.lsp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AksesApi extends AppCompatActivity {
    //deklarasi
    ListView listView;
    DataAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akses_api);
        //asosiasi
        listView = findViewById(R.id.list_view);
        adapter = new DataAdapter(this);
        //sinkronisasi adapter dengan listview
        listView.setAdapter(adapter);
        ambil_data();
    }

    //akses api server
    public void ambil_data(){
        //menjalankan proses di background
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                //akses api
                String url = "https://service.garasitekno.com/lokasi.php";
                Log.i("log", "masuk proses background");
                //jalankan akses
                try {
                    URL alamat = new URL(url);
                    HttpURLConnection koneksi = (HttpURLConnection) alamat.openConnection();
                    InputStream is = koneksi.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader bf = new BufferedReader(reader);

                    //kumpulkan ke dalam string
                    String respon = "";
                    String baris = bf.readLine();
                    while (baris != null){
                        respon += baris;
                        baris = bf.readLine();
                    }

                    Log.i("log", "Data Hasil = "+respon);
//                    String finalRespon = respon;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(AksesApi.this, "Data= "+ finalRespon, Toast.LENGTH_SHORT).show();
//                        }
//                    });

                    JSONObject jo = new JSONObject(respon);
                    return jo;

                }catch (Exception e){
                    Log.i("log", "error = " +e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Log.i("log", "masuk ke post exec");
                if (o != null){
                    //jalankan proses load
                    JSONObject jo = (JSONObject) o;
                    Toast.makeText(AksesApi.this, "berhasil tampil data", Toast.LENGTH_SHORT).show();
                    adapter.load_data(jo);
                }else {
                    Toast.makeText(AksesApi.this, "gagal ambil data", Toast.LENGTH_SHORT).show();
                }
            }
        };

        //jalankan task
        task.execute();
    }
}