package com.codebrain.minato.tragua;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends NavigationDrawerBaseActivity {

    private GoogleMap mMap;
    private CameraPosition cameraPosition;

    private boolean mLocationPermissionGranted;//used to known if have permision for location

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Location mLastKnowLocation;
    private LatLng mDefaultLocation = new LatLng(32,80);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            /**
             * Manipulates the map once available.
             * This callback is triggered when the map is ready to be used.
             * This is where we can add markers or lines, add listeners or move the camera. In this case,
             * we just add a marker near Sydney, Australia.
             * If Google Play services is not installed on the device, the user will be prompted to install
             * it inside the SupportMapFragment. This method will only be triggered once the user has
             * installed Google Play services and returned to the app.
             */
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(-34, 151);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });
    }

    protected void setCameraPosition(LatLng latLng)
    {
        if (mMap != null)
        {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
    }

    private void getDeviceLocation()
    {
        try
        {
            if (mLocationPermissionGranted)
            {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful())
                        {
                            mLastKnowLocation = task.getResult();
                            setCameraPosition(new LatLng(mLastKnowLocation.getLatitude(), mLastKnowLocation.getLongitude()));
                        }
                        else
                        {
                            //usig default location
                            setCameraPosition(mDefaultLocation);
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        }
        catch (NullPointerException e)
        {
            Log.d("Exception", e.getMessage());
        }
        catch (SecurityException e)
        {
            Log.d("Exception", e.getMessage());
        }
    }

    
}
