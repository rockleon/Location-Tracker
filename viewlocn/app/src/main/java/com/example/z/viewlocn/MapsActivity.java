package com.example.z.viewlocn;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //private StorageReference mStorageRef;
        //mStorageRef = FirebaseStorage.getInstance().getReference();
    }


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
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            final double[] latitu = {23.0089};
            final double[] longitu = {45.0056};
            final String[] name = {"user"};

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Location_Details");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot postSnapshot) {
                    for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {

                        latitu[0] = (Double) dataSnapshot.child("latitude").getValue();
                        longitu[0] = (Double) dataSnapshot.child("longitude").getValue();
                        name[0] = (String) dataSnapshot.child("name").getValue();

                        Log.d("LatLon", latitu[0] + longitu[0] + "");
                        //Toast.makeText(LiveTrain.this, latitu[0].toString()+" - "+ longitu[0].toString(), Toast.LENGTH_SHORT).show();
                        LatLng trainLocation = new LatLng(latitu[0], longitu[0]);

                        MarkerOptions mop = new MarkerOptions();
                        mop.position(trainLocation);
                        mop.title(name[0]);
                       // mop.icon(icon);
                        mMap.addMarker(mop);

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(trainLocation));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(trainLocation,13f));
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("Exception FB",databaseError.toException());
                }
            });

//        LatLng trainLocation = new LatLng(latitu[0], longitu[0]);
//
//        MarkerOptions mop = new MarkerOptions();
//            mop.position(trainLocation);
//            mop.title(name);
//            mop.icon(icon);
//            mMap.addMarker(mop);
//
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(trainLocation));
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(trainLocation,13f));//       }
    }
}
