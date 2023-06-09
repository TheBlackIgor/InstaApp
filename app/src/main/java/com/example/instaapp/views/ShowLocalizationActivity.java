package com.example.instaapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
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

    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.ACCESS_FINE_LOCATION"
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLocalizationBinding = ActivityShowLocalizationBinding.inflate(getLayoutInflater());
        setContentView(showLocalizationBinding.getRoot());

        mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map_fragment2);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);
        Places.initialize(getApplicationContext(), "AIzaSyAwu6FO-Vb-ITp39cSydpdr7e6yYjdHP5k");
        PlacesClient placesClient = Places.createClient(this);
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

    private boolean checkIfPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    //nie
                }
                break;

        }

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (!checkIfPermissionsGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(REQUIRED_PERMISSIONS, 100);
            }
        } else {
            map = googleMap;
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            try {
                geocode(PickedPhoto.getLocalization());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}