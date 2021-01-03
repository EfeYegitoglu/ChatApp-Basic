package com.efeyegitoglu.real_time_mesajlajma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reference;
    List<String> list;
    String userName;
    UserAdapter adapter;
    RecyclerView userRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tanimla();
        listele();
    }

    void tanimla(){
        userName=getIntent().getStringExtra("kAdi");
        reference= FirebaseDatabase.getInstance().getReference();
        list=new ArrayList<>();
        userRecyclerView=findViewById(R.id.userRecyclerView);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),3);
        userRecyclerView.setLayoutManager(layoutManager);
        adapter=  new UserAdapter(MainActivity.this,list,MainActivity.this,userName);
        userRecyclerView.setAdapter(adapter);
    }

    void listele(){
        reference.child("Kullanıcılar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (!dataSnapshot.getKey().equals(userName)){
                    list.add(dataSnapshot.getKey());
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
