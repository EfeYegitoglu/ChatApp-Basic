package com.efeyegitoglu.real_time_mesajlajma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GirisActivity extends AppCompatActivity {

    EditText kullaniciAdiEditText;
    Button kayitOlButton;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        tanimla();

    }


    public void tanimla(){
        kullaniciAdiEditText=findViewById(R.id.kullaniciAdiEditText);
        kayitOlButton=findViewById(R.id.kayitOlButton);
        reference= FirebaseDatabase.getInstance().getReference();

        kayitOlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ekle(kullaniciAdiEditText.getText().toString());
            }
        });
    }


   void ekle(final String kAdi){

        reference.child("Kullanıcılar").child(kAdi).child("KullanıcıAdı").setValue(kAdi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Giriş Yapıldı",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("kAdi",kAdi);
                    startActivity(intent);
                    finish();
                }

            }
        });
   }
}
