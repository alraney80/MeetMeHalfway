package com.example.meetmehalfway;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private LatLng addr1;
    private LatLng addr2;
    private LatLng center;
    private int radius;
    private GoogleMap mMap;
    MarkerOptions place1, place2;
    ArrayList markerPoints= new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        addr1 = getIntent().getParcelableExtra("Addr1LatLng");
        addr2 = getIntent().getParcelableExtra("Addr2LatLng");
        radius = getIntent().getIntExtra("Radius", 1);

        //Settings Page
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.settingsButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent modifySettings=new Intent(MapsActivity.this,SettingsActivity.class);
                startActivity(modifySettings);
            }
        });

        //SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Called when the map is ready.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        place1 = new MarkerOptions().position(addr1);
        mMap.addMarker(place1.title("Address 1"));
        place2 = new MarkerOptions().position(addr2);
        mMap.addMarker(place2.title("Address 2"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(addr1));

        markerPoints.add(addr1);
        markerPoints.add(addr2);

        String url = getDirectionsUrl(place1.getPosition(), place2.getPosition());

        FetchUrl fetchUrl = new FetchUrl();

        // Start downloading json data from Google Directions API
        fetchUrl.execute(url);

        StringBuilder pnValue = new StringBuilder(pnMethod(center));
        PlacesTask placesTask = new PlacesTask();
        placesTask.execute(pnValue.toString()); //OG
        //placesTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    public String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=driving";
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String url = "https://maps.googleapis.com/maps/api/directions/json?"
                + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;


        // https://maps.googleapis.com/maps/api/directions/json?origin=33.2534746,-97.1539148&destination=33.23599679999999,-96.7148&mode=driving&key=AIzaSyBguDOBAg_Zi2K5DAFRO83idl4ucvNhyGo




//        // Origin of route
//        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//        // Destination of route
//        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//        // Sensor enabled
//        String sensor = "sensor=false";
//        String mode = "mode=driving";
//        // Building the parameters to the web service
//        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
//        // Output format
//        String output = "json";
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        //return url;
    }
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        private LatLng extrapolate(List<LatLng> path, LatLng origin, double distance) {
            LatLng extrapolated = null;

            if (!PolyUtil.isLocationOnPath(origin, path, false, 1)) { // If the location is not on path non geodesic, 1 meter tolerance
                return null;
            }

            float accDistance = 0f;
            boolean foundStart = false;
            List<LatLng> segment = new ArrayList<>();

            for (int i = 0; i < path.size() - 1; i++) {
                LatLng segmentStart = path.get(i);
                LatLng segmentEnd = path.get(i + 1);

                segment.clear();
                segment.add(segmentStart);
                segment.add(segmentEnd);

                double currentDistance = 0d;

                if (!foundStart) {
                    if (PolyUtil.isLocationOnPath(origin, segment, false, 1)) {
                        foundStart = true;

                        currentDistance = SphericalUtil.computeDistanceBetween(origin, segmentEnd);

                        if (currentDistance > distance) {
                            double heading = SphericalUtil.computeHeading(origin, segmentEnd);
                            extrapolated = SphericalUtil.computeOffset(origin, distance - accDistance, heading);
                            break;
                        }
                    }
                } else {
                    currentDistance = SphericalUtil.computeDistanceBetween(segmentStart, segmentEnd);

                    if (currentDistance + accDistance > distance) {
                        double heading = SphericalUtil.computeHeading(segmentStart, segmentEnd);
                        extrapolated = SphericalUtil.computeOffset(segmentStart, distance - accDistance, heading);
                        break;
                    }
                }

                accDistance += currentDistance;
            }

            return extrapolated;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
           }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }

            double middleDistance = SphericalUtil.computeLength(lineOptions.getPoints());

            LatLng center = extrapolate(lineOptions.getPoints(), lineOptions.getPoints().get(0), (middleDistance/2));

            //Log.d("onPostExecute","without Polylines drawn");
            //Log.d("latitude = "+ center.latitude, "longitude = "+ center.longitude);

            mMap.addMarker(new MarkerOptions().position(center).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(center)
                    .zoom(10).build();

                    //center is a LatLng to be changed later after we have algorithm to determine this
        //center = addr1;
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(center)
                .radius(radius*1609.34)
                .strokeColor(Color.BLUE));

            //This zooms in the map so that you only see the two addresses instead of the world view.
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            //the include method will calculate the min and max bound.
            builder.include(place1.getPosition());
            builder.include(place2.getPosition());
            builder.include(center);
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.3); // offset from edges of the map 10% of screen
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            mMap.animateCamera(cu);
            //End Zoom Code

        }
    }
    class DataParser {

        List<List<HashMap<String,String>>> parse(JSONObject jObject){

            List<List<HashMap<String, String>>> routes = new ArrayList<>() ;
            JSONArray jRoutes;
            JSONArray jLegs;
            JSONArray jSteps;

            try {

                jRoutes = jObject.getJSONArray("routes");

                /** Traversing all routes */
                for(int i=0;i<jRoutes.length();i++){
                    jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<>();

                    /** Traversing all legs */
                    for(int j=0;j<jLegs.length();j++){
                        jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                        /** Traversing all steps */
                        for(int k=0;k<jSteps.length();k++){
                            String polyline = "";
                            polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            /** Traversing all points */
                            for(int l=0;l<list.size();l++){
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("lat", Double.toString((list.get(l)).latitude) );
                                hm.put("lng", Double.toString((list.get(l)).longitude) );
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
            }


            return routes;
        }


        /**
         * Method to decode polyline points
         * */
        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }

            return poly;
        }
    }

       protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;

        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(10);
            lineOptions.color(Color.RED);

            Log.d("onPostExecute","onPostExecute lineoptions decoded");

        }

        // Drawing polyline in the Google Map for the i-th route
        if(lineOptions != null) {
            mMap.addPolyline(lineOptions);
        }
        else {
            Log.d("onPostExecute","without Polylines drawn");
        }

    }

    public StringBuilder pnMethod(LatLng center) {

        //use the halfway point location here, currently its just a California location.
        double mLatitude = 33.241586533325226; //OG
        double mLongitude = -97.17666; //OG

        //double mLatitude = center.latitude;
        //double mLongitude = center.longitude;

        StringBuilder pn = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        pn.append("location=" + mLatitude + "," + mLongitude);
        pn.append("&radius=1609.34");
        pn.append("&types=" + "hospital");
        //pn.append("&types=" + "restaurant");
        pn.append("&sensor=true");
        //Key value = AlzaSyBguDOBAg_Zi2K5DAFRO83idl4ucvNhyGo
        pn.append("&key=" + getString(R.string.google_maps_key));

        Log.d("Map", "api: " + pn.toString());

        return pn;
    }

    private class PlacesTask extends AsyncTask<String, Integer, String> {

        //String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {

            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParsingNearby parseNearby = new ParsingNearby();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParserTask
            parseNearby.execute(result);
        }
    }

    private class ParsingNearby extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        //JSONObject jObject; //OG

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            JSONObject jObject;
            MarkerOptions pn1;
            List<HashMap<String, String>> places = null;
            //Place_JSON placeJson = new Place_JSON(); //OG

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParsingNearby", jsonData[0].toString());
                Place_JSON placeJson = new Place_JSON();
                Log.d("ParsingNearby", placeJson.toString());

                places = placeJson.parse(jObject);
                Log.d("ParsingNearby", "Executing nearby places");
                Log.d("ParsingNearby", places.toString());

            } catch (Exception e) {
                Log.d("Exception", e.toString());
                e.printStackTrace();
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {

            Log.d("Map", "list size: " + list.size());
            // Clears all the existing markers;
            //mMap.clear();

            for (int i = 0; i < list.size(); i++) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);


                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));

                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));

                // Getting name
                String name = hmPlace.get("place_name");

                Log.d("Map", "place: " + name);

                // Getting vicinity
                String nearby = hmPlace.get("nearby");

                LatLng latLng = new LatLng(lat, lng);

                // Setting the position for the marker
                markerOptions.position(latLng);
                //pn1 = new MarkerOptions().position(latLng);
                //mMap.addMarker(pn1.title(name + " : " + nearby));


                markerOptions.title(name + " : " + nearby); //OG

                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

            }
        }
    }

    public class Place_JSON {
         //Receives a JSONObject and returns a list

        public List<HashMap<String, String>> parse(JSONObject jObject) {

            JSONArray jPlaces = null;
            try {
                // Retrieves all the elements in the 'places' array */
                jPlaces = jObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Invoking getPlaces with the array of json object where each json object represent a place
            return getPlaces(jPlaces);
        }

        private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
            int placesCount = jPlaces.length();
            List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> place = null;

            // Taking each place, parses and adds to list object */
            for (int i = 0; i < placesCount; i++) {
                try {
                    // Call getPlace with place JSON object to parse the place */
                    place = getPlace((JSONObject) jPlaces.get(i));
                    placesList.add(place);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return placesList;
        }


         // Parsing the Place JSON object
        private HashMap<String, String> getPlace(JSONObject jPlace) {

            HashMap<String, String> place = new HashMap<String, String>();
            String placeName = "-NA-";
            String nearby = "-NA-";
            String latitude = "";
            String longitude = "";
            String reference = "";

            try {
                // Extracting Place name, if available
                if (!jPlace.isNull("name")) {
                    placeName = jPlace.getString("name");
                }

                // Extracting the Places Nearby, if available
                if (!jPlace.isNull("nearby")) {
                    nearby = jPlace.getString("nearby");
                }

                latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
                reference = jPlace.getString("reference");

                place.put("place_name", placeName);
                place.put("nearby", nearby);
                place.put("lat", latitude);
                place.put("lng", longitude);
                place.put("reference", reference);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return place;
        }
    }
}



