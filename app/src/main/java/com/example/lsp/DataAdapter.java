package com.example.lsp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends BaseAdapter {
    // deklarasi
    Context ctx;
    private List<Data> daftar;

    // konstruktor
    public  DataAdapter(Context c){
        this.ctx = c;
        daftar = new ArrayList<>();
    }

             // fungsi load_data
    public void load_data(JSONObject jo){
        try {
            JSONArray data = jo.getJSONArray("data");
            daftar.clear();
            // iterasi array
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);
                Data d = new Data(
                        obj.getString("lat"),
                        obj.getString("lon"),
                        obj.getString("nama"),
                        obj.getString("keterangan"),
                        obj.getString("kontributor")
                );
                daftar.add(d);
            }
            Log.i("log", "berhasil mengumpulkan data");
            notifyDataSetChanged();//mendeteksi perubahan listview

        }catch (Exception e){
            Log.i("error"," = "+e);
        }
    }
    @Override
    public int getCount() {
        return daftar.size();
    }

    @Override
    public Object getItem(int position) {
        return daftar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = li.inflate(R.layout.baris, parent, false);

        //asosiasi baris.xml
        TextView tv_lat = convertView.findViewById(R.id.tv_lat);
        TextView tv_lon = convertView.findViewById(R.id.tv_lon);
        TextView tv_nama = convertView.findViewById(R.id.tv_nama);
        TextView tv_ket = convertView.findViewById(R.id.tv_ket);
        TextView tv_kontributor = convertView.findViewById(R.id.tv_kontributor);

        //isikan nilai
        Log.i("log", "proses inflating baris");
        Data d = daftar.get(position);
        tv_lat.setText(d.lat);
        tv_lon.setText(d.lon);
        tv_nama.setText(d.nama);
        tv_ket.setText(d.ket);
        tv_kontributor.setText(d.kontributor);

        //sudah terisa, return
        return convertView;
    }
}
