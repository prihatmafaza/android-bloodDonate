package com.example.faza.skripsidonordarah;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.Placeholder;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class formBantuan extends AppCompatActivity {
    private DatabaseReference database;
    String getUserID;
    private EditText nama,lok,desk;
    private Spinner spinGol;
    private String[] goldar ={
            "A","O","B","AB"
    };
    double lat;
    double longi;
    private int PLACE_PICKER_REQUEST = 1;
    private Button btn_send;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    String email,goldars,jk,namah,notelp,umur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_bantuan);

        nama = (EditText) findViewById(R.id.f_nama);
        lok = (EditText) findViewById(R.id.f_lok);
        desk = (EditText) findViewById(R.id.f_dsk);
        spinGol = (Spinner) findViewById(R.id.sp_goldar);
        btn_send = (Button) findViewById(R.id.btn_send);
        auth = FirebaseAuth.getInstance();
        getUserID =  auth.getUid().toString();
        database = FirebaseDatabase.getInstance().getReference();
        setTitle("ShareBlood");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.krispi);
        lok.setKeyListener(null);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item , goldar);
        spinGol.setAdapter(adapter);
        lok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(formBantuan.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
//                Intent intent = new Intent(formBantuan.this, mapDonor.class);
//                startActivity(intent);
//                finish();
            }
        });

        spinGol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(formBantuan.this, "Selected "+ adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(nama.getText().toString())&&
                        !isEmpty(lok.getText().toString())&& !isEmpty(spinGol.getSelectedItem().toString())
                        && !isEmpty(desk.getText().toString())
                        ){
                    submit(new Transaksi(getUserID,nama.getText().toString(),lok.getText().toString(),spinGol.getSelectedItem().toString(),desk.getText().toString(),lat,longi,"belum","kosong"));
                    //submit2(new userModel(email,namah,notelp,goldars,jk,umur));
                }else{
                    Snackbar.make(findViewById(R.id.btn_send), "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                }
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nama.getWindowToken(),0);
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format(
                        "Place: %s \n" +
                                "Alamat: %s \n" +
                                "Latlng %s \n", place.getName(), place.getAddress(), place.getLatLng().latitude+" "+place.getLatLng().longitude);
                lat = place.getLatLng().latitude;
                longi= place.getLatLng().longitude;
                Toast.makeText(formBantuan.this,""+lat+ ""+longi, Toast.LENGTH_SHORT).show();

                lok.setText(toastMsg);

            }
        }
    }

    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void submit(Transaksi transaksi) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */

        database.child("transaksi").push().setValue(transaksi).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                nama.setText("");
                lok.setText("");
                desk.setText("");
                Snackbar.make(findViewById(R.id.btn_send), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(formBantuan.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, formBantuan.class);
    }
}
