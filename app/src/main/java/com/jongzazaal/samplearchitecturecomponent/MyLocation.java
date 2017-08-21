package com.jongzazaal.samplearchitecturecomponent;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import static android.arch.lifecycle.Lifecycle.Event.ON_DESTROY;
import static android.arch.lifecycle.Lifecycle.Event.ON_START;
import static android.arch.lifecycle.Lifecycle.Event.ON_STOP;

/**
 * Created by root on 8/21/17.
 */
public class MyLocation implements LifecycleObserver, LocationListener {

    private boolean enabled = false;
    private Lifecycle lifecycle;
    private MyListener myListener;
    private LocationManager locationManager;
    private Activity myActivity;

    public MyLocation(Activity myActivity,Context context, Lifecycle lifecycle, MyListener myListener) {
        this.myActivity = myActivity;
        this.myListener = myListener;
        this.lifecycle = lifecycle;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lifecycle.addObserver(this);
    }


    @Override
    public void onLocationChanged(Location location) {
        myListener.LocationChange(location);
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

    public interface MyListener {
        void LocationChange(Location location);
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(ON_START)
    public void start() {
        if (enabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10f, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 10f, this);
        }
        else {
            Dexter.withActivity(myActivity)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                            Toast.makeText(myActivity, "onPermissionGranted", Toast.LENGTH_SHORT).show();
                            enabled = true;
                            start();
                        }
                        @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                            Toast.makeText(myActivity, "onPermissionDenied", Toast.LENGTH_SHORT).show();
                        }
                        @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            Toast.makeText(myActivity, "onPermissionRationaleShouldBeShown", Toast.LENGTH_SHORT).show();
                            token.continuePermissionRequest();

                        }
                    }).check();


        }
    }
    @OnLifecycleEvent(ON_STOP)
    public void stop() {}
    @OnLifecycleEvent(ON_DESTROY)
    public void cleanup() {
        lifecycle.removeObserver(this);
    }
}

