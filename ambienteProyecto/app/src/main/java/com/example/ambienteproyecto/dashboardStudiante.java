package com.example.ambienteproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class dashboardStudiante extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboar_studiante);

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