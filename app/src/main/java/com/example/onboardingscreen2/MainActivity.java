package com.example.onboardingscreen2;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView nextpage;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView addinfo,message,call100,emergencynum,womenslaw,tricksToSafe,nearbyLocation;
    ImageView sos;
    private FusedLocationProviderClient client;
    DatabaseHandler myDB;
    private final  int REQUEST_CODE=8989;
    private LocationSettingsRequest.Builder builder;
    String x="",y="";
    private  static final int REQUEST_LOCATION =1;
    LocationManager locationManager ;
    Intent mIntent;
    MediaPlayer mediaPlayer;
    int i=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);
        //addinfo = findViewById(R.id.addinfo);
        message = findViewById( R.id.message);
        call100 = findViewById(R.id.call100);
        emergencynum =  findViewById(R.id.emergencynum);
        womenslaw = findViewById(R.id.womenslaw);
        tricksToSafe = findViewById(R.id.tricksToSafe);
        nearbyLocation = findViewById(R.id.nearbylocation);
        sos = findViewById(R.id.sos);
        mIntent = new Intent(MainActivity.this,MyService.class);
        mediaPlayer = MediaPlayer.create(this,R.raw.emergency_alarm);
        myDB = new DatabaseHandler(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            onGPS();
        }else{
            startTrack();
        }


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

       /* addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this,ADDINFO.class);
                startActivity(intent);
            }
        });*/

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this,MessageFamily.class);
                startActivity(intent);

            }
        });
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                Handler handler= new Handler();
                Runnable runnable= new Runnable() {
                    @Override
                    public void run() {
                        i=0;

                    }
                };
                if(i==1){
                    startService(mIntent);
                    handler.postDelayed(runnable,400);
                }else if (i==2){
                    stopService(mIntent);

                }
            }
        });


        call100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:100"));
                startActivity(intent);
            }
        });
        emergencynum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EmergencyNumber.class);
                startActivity(intent);
            }
        });
        tricksToSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TricksToSafe.class);
                startActivity(intent);
            }
        });
         womenslaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,WomenSecurityLaw.class);
                startActivity(intent);
            }
        });
        nearbyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this,NearbyLocation.class);
                startActivity(intent);
            }
        });
         



    }

    private void loadData() {
        ArrayList<String> thelist = new ArrayList<>();
        Cursor data = myDB.getListContents();
        if(data.getCount()==0){
            Toast.makeText(this, "no content to show", Toast.LENGTH_SHORT).show();
        }else{
            String msg = "I NEED HELP LATITUDE:" +x+"LOGNITUDE:" +y;
            String num ="";

            while(data.moveToNext()){
                thelist.add(data.getString(1));
                num = num + data.getString(1)+(data.isLast()?"":";");

            }
        }
    }

    private void startTrack() {
    }

    private void onGPS() {
    }

}