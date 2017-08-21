package com.jongzazaal.samplearchitecturecomponent;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


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
