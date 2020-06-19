package com.example.ambienteproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ingresarTarros extends AppCompatActivity {

    private EditText idTar, tipoTar, latitud, longitud;
    private Button ingresarTarr, buscarUbicación;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_tarros);

        idTar = (EditText) findViewById(R.id.idTarro);
        tipoTar = (EditText) findViewById(R.id.tipoTarro);
        latitud = (EditText) findViewById(R.id.lati);
        longitud = (EditText) findViewById(R.id.longi);

        ingresarTarr = (Button) findViewById(R.id.ingresarTarro);
        buscarUbicación = (Button) findViewById(R.id.ubica);

        myRef = FirebaseDatabase.getInstance().getReference();

    }

    public void tarro(View view){
        String id = myRef.push().getKey();
        int val1 = Integer.parseInt(idTar.getText().toString());
        String val2 = tipoTar.getText().toString();
        String val3 = latitud.getText().toString();
        String val4 = longitud.getText().toString();

        Tarro tar = new Tarro();

        tar.setIdTarro(val1);
        tar.setTipoTarro(val2);
        tar.setLatitud(val3);
        tar.setLongitud(val4);

        myRef.child("tarro").child(id).setValue(tar);

        Toast.makeText(this,"Se ha ingresado el tarro \n"+ val1,Toast.LENGTH_LONG).show();

        limpiar();
    }

    public void limpiar(){
        idTar.setText("");
        tipoTar.setText("");
        latitud.setText("");
        longitud.setText("");
    }

    public void irMapitaUwu (View view){
        Intent uwu = new Intent(ingresarTarros.this, mapitaUwu.class);
        startActivity(uwu);
    }


}