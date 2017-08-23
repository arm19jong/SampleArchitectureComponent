package com.jongzazaal.samplearchitecturecomponent;

import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by root on 8/21/17.
 */

public class fragmentApp extends Fragment implements LifecycleRegistryOwner, MyLocation.MyListener {
    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
    TextView tv2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MyLocation(getActivity(), getContext(), getLifecycle(), this);
//        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);

    }

    @Override
    public void onStart() {
        super.onStart();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);

    }

    @Override
    public void onStop() {
        super.onStop();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);

    }
    @Override
    public void onResume() {
        super.onResume();

        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.flagement2, container, false);
        tv2 = (TextView) v.findViewById(R.id.tv2);
        return v;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.mRegistry;
    }

    @Override
    public void LocationChange(Location location) {
        Log.d("TAG2", "LocationChange: "+location.getLatitude());
        tv2.setText(location.toString());
    }
}
