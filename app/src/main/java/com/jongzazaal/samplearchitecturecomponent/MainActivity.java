package com.jongzazaal.samplearchitecturecomponent;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private boolean enabled = false;
    private LocationManager locationManager;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onStart() {
        super.onStart();
        if (enabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10f, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 10f, this);
        }
        else {
            requestPermission();
        }
    }
    private void requestPermission(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(MainActivity.this, "onPermissionGranted", Toast.LENGTH_SHORT).show();
                        enabled = true;
                        onStart();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this, "onPermissionDenied", Toast.LENGTH_SHORT).show();
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        Toast.makeText(MainActivity.this, "onPermissionRationaleShouldBeShown", Toast.LENGTH_SHORT).show();
                        token.continuePermissionRequest();

                    }
                }).check();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        tv.setText(location.getLatitude()+", "+location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
