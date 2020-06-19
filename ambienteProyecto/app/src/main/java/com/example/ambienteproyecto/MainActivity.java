package com.example.ambienteproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText pass, user;
    List<Usuario> alguien;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pass = (EditText) findViewById(R.id.passT);
        user = (EditText) findViewById(R.id.userT);

        alguien = new ArrayList<Usuario>();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    public void login (View view){
        alguien.clear();
        final String midleUser = user.getText().toString();
        final String midlePass = pass.getText().toString();

        myRef.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                    Usuario persona = objSnapshot.getValue(Usuario.class);
                    alguien.add(persona);
                }
                for(int i = 0; i < alguien.size();i ++ ){
                    if (midleUser.equals(alguien.get(i).getUsername()) && midlePass.equals(alguien.get(i).getPassword())){
                        if(alguien.get(i).getCargo().equals("estudiante")){
                            Intent ventana1 = new Intent(MainActivity.this, dashboardStudiante.class);
                            startActivity(ventana1);
                        }
                        else if (alguien.get(i).getCargo().equals("profesor")){
                            Intent ventana2 = new Intent(MainActivity.this, dashboardStudiante.class);
                            startActivity(ventana2);
                        }
                        else if (alguien.get(i).getCargo().equals("admin")){
                            Intent ventana2 = new Intent(MainActivity.this, crudAdmin.class);
                            startActivity(ventana2);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
           /*
           if (midlePass.equals("1234")){
               Intent ventana2 = new Intent(MainActivity.this, crudAdmin.class);
               startActivity(ventana2);
           }
            */
    }

}