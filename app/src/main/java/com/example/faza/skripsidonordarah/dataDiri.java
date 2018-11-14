package com.example.faza.skripsidonordarah;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class dataDiri extends AppCompatActivity {
    String getUserID,getEmailID;
    String status;
    private DatabaseReference database;
    private EditText nama,notelp,umur;
    private Button btnLengkap;
    private Spinner spinjk;
    private Spinner spingoldar;
    private Spinner spinrhesus;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener fStateListener;
    String[] goldar =  {
        "A","B","AB","O"
    };
    String[] rhesus = {
        "Positive (+)" , "Negative (-)" , "Tidak Tahu"
    };
    String[] jk = {
        "Laki - Laki" , "Perempuan"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_diri);
        setTitle("ShareBlood");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.krispi);

        nama = (EditText) findViewById(R.id.fieldnama);
        notelp = (EditText) findViewById(R.id.fieldnotelp);
        umur = (EditText) findViewById(R.id.fieldumur);
        spinjk = (Spinner) findViewById(R.id.spinJK);
        spingoldar = (Spinner) findViewById(R.id.spinGoldar);
        spinrhesus = (Spinner) findViewById(R.id.spinReshus);
        btnLengkap = (Button) findViewById(R.id.btnKirim);
        Intent a = getIntent();
        getUserID = a.getStringExtra("getUserID");
        getEmailID = a.getStringExtra("getEmailID");
        database = FirebaseDatabase.getInstance().getReference();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item , jk);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item , goldar);
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item , rhesus);
        spinjk.setAdapter(adapter);
        spingoldar.setAdapter(adapter2);
        spinrhesus.setAdapter(adapter3);

        spinjk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(dataDiri.this, "Selected "+ adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spingoldar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int y, long id) {
                Toast.makeText(dataDiri.this, "Selected "+ adapter2.getItem(y), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinrhesus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                Toast.makeText(dataDiri.this, "Selected "+ adapter3.getItem(p), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnLengkap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(nama.getText().toString())&& !isEmpty(notelp.getText().toString())&& !isEmpty(umur.getText().toString()) && !isEmpty(spinjk.getSelectedItem().toString()) && !isEmpty(spingoldar.getSelectedItem().toString()) && !isEmpty(spinrhesus.getSelectedItem().toString())
                        ){
                    submit(new userModel(getEmailID,
                            nama.getText().toString(),notelp.getText().toString(),spingoldar.getSelectedItem().toString(),
                            spinjk.getSelectedItem().toString(),umur.getText().toString(),"aktif"));

                }else{
                    Toast.makeText(dataDiri.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nama.getWindowToken(),0);
            }
        });
    }
    private void submit(userModel user){
        database.child("user").child(getUserID).push().setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                nama.setText("");
                notelp.setText("");
                umur.setText("");
               // Snackbar.make(findViewById(R.id.btn_send), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(dataDiri.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("getUserID",getUserID);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

    }
    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }
}
