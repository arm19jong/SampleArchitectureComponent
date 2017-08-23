package com.jongzazaal.samplearchitecturecomponent;

import android.arch.lifecycle.LifecycleActivity;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by root on 8/22/17.
 */

public class MyActivity extends LifecycleActivity{

    TextView tv;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        new MyLocation(this, this, getLifecycle(), new MyLocation.MyListener() {
            @Override
            public void LocationChange(Location location) {
                tv.setText(location.getLatitude()+", "+location.getLongitude());
            }
        });
    }
}
