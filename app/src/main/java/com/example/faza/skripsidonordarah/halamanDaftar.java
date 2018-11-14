package com.example.faza.skripsidonordarah;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class halamanDaftar extends AppCompatActivity {
    String getUserID,getEmailID;
    Button btnDaftar,golA,golB,golO,golAB;
    EditText fieldEmail,fieldPass;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final String TAG = halamanDaftar.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar);
        auth = FirebaseAuth.getInstance();
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(
                this.getResources().getColor(R.color.redpest));
        actionBar.setBackgroundDrawable(colorDrawable);
        setTitle("ShareBlood");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.krispi);
        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }else{
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        btnDaftar = (Button) findViewById(R.id.btnDaftarD);
        fieldEmail = (EditText) findViewById(R.id.fieldEmailD);
        fieldPass = (EditText) findViewById(R.id.fieldPassD);


        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(fieldEmail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Masukkan Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(fieldPass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Masukkan password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                daftar(fieldEmail.getText().toString(),fieldPass.getText().toString());

            }
        });
    }
    public void daftar(final String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    task.getException().printStackTrace();
                    Toast.makeText(halamanDaftar.this, "Proses Pendaftaran Gagal",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(halamanDaftar.this, "Proses Pendaftaran Berhasil\n" +
                                    "Email "+email,
                            Toast.LENGTH_SHORT).show();
                    getUserID = auth.getCurrentUser().getUid().toString();
                    getEmailID = auth.getCurrentUser().getEmail().toString();
                    Intent intent = new Intent(halamanDaftar.this, dataDiri.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("getUserID",getUserID);
                    bundle.putString("getEmailID",getEmailID);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(fStateListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (fStateListener != null) {
            auth.removeAuthStateListener(fStateListener);
        }
    }
}
