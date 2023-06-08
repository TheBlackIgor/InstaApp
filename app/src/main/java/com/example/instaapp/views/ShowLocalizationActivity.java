package com.example.instaapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.instaapp.R;
import com.example.instaapp.databinding.ActivityMapBinding;
import com.example.instaapp.databinding.ActivityShowLocalizationBinding;
import com.example.instaapp.statik.NewPostFile;
import com.example.instaapp.statik.PickedPhoto;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ShowLocalizationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityShowLocalizationBinding showLocalizationBinding;
    private GoogleMap map;
    private List<Address> list;
    private Geocoder geocoder;
    private SupportMapFragment mapFragment;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("INFO1", "XDDDDDDDDDDDDDDD");
        showLocalizationBinding = ActivityShowLocalizationBinding.inflate(getLayoutInflater());
        Log.d("INFO2", "XDDDDDDDDDDDDDDD");
        setContentView(showLocalizationBinding.getRoot());
        Log.d("INFO3", "XDDDDDDDDDDDDDDD");

        mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map_fragment2);
        Log.d("INFO4", "XDDDDDDDDDDDDDDD");
        mapFragment.getMapAsync(this);
        Log.d("INFO5", "XDDDDDDDDDDDDDDD");
        geocoder = new Geocoder(this);
        Log.d("INFO6", "XDDDDDDDDDDDDDDD");
        Places.initialize(getApplicationContext(), "AIzaSyAwu6FO-Vb-ITp39cSydpdr7e6yYjdHP5k");
        Log.d("INFO7", "XDDDDDDDDDDDDDDD");
        PlacesClient placesClient = Places.createClient(this);
        Log.d("INFO8", "XDDDDDDDDDDDDDDD");
    }
    private void geocode(String locationName) throws IOException {
        Log.d("Location123", locationName);
        list = geocoder.getFromLocationName(locationName, 1);

        double latitude = list.get(0).getLatitude();
        double longitude = list.get(0).getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);
        Log.d("latleng", String.valueOf(latLng));
        Log.d("MAPP", map.toString());
        map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng , 10);
//        map.moveCamera(cameraUpdate);
        map.animateCamera(cameraUpdate);
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        Log.d("INFO9", "XDDDDDDDDDDDDDDD");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.d("INFO10", "XDDDDDDDDDDDDDDD");
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);

        Log.d("INFO11", "XDDDDDDDDDDDDDDD");
        LocationManager locationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            locationManager = (LocationManager) getSystemService(ShowLocalizationActivity.this.LOCATION_SERVICE);
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
        }

        try {
            geocode(PickedPhoto.getLocalization());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}