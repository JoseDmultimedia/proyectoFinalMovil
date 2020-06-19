package com.example.ambienteproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class crudAdmin extends AppCompatActivity {

    private Button ingresarUP, ingresarTarro, revisarUsuarios, revisarTarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_admin);
        ingresarUP = (Button) findViewById(R.id.ingresarEP);
        ingresarTarro = (Button) findViewById(R.id.ingresarTarro);
        revisarUsuarios = (Button) findViewById(R.id.revisarUsuarios);
        revisarTarros = (Button) findViewById(R.id.revisarTarros);


        ingresarUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(crudAdmin.this, ingresarUsuarios.class);
                startActivity(in);
            }
        });

    }

    public void ingresarTar (View view) {
        Intent otro = new Intent(crudAdmin.this, ingresarTarros.class);
        startActivity(otro);
    }

    public  void revisarUse (View view){
        Intent lok = new Intent(crudAdmin.this, revisarUsuarios.class);
        startActivity(lok);
    }

    public void revisarTar(View view){
        Intent ter = new Intent(crudAdmin.this, revisarTarros.class);
        startActivity(ter);
    }
}