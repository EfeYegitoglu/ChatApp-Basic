package com.efeyegitoglu.real_time_mesajlajma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    String userName,otherName,key;
    ImageView backImage,sendImage;
    TextView chatUserName;
    EditText chatEditText;
    DatabaseReference reference;

    RecyclerView chatRecyView;
    MesajAdapter mesajAdapter;
    List<MesajModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tanimla();
        loadMesaj();
    }

    public void tanimla(){
        reference= FirebaseDatabase.getInstance().getReference();
        userName=getIntent().getStringExtra("username");
        otherName=getIntent().getStringExtra("othername");
        Log.i("okuu",userName+otherName);

        backImage=findViewById(R.id.backImage);
        chatUserName=findViewById(R.id.chatUserName);
        sendImage=findViewById(R.id.sendImage);
        chatEditText=findViewById(R.id.chatEditText);

        chatUserName.setText(otherName);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("kAdi",userName);
                startActivity(intent);
                finish();
            }
        });

        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String mesaj = chatEditText.getText().toString();
                    chatEditText.setText("");
                if (!mesaj.equals("")) {
                    mesajGonder(mesaj);
                }

            }
        });

        list=new ArrayList<>();
        chatRecyView=findViewById(R.id.chatRecyView);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),1);
        chatRecyView.setLayoutManager(layoutManager);
        mesajAdapter=new MesajAdapter(getApplicationContext(),list,userName,ChatActivity.this);
        chatRecyView.setAdapter(mesajAdapter);



    }

    public void mesajGonder(String text){
        key=reference.child("Mesajlar").child(userName).child(otherName).push().getKey();
        final Map messageMap=new HashMap();
        messageMap.put("text",text);
        messageMap.put("from",userName);
        reference.child("Mesajlar").child(userName).child(otherName).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()){

                  reference.child("Mesajlar").child(otherName).child(userName).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {

                      }
                  });
              }
            }
        });
    }


    void loadMesaj(){
        reference.child("Mesajlar").child(userName).child(otherName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MesajModel mesajModel=dataSnapshot.getValue(MesajModel.class);
                list.add(mesajModel);
                mesajAdapter.notifyDataSetChanged();
                chatRecyView.scrollToPosition(list.size()-1);
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
