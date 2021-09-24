package com.example.lsp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class GPS implements LocationListener {
    //deklarasi
    Context ctx;
    Location lokasi;

    //konstruktor
    public GPS(Context C){
        this.ctx = C;
    }

    //ambil lokasi
    public Location ambil_lokasi(){
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        // cek permission
        if(ContextCompat.checkSelfPermission(ctx,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(ctx, "Sedang ambil lokasi", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(ctx, "GPS tidak disetujui", Toast.LENGTH_SHORT).show();
            return null;
        }

        // ambil lokasi
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 100, this);
        lokasi = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return lokasi;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
