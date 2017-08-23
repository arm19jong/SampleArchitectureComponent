package com.jongzazaal.samplearchitecturecomponent;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends LifecycleActivity implements  MyLocation.MyListener {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        new MyLocation(this, this, getLifecycle(), this);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrderFragment();
            }
        });

    }

    @Override
    public void LocationChange(Location location) {
        tv.setText(location.getLatitude()+", "+location.getLongitude());
    }
    private void addOrderFragment() {
        fragmentApp fragment = new fragmentApp();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment, fragment);
        transaction.commit();
    }
}
