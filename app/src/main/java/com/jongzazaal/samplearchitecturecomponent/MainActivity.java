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
        new MyLocation(this, this, getLifecycle(), this);

    }
    @Override
    public LifecycleRegistry getLifecycle() {
        return this.mRegistry;
    }


    @Override
    public void LocationChange(Location location) {
        tv.setText(location.getLatitude()+", "+location.getLongitude());
    }
}
