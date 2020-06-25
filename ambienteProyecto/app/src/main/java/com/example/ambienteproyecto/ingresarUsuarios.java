package com.example.ambienteproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ingresarUsuarios extends AppCompatActivity {

    private EditText name, cargo, username, password;
    private Button ingresalo;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_usuarios);

        name = (EditText) findViewById(R.id.name);
        cargo = (EditText) findViewById(R.id.cargo);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        ingresalo = (Button) findViewById(R.id.ingresarUser);

        myRef = FirebaseDatabase.getInstance().getReference();

        ingresalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ingresaloA();
            }
        });
    }

    public void ingresaloA (){

        String id = myRef.push().getKey();
        String val1 = name.getText().toString();
        String val2 = cargo.getText().toString();
        String val3 = username.getText().toString();
        String val4 = password.getText().toString();


        Usuario alguien = new Usuario();

        alguien.setNombre(val1);
        alguien.setCargo(val2);
        alguien.setUsername(val3);
        alguien.setPassword(val4);
        alguien.setId(id);

        myRef.child("user").child(id).setValue(alguien);

        Toast.makeText(this,"Se ha ingresado el usuario \n"+ val1,Toast.LENGTH_LONG).show();

        limpiar();
    }

    public void limpiar(){
        name.setText("");
        cargo.setText("");
        username.setText("");
        password.setText("");
    }

}