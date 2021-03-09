package com.xavi.polylinenuevo;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText et_latitud, et_longitud;
    private Button btn_insertar_punto;
    private ProgressBar cargaMap;
    private Location location;
    private LocationManager lm;
    private Boolean mapaCentrado = false;
    private PolylineOptions opciones_polyline = new PolylineOptions().color(Color.BLUE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        inicializarCampos();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void inicializarCampos() {
        et_latitud = findViewById(R.id.et_latitud);
        et_longitud = findViewById(R.id.et_longitud);
        cargaMap = findViewById(R.id.pb_progressBar_cargamapa);
        btn_insertar_punto = findViewById(R.id.btn_insertar_punto);
        //obtenerUbicacion();
        btn_insertar_punto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //miro lo que a puesto en los campos de latitud y longitud y se crea un lantlong para añadirlo al polylineOpcion
                Double latitud = Double.parseDouble(et_latitud.getText().toString());
                Double longitud = Double.parseDouble(et_longitud.getText().toString());
                LatLng punto = new LatLng(latitud, longitud);


                opciones_polyline.add(punto);
                mMap.addPolyline(opciones_polyline);
                mMap.addMarker(new MarkerOptions().position(punto).title("Marker"));
                if (mapaCentrado == false) {
                    mapaCentrado = true;

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(punto, 6));
                }
            }
        });

    }

    private void obtenerUbicacion() {
        LocationListener oyente_localizaciones = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d("Localizacion: ", location.getLatitude() + ", " + location.getLongitude());


            }

        };

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
        btn_insertar_punto.setEnabled(true);

        cargaMap.setVisibility(View.INVISIBLE);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}