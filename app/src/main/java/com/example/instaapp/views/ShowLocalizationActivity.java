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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Loc1234", "XDDDDDD");
        super.onCreate(savedInstanceState);
        showLocalizationBinding = ActivityShowLocalizationBinding.inflate(getLayoutInflater());
        setContentView(showLocalizationBinding.getRoot());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map_fragment2);


        mapFragment.getMapAsync(this);
    }
    private void getPlaceById(String placeId) {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
        Places.initialize(getApplicationContext(), "AIzaSyAwu6FO-Vb-ITp39cSydpdr7e6yYjdHP5k");
        PlacesClient placesClient = Places.createClient(this);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            LatLng latLng = place.getLatLng();
            map.addMarker(new MarkerOptions().position(latLng).title(place.getName()));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng , 10);
            map.moveCamera(cameraUpdate);
        }).addOnFailureListener((exception) -> {
            exception.printStackTrace();
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);

        getPlaceById(PickedPhoto.getLocalization());
    }
}