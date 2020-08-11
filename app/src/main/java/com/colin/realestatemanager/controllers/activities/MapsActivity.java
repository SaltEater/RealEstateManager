package com.colin.realestatemanager.controllers.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import com.colin.realestatemanager.R;
import com.colin.realestatemanager.models.EstateWithPhotos;
import com.colin.realestatemanager.viewmodels.MapViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    @BindView(R.id.map_toolbar)
    Toolbar toolbar;

    private GoogleMap map;
    private ArrayList<Marker> markers = new ArrayList<>();
    private List<EstateWithPhotos> estates = new ArrayList<>();
    private MapViewModel mapViewModel;
    private Geocoder geocoder;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       this.geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        configEstatesList();
        configToolbar();
    }

    // ===============================
    // TOOLBAR
    // ===============================

    private void configToolbar() {
        toolbar.setTitle("Map");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_estate_activity_menu, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // ===============================
    // CONFIGURATION
    // ===============================

    private void configEstatesList() {
        if (mapViewModel.getAllEstatesWithPhotos().getValue() != null) {
            estates.addAll(mapViewModel.getAllEstatesWithPhotos().getValue());
        }
        else {
            estates = new ArrayList<>();
        }
        mapViewModel.getAllEstatesWithPhotos().observe(this, estateWithPhotos -> {
            estates.clear();
            deleteMarkers();
            if (estateWithPhotos != null) {
                estates.addAll(estateWithPhotos);
                addMarkers();
            }
        });
    }

    private void addMarkers() {

        for (EstateWithPhotos estateWithPhotos : estates) {
            try {
                Address address = geocoder.getFromLocationName(getAddress(estateWithPhotos), 1).get(0);
                markers.add(map.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude()))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))));
                markers.get(markers.size() - 1).setTag(estateWithPhotos.getEstate().getEstateId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteMarkers() {
        for (int i = 0; i < markers.size(); i++)
            markers.get(i).remove();
        markers.clear();
    }

    private String getAddress(EstateWithPhotos estate) {
        return estate.getEstate().getAddress() + " " +
                estate.getEstate().getCity() + " " +
                estate.getEstate().getPostalCode() + " " +
                estate.getEstate().getState();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
        addMarkers();
        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, location -> {
                            if (location != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));
                            }
                        })
                .addOnFailureListener(this, location -> {
                });
            }
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (isPermissionGranted(permissions, grantResults)) {
                enableMyLocation();
            }
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTag() != null && findEstate((Long) marker.getTag()) != -1)
            launchEstateDetailsActivity(estates.get(findEstate((Long) marker.getTag())));
        return true;
    }

    private void launchEstateDetailsActivity(EstateWithPhotos estate) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("Estate", estate);
        startActivity(intent);
    }

    private int findEstate(long estateId) {
        for (int i = 0; i < estates.size(); i++)
            if (estates.get(i).getEstate().getEstateId() == estateId)
                return i;
        return -1;
    }

    private boolean isPermissionGranted(String[] grantPermissions, int[] grantResults) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (Manifest.permission.ACCESS_FINE_LOCATION.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }
}