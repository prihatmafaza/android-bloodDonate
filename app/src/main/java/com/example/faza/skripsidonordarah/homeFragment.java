package com.example.faza.skripsidonordarah;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment implements menuUserAdapter.dataListener {
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    String getUserID;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference reference,reference2;
    private ArrayList<Transaksi> listtransaksi;
    LinearLayoutManager mLayoutManager;
    String uid;

    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        auth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) view.findViewById(R.id.rc_menu);
        recyclerView.setHasFixedSize(true);
        MyRecyclerView();
        GetData();



        return view;
    }
    private void GetData(){
       // Toast.makeText(getActivity(),"Mohon Tunggu Sebentar...", Toast.LENGTH_LONG).show();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("transaksi").orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listtransaksi = new ArrayList<>();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Transaksi tr = snapshot.getValue(Transaksi.class);

                    tr.setKey(snapshot.getKey());
                    listtransaksi.add(tr);
                }
                menuUserAdapter adapter = new menuUserAdapter(listtransaksi,getActivity(),homeFragment.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });

    }
    private void MyRecyclerView(){
        mLayoutManager = new LinearLayoutManager(getActivity());

        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration did = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        did.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.line));
        recyclerView.addItemDecoration(did);
        recyclerView.setLayoutManager(mLayoutManager);
    }


    public void onDeleteData(Transaksi transaksi, int position) {
        if(reference != null){
            reference.child("transaksi").child(transaksi.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}
