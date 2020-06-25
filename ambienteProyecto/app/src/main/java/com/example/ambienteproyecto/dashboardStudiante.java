package com.example.ambienteproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dashboardStudiante extends AppCompatActivity {

    private EditText localizar;
    List<Tarro> algo;
    ArrayList algo2;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboar_studiante);
        localizar = (EditText) findViewById(R.id.tipoLocalizar);
        algo = new ArrayList<Tarro>();
        algo2 = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference();

    }

    public void local (View view){
        algo.clear();
        algo2.clear();
        final String type = localizar.getText().toString();
        myRef.child("tarro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                    Tarro tar = objSnapshot.getValue(Tarro.class);
                    algo.add(tar);
                }
                for (int i = 0; i < algo.size(); i ++ ){
                    if (type.equals(algo.get(i).getTipoTarro())){
                        algo2.add(algo.get(i).getIdTarro());
                    }
                }
                Toast.makeText(dashboardStudiante.this, "Estos son los id de los tarros donde se puede depositar" + algo2 ,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        localizar.setText("");
    }


    public void irDepositar (View view){
        Intent depo = new Intent(dashboardStudiante.this, depositar.class);
        startActivity(depo);
    }

    public void verMapUwu (View view){
        Intent uwu = new Intent(dashboardStudiante.this, mapitaUwu.class);
        startActivity(uwu);
    }

}