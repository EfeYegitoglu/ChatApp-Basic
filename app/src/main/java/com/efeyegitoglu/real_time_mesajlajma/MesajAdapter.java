package com.efeyegitoglu.real_time_mesajlajma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.ViewHolder> {

     Context context;
     List<MesajModel> list;
     String userName;
     Boolean state=false;
     Activity activity;
     int view_send=1,view_received=2;

    public MesajAdapter(Context context, List<MesajModel> list, String userName, Activity activity) {
        this.context = context;
        this.list = list;
        this.userName = userName;
        state=false;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view;
       if (viewType==view_send){
           view= LayoutInflater.from(context).inflate(R.layout.send,parent,false);
           return new ViewHolder(view);
       }else
       {
           view= LayoutInflater.from(context).inflate(R.layout.received,parent,false);
           return new ViewHolder(view);
       }


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.textView.setText(list.get(position).getText().toString());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (state==true){
                textView=itemView.findViewById(R.id.sendText);

            }else {
                textView=itemView.findViewById(R.id.receivedText);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getFrom().equals(userName)){
             state=true;
             return view_send;
        }
        else {
            state=false;
            return view_received;
        }
    }
}
