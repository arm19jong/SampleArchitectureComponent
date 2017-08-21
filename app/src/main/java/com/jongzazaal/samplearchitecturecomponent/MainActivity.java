package com.jongzazaal.samplearchitecturecomponent;

import android.Manifest;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity implements LifecycleRegistryOwner, MyLocation.MyListener {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(MainActivity.this, "onPermissionGranted", Toast.LENGTH_SHORT).show();
                        callLocationListener();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this, "onPermissionDenied", Toast.LENGTH_SHORT).show();
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        Toast.makeText(MainActivity.this, "onPermissionRationaleShouldBeShown", Toast.LENGTH_SHORT).show();
                    }
                }).check();
    }
    @Override
    public LifecycleRegistry getLifecycle() {
        return this.mRegistry;
    }

    private void callLocationListener() {
        MyLocation myLocation = new MyLocation(this, mRegistry, this);
        myLocation.setEnabled(true);
    }

    @Override
    public void LocationChange(Location location) {
        tv.setText(location.getLatitude()+", "+location.getLongitude());
    }
}
