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

import java.util.StringTokenizer;


public class depositar extends AppCompatActivity implements View.OnClickListener {

    private EditText nombrePro, tipoPro, tarroId;
    private Button qrAction, ingresarPro;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depositar);

        nombrePro = (EditText) findViewById(R.id.nombrePro);
        tipoPro = (EditText) findViewById(R.id.tipoPro);
        tarroId = (EditText) findViewById(R.id.idTarroPro);

        qrAction = (Button) findViewById(R.id.ingea);
        ingresarPro = (Button) findViewById(R.id.desechoIng);

        myRef = FirebaseDatabase.getInstance().getReference();
        qrAction.setOnClickListener(this);
    }
    public void onActivityResult (int requestCode, int resultCode, Intent inte) {
        //Se obtinene el resultado del proceso de escaneo y se parsea
        super.onActivityResult(requestCode, resultCode, inte);
        IntentResult sccaningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, inte);
        if (sccaningResult != null) {
            //desplegar el contenido obtenido de la imagen
            String scanContent = sccaningResult.getContents();
            StringTokenizer t = new StringTokenizer(scanContent, "*");
            final String name = t.nextToken();
            final String type = t.nextToken();
            final String idT = t.nextToken();

            // se depliegan los datos obtenidos a traves del escaneo de la imagen
            nombrePro.setText("" + name);
            tipoPro.setText("" + type);
            tarroId.setText("" + idT);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void ingresarProd (View view){
        String id = myRef.push().getKey();

        String valx = nombrePro.getText().toString().trim();
        String valy = tipoPro.getText().toString().trim();
        int valz = Integer.parseInt(tarroId.getText().toString());

        Producto pro = new Producto();

        pro.setNombreProduct(valx);
        pro.setTipo(valy);
        pro.setIdTarro(valz);

        myRef.child("producto").child(id).setValue(pro);

        Toast.makeText(this,"Se ha ingresado el producto \n"+ valx,Toast.LENGTH_LONG).show();

        limpiar();

    }


    public void limpiar (){
        nombrePro.setText("");
        tipoPro.setText("");
        tarroId.setText("");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ingea){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }
}