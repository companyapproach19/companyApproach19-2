package com.example.geolocalizacionequ3;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0; // latitud
    double lng = 0.0; // longitud
    String mensaje1;
    String direccion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        miUbicacion();

        //Funcion para activar los servicios del gps cuando estén apagados
        private final void locationStart(){
            LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(!gpsEnabled){
                Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(settingsIntent);
            }
        }
    // Nos permite obtener la direccion de la calle a partir de la longuitud y latitud
    public final void setLocation(location loc){
            if (loc.getLatitude() != 0.0 && loc.getLonguitude() != 0.0) {
                try {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> list = geocoder.getFromLocation(loc.getLatitude(), loc.getLonguitude(), 1);
                    if (!list.isEmpty()) {
                        Address DirCalle = list.get(0);
                        direccion = (DirCalle.getAddressline(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
     }

     //Nos permite aniadir el marcador en el mapa
        private void agregarMarcador(double lat, double lng) {
            LatLng coordenadas = new LatLng(lat, lng);
            CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 14);
            if (marcador != null) marcador.remove();
            marcador = mMap.addMarker(new MarkerOptions()
                    .position(coordenadas)
                    .title("Mi posición actual" + direccion)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
            mMap.animateCamera(miUbicacion);
        }

        //Para actualizar la ubicacion
        private void actualizarUbicacion(Location location) {
            if (location != null) {

                lat = location.getLatitude();
                lng = location.getLongitude();
                agregarMarcador(lat, lng);


            }
        }

        // Para el control del GPS
        LocationListener locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                actualizarUbicacion(location);
                setLocation(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                mensaje1 = ("GPS Activado");
                Mensaje();
            }

            @Override
            public void onProviderDisabled(String s) {
                mensaje1 = ("GPS Desactivado");
                locationStart();
                Mensaje();
            }
        };

        // metodo para poder obtener mi ubicacion

        private static int PETICION_PERMISO_LOCALIZACION = 101;

        private void miUbicacion() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ActivityCompat.requestPermissions(this, new String[] (Manifest.permission.ACCESS_FINE_LOCATION), PETICION_PERMISO_LOCALIZACION)) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            } else {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                actualizarUbicacion(location);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 17000,0,locListener);
            }

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

    public void Mensaje(){

       Toast toast = Toast.makeText(this, mensaje1, Toast.LENGTH_SHORT);
        toast.show();
    }
}