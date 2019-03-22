package com.example.meetmehalfway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class LandingPage extends AppCompatActivity {

    private Button enterButton;
    private String placeAPIKey = BuildConfig.PlaceAPIKey;
    TextView addr1Result;
    TextView addr2Result;
    LatLng addr1LatLng;
    LatLng addr2LatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        /*
         * Initialize Places.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), placeAPIKey);
        }

        // Initialize the first AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_frag_address1);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        addr1Result = this.findViewById(R.id.AddrOneResult);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //addr1Result = findViewById(R.id.AddrOneResult);
                addr1Result.setText(place.getName());
                addr1LatLng = place.getLatLng();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                addr1Result.setText(status.toString());
            }
        });

        // Initialize the second AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_frag_address2);

        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        addr2Result = this.findViewById(R.id.AddrTwoResult);

        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //addr2Result = findViewById(R.id.AddrTwoResult);
                addr2Result.setText(place.getName());
                addr2LatLng = place.getLatLng();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                addr2Result.setText(status.toString());
            }
        });

        enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMapsActivity(addr1LatLng, addr2LatLng);
            }
        });
    }

    public void OpenMapsActivity(LatLng Addr1LatLng, LatLng Addr2LatLng) {
        Intent intent = new Intent(this, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("Addr1LatLng", Addr1LatLng);
        bundle.putParcelable("Addr2LatLng", Addr2LatLng);
        intent.putExtras(bundle);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
