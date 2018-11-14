package com.example.faza.skripsidonordarah;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class editPostingBantuan extends AppCompatActivity {
    private EditText editJudul , editLok , editDesk;
    private Spinner spEditGoldar;
    private String[] editGoldar ={
            "A","O","B","AB"
    };
    double latEdit,longEdit;
    private int PLACE_PICKER_REQUEST = 1;
    private Button btnEdit;
    private DatabaseReference database;
    private FirebaseAuth auth;
    double lat;
    double longi;
    String getUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_posting_bantuan);

        editJudul = (EditText) findViewById(R.id.editNama);
        editLok = (EditText)findViewById(R.id.editLok);
        editDesk = (EditText) findViewById(R.id.editDsk);
        spEditGoldar = (Spinner) findViewById(R.id.spEditGoldar);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        auth = FirebaseAuth.getInstance();
        getUid = auth.getCurrentUser().getUid().toString();
        database = FirebaseDatabase.getInstance().getReference();
        getData();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item , editGoldar);
        spEditGoldar.setAdapter(adapter);
        spEditGoldar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(editPostingBantuan.this, "Selected "+ adapter.getItem(position), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editLok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(editPostingBantuan.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(editJudul.getText().toString()) && !isEmpty( editLok.getText().toString()) &&
                        !isEmpty(editDesk.getText().toString())){
                    Transaksi transaksi = new Transaksi();
                    transaksi.setUid(getUid);
                    transaksi.setNama(editJudul.getText().toString());
                    transaksi.setLok(editLok.getText().toString());
                    transaksi.setGolcari(spEditGoldar.getSelectedItem().toString());
                    transaksi.setDeskripsi(editDesk.getText().toString());
                    transaksi.setLatitude(lat);
                    transaksi.setLongitude(longi);
                    transaksi.setStatus("belum");
                    transaksi.setPenolong("kosong");
                    updateData(transaksi);

                }
            }
        });
    }

    private void getData(){
        final String getJudul = getIntent().getExtras().getString("nama");
        final String getDesk = getIntent().getExtras().getString("desk");
        final String getLok = getIntent().getExtras().getString("lok");
        final String getUid = getIntent().getExtras().getString("uid");
        final Double getLat = getIntent().getExtras().getDouble("latitude");
        final Double getLong = getIntent().getExtras().getDouble("longitude");
        final String getTime = getIntent().getExtras().getString("time");
        final String getGolcari = getIntent().getExtras().getString("golcari");

        editJudul.setText(getJudul);
        editDesk.setText(getDesk);
        editLok.setText(getLok);
        longi = getLong;
        lat = getLat;
       // spEditGoldar.getSelectedItem(getGolcari);


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
                Toast.makeText(editPostingBantuan.this,""+lat+ ""+longi, Toast.LENGTH_SHORT).show();

                editLok.setText(toastMsg);

            }
        }
    }
    private void updateData(Transaksi transaksi){
       // String userID = auth.getUid();
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        database.child("transaksi").child(getKey).setValue(transaksi).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                editJudul.setText("");
                editLok.setText("");
                editDesk.setText("");
                Snackbar.make(findViewById(R.id.btnEdit), "Data berhasil dirubah", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(editPostingBantuan.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
