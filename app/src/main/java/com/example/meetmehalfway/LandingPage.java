package com.example.meetmehalfway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class LandingPage extends AppCompatActivity {

    private Button enterButton;
    //private static final String TAG = "YOUR-TAG-NAME";
    private String placeAPIKey = BuildConfig.PlaceAPIKey;
    private TextView addr1Result;
    private TextView addr2Result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        addr1Result = this.findViewById(R.id.AddrOneResult);
        addr2Result = this.findViewById(R.id.AddrTwoResult);

        /*
         * Initialize Places.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), placeAPIKey);
        }

        // Initialize the first AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_frag_address1);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                addr1Result.setText(String.format(place.getName(),place.getAddress()));
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

        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                addr2Result.setText(place.getName());
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
                OpenMapsActivity();
            }
        });
    }

    public void OpenMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
