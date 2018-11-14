package com.example.faza.skripsidonordarah;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class menuUserAdapter extends RecyclerView.Adapter<menuUserAdapter.ViewHolder> {
    public interface dataListener{
        void onDeleteData(Transaksi transaksi , int position);
    }

    private ArrayList<Transaksi> listtransaksi;
    private ArrayList<userModel> listuser;
    private Context context;
    private FirebaseAuth auth;
    private String getUserid;
    dataListener listener;


    public menuUserAdapter(ArrayList<Transaksi> listtransaksi, Context context, homeFragment fragment) {
        this.listtransaksi = listtransaksi;
        //this.listuser = listuser;
        this.context = context;
        listener = fragment;
    }

//    public void onAttach(Context context) {
//        super.onAttach(context);
//        listener = (dataListener) context;
//    }
//
//    public void onDetach(){
//        super.onDetach();
//        listener = null;
//    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama, lok , desk, namah, golcari;
        LinearLayout listitem;

        public ViewHolder(View itemView) {
            super(itemView);
           // namah = (TextView) itemView.findViewById(R.id.namaacc);
            nama = (TextView) itemView.findViewById(R.id.namarc);
            lok = (TextView) itemView.findViewById(R.id.lokasirc);
            desk = (TextView) itemView.findViewById(R.id.deksripsirc);
            golcari = (TextView) itemView.findViewById(R.id.golcari);
            listitem = itemView.findViewById(R.id.layli);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.desain_daftar,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String nama = listtransaksi.get(position).getNama();
        final String lok = listtransaksi.get(position).getLok();
        final String desk = listtransaksi.get(position).getDeskripsi();
        final String uid = listtransaksi.get(position).getUid();
        final Double lat = listtransaksi.get(position).getLatitude();
        final Double longi = listtransaksi.get(position).getLongitude();
        final long time = listtransaksi.get(position).getTime();
        final String goldar = listtransaksi.get(position).getGolcari();
        final String status = listtransaksi.get(position).getStatus();
        final String penolong = listtransaksi.get(position).getPenolong();
        //final String nama2 = listuser.get(position).getNama();

        holder.nama.setText(""+nama);
        holder.lok.setText(""+lok);
        holder.desk.setText(""+desk);
        holder.golcari.setText("Goldar:  "+goldar);
        //holder.namah.setText(""+nama2);
        holder.listitem.setTag(listtransaksi);
        holder.listitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("nama",listtransaksi.get(position).getNama());
                bundle.putString("desk",listtransaksi.get(position).getDeskripsi());
                bundle.putString("lok",listtransaksi.get(position).getLok());
                bundle.putString("uid",listtransaksi.get(position).getUid());
                bundle.putDouble("latitude",listtransaksi.get(position).getLatitude());
                bundle.putDouble("longitude",listtransaksi.get(position).getLongitude());
                bundle.putLong("time",listtransaksi.get(position).getTime());
                bundle.putString("goldar",listtransaksi.get(position).getGolcari());
                bundle.putString("status",listtransaksi.get(position).getStatus());
                bundle.putString("penolong",listtransaksi.get(position).getPenolong());
                bundle.putString("getPrimaryKey",listtransaksi.get(position).getKey());

                //bundle.putString("nama",listuser.get(position).getNama());
                Intent it = new Intent (v.getContext(), detailBantuan.class);
                it.putExtras(bundle);
                v.getContext().startActivity(it);
            }
        });
        auth = FirebaseAuth.getInstance();
        getUserid = auth.getCurrentUser().getUid().toString();
        holder.listitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                if(getUserid.equals(uid)){
                    final String[] action = {"Update","Delete"};
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setItems(action, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 0:
                                    Bundle bundle = new Bundle();
                                    bundle.putString("nama",listtransaksi.get(position).getNama());
                                    bundle.putString("desk",listtransaksi.get(position).getDeskripsi());
                                    bundle.putString("lok",listtransaksi.get(position).getLok());
                                    bundle.putString("uid",listtransaksi.get(position).getUid());
                                    bundle.putDouble("latitude",listtransaksi.get(position).getLatitude());
                                    bundle.putDouble("longitude",listtransaksi.get(position).getLongitude());
                                    bundle.putLong("time",listtransaksi.get(position).getTime());
                                    bundle.putString("getPrimaryKey",listtransaksi.get(position).getKey());

                                    //bundle.putString("nama",listuser.get(position).getNama());
                                    Intent it = new Intent (v.getContext(), editPostingBantuan.class);
                                    it.putExtras(bundle);
                                    context.startActivity(it);
                                    break;
                                case 1:
                                    listener.onDeleteData(listtransaksi.get(position),position);
                                    break;
                            }
                        }
                    });
                    alert.create();
                    alert.show();
                }
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return listtransaksi.size();
    }
}
