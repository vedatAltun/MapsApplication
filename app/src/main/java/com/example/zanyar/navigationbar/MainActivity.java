package com.example.zanyar.navigationbar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback{
Position position=new Position();
    private static GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_snapshot) {
            takeSnapshot(mMap);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
       @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_earth) {
            earth web2=new earth();
         fragmanetGoster(web2);
              } else if (id == R.id.nav_streetview) {
            Intent intent=new Intent(MainActivity.this,StreetView.class);
            startActivity(intent);
        } else if (id == R.id.nav_marker) {
            Intent intent=new Intent(MainActivity.this,marker.class);
            startActivity(intent);
        } else if (id == R.id.nav_places) {


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_autocomplete) {
            Intent intent=new Intent(MainActivity.this,autocomplete.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_position) {
            Position PositionFragment=new Position();
            fragmanetGoster(PositionFragment);
        }
        else if(id == R.id.nav_snapshot){
            snapshot snapshotFragment=new snapshot();
            fragmanetGoster(snapshotFragment);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void fragmanetGoster(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relativemap,fragment).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
mMap=googleMap;
       // mMap.setMyLocationEnabled(true);

    }
    public void getPosition(View view){
        Intent intent=new Intent(MainActivity.this,Position.class);
      startActivity(intent);
    }

    public void takeSnapshot( GoogleMap map1) {

        if (map1 == null) {
            Toast.makeText(getApplicationContext(),"Ekran Görüntüsü Alınamamıştır.",Toast.LENGTH_SHORT).show();
            return;
        }
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
            Bitmap bitmap;
            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                bitmap = snapshot;
                try {
                    FileOutputStream out = new FileOutputStream
                            ("/mnt/sdcard/Screenshots/Currentposition.png");
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    Toast.makeText(getApplicationContext(),"Ekran Görüntüsü Alınmıştır.",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
        map1.snapshot(callback);
    }
    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }
  }
