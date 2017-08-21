package com.jongzazaal.samplearchitecturecomponent;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

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

    public MyLocation(Context context, Lifecycle lifecycle, MyListener myListener) {
        this.myListener = myListener;
        this.lifecycle = lifecycle;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        lifecycle.addObserver(this);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        start();
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
        public void LocationChange(Location location);
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(ON_START)
    public void start() {
        if (enabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10f, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 10f, this);
        }
    }
    @OnLifecycleEvent(ON_STOP)
    public void stop() {}
    @OnLifecycleEvent(ON_DESTROY)
    public void cleanup() {
        lifecycle.removeObserver(this);
    }
}

