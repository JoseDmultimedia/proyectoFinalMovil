package com.example.ambienteproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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


        lvTar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arrayAdapterUser, View view, int position, long id) {
                Tarro Tarro = trro.get(position);
                CallUpdateAndDeleteDialog(Tarro.getIdTarro(), Tarro.getTipoTarro(), Tarro.getLongitud(), Tarro.getLatitud(), Tarro.getId());
            }
        });

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

    private void CallUpdateAndDeleteDialog(final int idTar, String tipoTar, String lati, String longi, final String id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        //Definición de interfaces del layout update_dialog
        final EditText updateIdTarro = (EditText) dialogView.findViewById(R.id.updateIdTarro);
        final EditText updateTipoTarro = (EditText) dialogView.findViewById(R.id.updateTipoTarro);
        final EditText updateLongitud = (EditText) dialogView.findViewById(R.id.updateLongitud);
        final EditText updateLatitud = (EditText) dialogView.findViewById(R.id.updateLatitud);

        updateIdTarro.setText(String.valueOf(idTar));
        updateTipoTarro.setText(tipoTar);
        updateLongitud.setText(lati);
        updateLatitud.setText(longi);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateTarr);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteTarr);
        // seteo dialog title para el username

        dialogBuilder.setTitle(String.valueOf(idTar));

        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Cuando presiono el boton actualizar
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idTaro = Integer.parseInt(updateIdTarro.getText().toString());
                String tipo = updateTipoTarro.getText().toString().trim();
                String lati = updateLatitud.getText().toString().trim();
                String log = updateLongitud.getText().toString().trim();

                updateTarro(idTaro, tipo, lati, log, id);
                b.dismiss();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTarro(id);
                b.dismiss();
            }
        });
    }

    public boolean updateTarro(int idTar, String tipoTar, String lati, String longi, String id){
        Tarro tarro = new Tarro(idTar, tipoTar, lati, longi, id);
        myRef.child("tarro").child(id).setValue(tarro);
        Toast.makeText(getApplicationContext(), "Tarro Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean  deleteTarro(String id){
        myRef.child("tarro").child(id).removeValue();
        Toast.makeText(getApplicationContext(), "Tarro Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

}