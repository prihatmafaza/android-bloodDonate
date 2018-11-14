package com.example.faza.skripsidonordarah;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class halamanLogin extends AppCompatActivity {
    private EditText fieldID,fieldPass;
    private FirebaseAuth auth ;
    private Button btnLogin,btndaftar;
    private ProgressBar progressBar;
    String getUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);
        setTitle("ShareBlood");

        auth = FirebaseAuth.getInstance();
//        String getNama = auth.getCurrentUser().getDisplayName().toString();
//        Toast.makeText(getApplicationContext(), ""+getNama, Toast.LENGTH_SHORT).show();

        if (auth.getCurrentUser() != null) {
            getUserID = auth.getCurrentUser().getUid().toString();
            Intent intent = new Intent(halamanLogin.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("getUserID",getUserID);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

        fieldID = (EditText) findViewById(R.id.fieldID);
        fieldPass = (EditText) findViewById(R.id.fieldPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btndaftar = (Button) findViewById(R.id.btnDaftar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        CardView card = (CardView) findViewById(R.id.cardview);
        card.setBackgroundColor(Color.parseColor("#E6E6E6"));
        card.setMaxCardElevation(5);
        card.setRadius(5.0f);

        auth = FirebaseAuth.getInstance();
        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(halamanLogin.this, halamanDaftar.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = fieldID.getText().toString();
                final String password = fieldPass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Masukkan Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Masukkan password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(halamanLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                fieldPass.setError("PASSWORD EROR");
                            } else {
                                Toast.makeText(halamanLogin.this,"GAGAL LOGIN", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Intent intent = new Intent(halamanLogin.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("getUserID",getUserID);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }

                    }
                });

            }
        });

    }


}
