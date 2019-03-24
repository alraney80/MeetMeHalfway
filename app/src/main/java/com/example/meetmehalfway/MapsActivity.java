package com.example.meetmehalfway;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private LatLng addr1;
    private LatLng addr2;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        addr1 = getIntent().getParcelableExtra("Addr1LatLng");
        addr2 = getIntent().getParcelableExtra("Addr2LatLng");

        Log.d("**************Addr 1", "" + addr1);
        Log.d("**************Addr 2", "" + addr2);



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



            // This zooms in the map so that you only see the two addresses instead of the world view.
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        //the include method will calculate the min and max bound.
        builder.include(new MarkerOptions().position(addr1).getPosition());
        builder.include(new MarkerOptions().position(addr2).getPosition());
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cu);
        //End Zoom Code

    }
}
