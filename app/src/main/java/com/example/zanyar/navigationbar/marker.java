package com.example.zanyar.navigationbar;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class marker extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.K
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap !=null){
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    marker.this.setMarker("local" , latLng.latitude,latLng.longitude);
                }
            });
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Geocoder gc=new Geocoder(marker.this);
                    LatLng ll=marker.getPosition();
                    List<Address>list=null;
                    double lat=ll.latitude;
                    double lng=ll.longitude;
                    try {
                        list=gc.getFromLocation(lat , lng, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address add=list.get(0);
                    marker.setTitle(add.getLocality());
                    marker.showInfoWindow();

                }
            });
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {


                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v=getLayoutInflater().inflate(R.layout.info_marker,null);
                    TextView tv_locality=v.findViewById(R.id.tv_locality);
                    TextView tv_lat=v.findViewById(R.id.tv_lat);
                    TextView tv_long=v.findViewById(R.id.tv_lang);
                    TextView tv_snippet=v.findViewById(R.id.tv_snippet);

                    LatLng ll=marker.getPosition();
                    tv_locality.setText(marker.getTitle());
                    tv_lat.setText("Enlem"+ll.latitude);
                    tv_long.setText("Boylam"+ll.longitude);
                    tv_snippet.setText(marker.getSnippet());
                    return v;
                }
            });
        }

goToLoactionzoom(36.66 ,38.66 ,15,mMap);
        //goToLoaction(36.66 ,38.66);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled(true);

    }
    private void goToLoaction(double lat, double lng){

LatLng ll =new LatLng(lat,lng);
        CameraUpdate update=CameraUpdateFactory.newLatLng(ll);
        mMap.moveCamera(update);
    }
Marker marker,marker1,marker2;
    Polyline line;
    Circle circle;
    Polygon shape;
    public void GeoLocation( View view)throws IOException{
         edit = (EditText) findViewById(R.id.edit_konum);
     String location=edit.getText().toString();
        Geocoder gc=new Geocoder(this);
        List<Address>list=gc.getFromLocationName(location,1);
        Address address=list.get(0);
        String locality=address.getLocality();
        Toast.makeText(this,locality,Toast.LENGTH_SHORT).show();
        double lat=address.getLatitude();
        double lng=address.getLongitude();
        goToLoactionzoom(lat,lng,15,mMap);
 //burada bir marker eklenince önceki var olan markerı siler
        setMarker(locality, lat, lng);

    }

    ArrayList<Marker>  markers=new ArrayList<Marker>();
    static final int POLYGON_POINTS=5;
    private void setMarker(String locality, double lat, double lng) {
//        if (this.marker !=null){
//this.marker.remove();
       // RemoveEverything();
//        }
        //bu kodun amacı her işret eklendiğinde öncekini silmektir.
        if (markers.size()==POLYGON_POINTS){
            RemoveEverything();
        }

        MarkerOptions markerOptions=new MarkerOptions()
                .title(locality)
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                .position(new LatLng(lat,lng))
                .snippet("Iam here");

markers.add(mMap.addMarker(markerOptions));
        if (markers.size()==POLYGON_POINTS){
            drawPolygon();
        }
//
//        if (marker1 ==null){
//            marker1= mMap.addMarker(markerOptions);
//        }
//        else if (marker2==null){
//            marker2= mMap.addMarker(markerOptions);
//            drawLine();
//        }
//        else {
//            RemoveEverything();
//            marker1= mMap.addMarker(markerOptions);
//
//        }


            // marker= mMap.addMarker(markerOptions);
   //     circle =  drawCircle(new LatLng(lat,lng));
    }

    private void drawPolygon() {
        PolygonOptions options=new PolygonOptions()
                .fillColor(0x33FF0000)
                .strokeWidth(3)
                .strokeColor(Color.RED);
        for (int i=0;i<POLYGON_POINTS;i++){
            options.add(markers.get(i).getPosition());

        }
shape=mMap.addPolygon(options);
    }

//
//    private Circle drawCircle(LatLng latLng) {
//
//        CircleOptions options=new CircleOptions()
//                .center(latLng)
//                .fillColor(0x33FF0000)
//                .radius(500)
//                .strokeColor(Color.BLUE)
//                .strokeWidth(3);
//
//        return mMap.addCircle(options);
//
//    }
    public void RemoveEverything(){
////        marker.remove();
////        marker=null;
////        circle.remove();
////        circle=null;

//        marker1.remove();
//        marker1=null;
//        marker2.remove();
//        marker2=null;
//        line.remove();
        for (Marker marker: markers){
            marker.remove();

        }
        markers.clear();
        shape.remove();
        shape=null;

//
//
}
//
//    private void drawLine() {
//        PolylineOptions option=new PolylineOptions()
//                .add(marker1.getPosition())
//                .add(marker2.getPosition())
//                .color(Color.BLUE)
//                .width(3);
//
//
//        line = mMap.addPolyline(option);
//
//
//    }



    public GoogleMap goToLoactionzoom(double lat, double lng,float zoom,GoogleMap gelenMap){
        LatLng ll =new LatLng(lat,lng);
        CameraUpdate update=CameraUpdateFactory.newLatLngZoom(ll,zoom);
        gelenMap.moveCamera(update);
        return gelenMap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

}

