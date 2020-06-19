package com.example.ambienteproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class revisarTarros extends AppCompatActivity {

    private ListView lvTar;
    private EditText buscarT;
    private Button busquedaT;
    private ImageButton helpT;

    private DatabaseReference myRef;

    List<Tarro> trro;
    List<Tarro> resultados;
    ArrayAdapter<Tarro> arrayAdapterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisar_tarros);

        lvTar = (ListView) findViewById(R.id.listaT);
        buscarT = (EditText) findViewById(R.id.wordT);
        busquedaT = (Button) findViewById(R.id.buscarT);
        helpT = (ImageButton) findViewById(R.id.helpT);

        trro = new ArrayList<Tarro>();
        resultados = new ArrayList<Tarro>();

        myRef = FirebaseDatabase.getInstance().getReference();

        helpT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerta("Los parametros que se pueden buscar son:\n"
                        + "1. Escribir todos (Trae todos los tarros)\n"
                        + "2. Escribir su idTarro(trae solo a ese tarro)");
            }
        });
    }
    public void alerta (String cadena){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(cadena);
        dialogBuilder.setCancelable(true).setTitle("Alerta");
        dialogBuilder.create().show();
    }
    public void mostrarT (View view){
        trro.clear();
        resultados.clear();
        final String valor = buscarT.getText().toString().trim();
        if (valor.equals("todos")) {
            myRef.child("tarro").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        Tarro tr = objSnapshot.getValue(Tarro.class);
                        trro.add(tr);
                        arrayAdapterUser = new ArrayAdapter<Tarro>(revisarTarros.this, android.R.layout.simple_list_item_1, trro);
                        lvTar.setAdapter(arrayAdapterUser);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else{
            trro.clear();
            resultados.clear();
            final int value = Integer.parseInt(valor);
            myRef.child("tarro").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        Tarro tr = objSnapshot.getValue(Tarro.class);
                        trro.add(tr);
                    }
                    for (int i = 0; i < trro.size(); i ++) {
                        if(value == trro.get(i).getIdTarro()) {
                            resultados.add(trro.get(i));
                            arrayAdapterUser = new ArrayAdapter<Tarro>(revisarTarros.this, android.R.layout.simple_list_item_1, resultados);
                            lvTar.setAdapter(arrayAdapterUser);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        buscarT.setText("");
    }
}