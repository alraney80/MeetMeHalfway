package com.example.meetmehalfway;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private LatLng addr1;
    private LatLng addr2;
    private LatLng center;
    private int radius;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        addr1 = getIntent().getParcelableExtra("Addr1LatLng");
        addr2 = getIntent().getParcelableExtra("Addr2LatLng");
        radius = getIntent().getIntExtra("Radius", 10);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.addMarker(new MarkerOptions().position(addr1).title("Address 1"));
        mMap.addMarker(new MarkerOptions().position(addr2).title("Address 2"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(addr1));

        //center is a LatLng to be changed later after we have algorithm to determine this
        center = addr1;
        Circle circle = map.addCircle(new CircleOptions()
            .center(center)
            .radius(radius*1609.34)
            .strokeColor(Color.RED)
            .fillColor(Color.BLUE));
    }
}

