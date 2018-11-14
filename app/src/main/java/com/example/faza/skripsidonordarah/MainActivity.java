package com.example.faza.skripsidonordarah;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    String getUserID;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("ShareBlood");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.krispi);

        auth = FirebaseAuth.getInstance();


        getUserID = auth.getCurrentUser().getUid().toString();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, formBantuan.class);
                Bundle bundle = new Bundle();
                bundle.putString("getUserID",getUserID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user==null){
                    startActivity(new Intent(MainActivity.this,halamanLogin.class));
                    finish();
                }
            }
        };
        homeFragment homeFragment = new homeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, homeFragment);
        fragmentTransaction.commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavBot);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemHome:
                        homeFragment homeFragment = new homeFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content, homeFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.itemProfile:
                        profileFragment profileFragment = new profileFragment();
                        android.support.v4.app.FragmentTransaction fragmentProfTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentProfTransaction.replace(R.id.content, profileFragment);
                        fragmentProfTransaction.commit();
                        break;
                }

                return false;
            }
        });



    }
//    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//        //create a dialog to ask yes no question whether or not the user wants to exit
//    }



}
