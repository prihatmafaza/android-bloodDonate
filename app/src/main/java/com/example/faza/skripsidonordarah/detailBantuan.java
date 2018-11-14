package com.example.faza.skripsidonordarah;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class detailBantuan extends AppCompatActivity implements OnMapReadyCallback {
    private FirebaseAuth auth;
    private DatabaseReference database, database2;
    private GoogleMap mMap;
    long timez;
    String getUserID, uid  , day , curdate;
    TextView judul, desk, lok, nama,tanggalan,goldarcari;
    String namah, email, jk, notelp, goldars, umur, status,penolong,statuspen,penolong2;
    String namah2, email2, jk2, notelp2, goldars2, umur2,key;
    Button btn_bantuan;
    Double lat, longi;
    ImageView logoverif;

    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bantuan);
        auth = FirebaseAuth.getInstance();
        getUserID = auth.getCurrentUser().getUid().toString();
        Intent a = getIntent();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfrag);
        mapFragment.getMapAsync(this);
        setTitle("ShareBlood");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.krispi);

        nama = (TextView) findViewById(R.id.namaakun);
        judul = (TextView) findViewById(R.id.judul);
        desk = (TextView) findViewById(R.id.deskripsidetail);
        lok = (TextView) findViewById(R.id.alamatdetail);
        goldarcari = (TextView) findViewById(R.id.golcaridet);
        btn_bantuan = (Button) findViewById(R.id.btn_bantu);
        tanggalan = (TextView) findViewById(R.id.tanggalan);
        logoverif = (ImageView) findViewById(R.id.logoverif);
        logoverif.setVisibility(View.GONE);
        uid = a.getStringExtra("uid");
        penolong2 = a.getStringExtra("penolong");
        Bundle bundle = this.getIntent().getExtras();
        timez = bundle.getLong("time");
        lat = bundle.getDouble("latitude");
        longi = bundle.getDouble("longitude");
        SimpleDateFormat sfd = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        day= sfd.format(new Date(timez));

        status = a.getStringExtra("status");


        database = FirebaseDatabase.getInstance().getReference();
        database.child("user").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //userModel um = snapshot.getValue(userModel.class);
                    HashMap map = (HashMap) snapshot.getValue();
                    //userModel um = snapshot.getValue(userModel.class);
                    namah = (String) map.get("nama");
                    email = (String) map.get("email");
                    jk = (String) map.get("jenisKelamin");
                    notelp = (String) map.get("no_telepon");
                    goldars = (String) map.get("goldar");
                    umur = (String) map.get("umur");

                }
                //Toast.makeText(getApplicationContext(),""+namah, Toast.LENGTH_LONG).show();
                nama.setText("" + namah);
                if (uid.equals(getUserID)) {
                    if(status.equals("proses")){
                        btn_bantuan.setText("Konfirmasi Bantuan");
                        btn_bantuan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database2 = FirebaseDatabase.getInstance().getReference();
                                database2.child("user").child(penolong2).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            //userModel um = snapshot.getValue(userModel.class);
                                            //HashMap map = (HashMap) snapshot.getValue();
                                            userModel um = snapshot.getValue(userModel.class);
                                            namah2 = um.getNama();
                                            //namah2 = (String) map.get("nama");
                                            email2 = um.getEmail();
                                            jk2 = um.getJenisKelamin();
                                            notelp2 = um.getNo_telepon();
                                            goldars2 = um.getGoldar();
                                            umur2 = um.getUmur();
                                            statuspen = um.getStatus();
                                            um.setKey(snapshot.getKey());
                                            key = (String) um.getKey();

                                        }
                                        Calendar calendar = Calendar.getInstance();

                                        calendar.add(Calendar.MONTH, 3);
                                        Date tigabulan = calendar.getTime();
                                        userModel user = new userModel();
                                        user.setNama(namah2);
                                        user.setEmail(email2);
                                        user.setJenisKelamin(jk2);
                                        user.setNo_telepon(notelp2);
                                        user.setGoldar(goldars2);
                                        user.setUmur(umur2);
                                        user.setStatus("Deaktif Hingga "+tigabulan);
                                       // Toast.makeText(getApplicationContext()," "+tigabulan, Toast.LENGTH_LONG).show();
                                        database2.child("user").child(penolong2).child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                Transaksi transaksi = new Transaksi();
                                transaksi.setUid(uid);
                                transaksi.setNama(getIntent().getExtras().getString("nama"));
                                transaksi.setLok(getIntent().getExtras().getString("lok"));
                                transaksi.setGolcari(getIntent().getExtras().getString("goldar"));
                                transaksi.setDeskripsi(getIntent().getExtras().getString("desk"));
                                transaksi.setLatitude(getIntent().getExtras().getDouble("latitude"));
                                transaksi.setLongitude(getIntent().getExtras().getDouble("longitude"));
                                transaksi.setStatus("sudah");
                                transaksi.setPenolong(getIntent().getExtras().getString("penolong"));
                                updateData(transaksi);

                            }
                        });
                    }else if(status.equals("belum")){
                        btn_bantuan.setVisibility(View.GONE);
                    }else{
                        logoverif.setVisibility(View.VISIBLE);
                        btn_bantuan.setVisibility(View.GONE);
                    }
                } else {
                    if (status.equals("belum")){
                        database2 = FirebaseDatabase.getInstance().getReference();
                        database2.child("user").child(getUserID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    userModel um = snapshot.getValue(userModel.class);
                                    statuspen = um.getStatus();
                                }
                                    btn_bantuan.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(statuspen.equals("aktif")){
                                                penolong = auth.getCurrentUser().getUid().toString();

                                                Transaksi transaksi = new Transaksi();
                                                transaksi.setUid(uid);
                                                transaksi.setNama(getIntent().getExtras().getString("nama"));
                                                transaksi.setLok(getIntent().getExtras().getString("lok"));
                                                transaksi.setGolcari(getIntent().getExtras().getString("goldar"));
                                                transaksi.setDeskripsi(getIntent().getExtras().getString("desk"));
                                                transaksi.setLatitude(getIntent().getExtras().getDouble("latitude"));
                                                transaksi.setLongitude(getIntent().getExtras().getDouble("longitude"));
                                                transaksi.setStatus("proses");
                                                transaksi.setPenolong(penolong);

                                                updateData(transaksi);

                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:"+notelp));
                                                if (ActivityCompat.checkSelfPermission(detailBantuan.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    // TODO: Consider calling
                                                    //    ActivityCompat#requestPermissions
                                                    // here to request the missing permissions, and then overriding
                                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                    //                                          int[] grantResults)
                                                    // to handle the case where the user grants the permission. See the documentation
                                                    // for ActivityCompat#requestPermissions for more details.
                                                    ActivityCompat.requestPermissions(detailBantuan.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                                                    //startActivity(callIntent);
                                                    return;
                                                }
                                                startActivity(callIntent);

                                            }else{
                                                Toast.makeText(getApplicationContext()," "+statuspen, Toast.LENGTH_LONG).show();
                                            }
                                               // Toast.makeText(getApplicationContext(),"Lat : "+lat+"Long : "+longi, Toast.LENGTH_LONG).show();
                                        }
                                    });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }else if (status.equals("proses")){
                        btn_bantuan.setVisibility(View.GONE);

                    }else{
                        logoverif.setVisibility(View.VISIBLE);
                        btn_bantuan.setVisibility(View.GONE);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        tanggalan.setText(day);
        goldarcari.setText("Goldar: "+a.getStringExtra("goldar"));
        judul.setText(a.getStringExtra("nama"));
        desk.setText(a.getStringExtra("desk"));
        lok.setText(a.getStringExtra("lok"));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       // lat =  Double.parseDouble(getParentActivityIntent().getStringExtra("lat"));

        // Add a marker in Sydney and move the camera
        LatLng posisi = new LatLng(lat,     longi);
        mMap.addMarker(new MarkerOptions().position(posisi).title("Posisi Pasien"));

        CameraPosition campo =  new CameraPosition.Builder().target(posisi).zoom(17).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(campo),2000,null);
       //mMap.moveCamera(CameraUpdateFactory.newLatLng(posisi));
    }
    private void updateData(Transaksi transaksi){
        // String userID = auth.getUid();
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        database.child("transaksi").child(getKey).setValue(transaksi).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Snackbar.make(findViewById(R.id.btn_bantuan), "Data berhasil dirubah", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(detailBantuan.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void updateProfil(userModel userModel){

    }
}
