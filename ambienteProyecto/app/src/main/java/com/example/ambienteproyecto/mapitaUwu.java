package com.example.ambienteproyecto;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class mapitaUwu extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private DatabaseReference myRef;
    List<Tarro> trro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1Ijoiamdpcm9uciIsImEiOiJja2JtcDA1cW4xbDBjMnhteHB1MXZmMnJ3In0.9wXbZ2oen-ctiBB6zm511g");
        setContentView(R.layout.activity_mapita_uwu);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        myRef = FirebaseDatabase.getInstance().getReference();
        trro = new ArrayList<Tarro>();

    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {

        myRef.child("tarro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Tarro tr = objSnapshot.getValue(Tarro.class);
                    trro.add(tr);
                }

                    for (int i = 0; i < trro.size(); i++) {

                        MarkerOptions options = new MarkerOptions();
                        options.title(trro.get(i).getTipoTarro());
                        IconFactory iconFactory = IconFactory.getInstance(mapitaUwu.this);
                        Icon icon = iconFactory.fromResource(R.drawable.garbage);
                        options.icon(icon);

                        double value1 = Double.parseDouble(trro.get(i).getLatitud());
                        double value2 = Double.parseDouble(trro.get(i).getLongitud());

                        options.position(new LatLng(value1, value2));
                        mapboxMap.addMarker(options);

                        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {
                                // Create FillLayer with GeoJSON source and add the FillLayer to the map

                            }
                        });
                    }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}