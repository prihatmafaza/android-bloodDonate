package com.example.faza.skripsidonordarah;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference database;
    TextView profileNama, profileJK, profileGoldar , profileNotelp , profileEmail, profileUmur, profileStatus;
    Button btnKeluar;
    String getUserID;
    String namah, email, jk, notelp, goldars, umur, status;

    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        auth = FirebaseAuth.getInstance();
        getUserID = auth.getCurrentUser().getUid().toString();
        profileNama = (TextView) view.findViewById(R.id.profileName);
        profileJK = (TextView) view.findViewById(R.id.profileJK);
        profileGoldar = (TextView) view.findViewById(R.id.profileGoldar);
        profileNotelp = (TextView) view.findViewById(R.id.profileNotlp);
        profileEmail = (TextView) view.findViewById(R.id.profileEmail);
        profileUmur = (TextView) view.findViewById(R.id.profileUmur);
        profileStatus = (TextView) view.findViewById(R.id.profileStatus);
        btnKeluar = (Button) view.findViewById(R.id.btnKeluar);

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), halamanLogin.class));
                signOut();
                getActivity().finish();
            }
        });

        database = FirebaseDatabase.getInstance().getReference();
        database.child("user").child(getUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap map = (HashMap) snapshot.getValue();
                    //userModel um = snapshot.getValue(userModel.class);
                    namah = (String) map.get("nama");
                    email = (String) map.get("email");
                    jk = (String) map.get("jenisKelamin");
                    notelp = (String) map.get("no_telepon");
                    goldars = (String) map.get("goldar");
                    umur = (String) map.get("umur");
                    status = (String)map.get("status");
                }

                profileNama.setText(""+namah);
                profileJK.setText(""+jk);
                profileGoldar.setText(""+goldars);
                profileNotelp.setText(""+notelp);
                profileEmail.setText(""+email);
                profileUmur.setText(""+umur);
                profileStatus.setText(""+status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
    public void signOut(){
        auth.signOut();
    }




}
