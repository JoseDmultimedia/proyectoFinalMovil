package com.example.ambienteproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class revisarUsuarios extends AppCompatActivity {

    private ListView lista;
    private EditText buscarA;
    private Button busqueda;
    private ImageButton helpUi;

    private DatabaseReference myRef;

    List<Usuario> user;
    List<Usuario> resultados;
    ArrayAdapter<Usuario> arrayAdapterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisar_usuarios);

        lista = (ListView) findViewById(R.id.list);
        buscarA = (EditText) findViewById(R.id.buscarU);
        busqueda = (Button) findViewById(R.id.look);
        helpUi = (ImageButton) findViewById(R.id.help);

        user = new ArrayList<Usuario>();
        resultados = new ArrayList<Usuario>();

        myRef = FirebaseDatabase.getInstance().getReference();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arrayAdapterUser, View view, int position, long id) {
                Usuario use = user.get(position);
                CallUpdateAndDeleteDialog(use.getNombre(), use.getCargo(), use.getUsername(), use.getPassword(), use.getId());
            }
        });

        helpUi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerta("Los parametros que se pueden buscar son:\n"
                                + "1. Escribir todos (Trae todos los usuarios)\n"
                                + "2. Escribir su username(trae solo a ese usuario)");
            }
        });
    }

    public void alerta (String cadena){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(cadena);
        dialogBuilder.setCancelable(true).setTitle("Alerta");
        dialogBuilder.create().show();
    }

    public void mostrar (View view){
        user.clear();
        resultados.clear();
        final String valor = buscarA.getText().toString().trim();
        if (valor.equals("todos")) {
            myRef.child("user").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        Usuario us = objSnapshot.getValue(Usuario.class);
                        user.add(us);
                        arrayAdapterUser = new ArrayAdapter<Usuario>(revisarUsuarios.this, android.R.layout.simple_list_item_1, user);
                        lista.setAdapter(arrayAdapterUser);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else{
            user.clear();
            resultados.clear();
            myRef.child("user").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        Usuario us = objSnapshot.getValue(Usuario.class);
                        user.add(us);
                    }
                    for (int i = 0; i < user.size(); i ++) {
                        if(valor.equals(user.get(i).getUsername())) {
                            resultados.add(user.get(i));
                            arrayAdapterUser = new ArrayAdapter<Usuario>(revisarUsuarios.this, android.R.layout.simple_list_item_1, resultados);
                            lista.setAdapter(arrayAdapterUser);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        buscarA.setText("");
    }

    private void CallUpdateAndDeleteDialog(String nombre, String cargo, String password, String username, final String id) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog2, null);
        dialogBuilder.setView(dialogView);

        //Definición de interfaces del layout update_dialog
        final EditText updateNombre = (EditText) dialogView.findViewById(R.id.updateNombre);
        final EditText updateCargo = (EditText) dialogView.findViewById(R.id.updateCargo);
        final EditText updateUsername = (EditText) dialogView.findViewById(R.id.updateUsername);
        final EditText updatePassword = (EditText) dialogView.findViewById(R.id.updatePassword);

        updateNombre.setText(nombre);
        updateCargo.setText(cargo);
        updateUsername.setText(username);
        updatePassword.setText(password);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUse);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUse);
        // seteo dialog title para el username

        dialogBuilder.setTitle(nombre);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Cuando presiono el boton actualizar
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = updateNombre.getText().toString();
                String cargo = updateCargo.getText().toString().trim();
                String usern = updateUsername.getText().toString().trim();
                String pass = updatePassword.getText().toString().trim();

                updateUse(name, cargo, usern, pass, id);
                b.dismiss();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUse(id);
                b.dismiss();
            }
        });
    }


    public boolean updateUse(String nombre, String cargo, String password, String username, String id){
        Usuario us = new Usuario(nombre, cargo, password, username, id);
        myRef.child("user").child(id).setValue(us);
        Toast.makeText(getApplicationContext(), "Usuario Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    public boolean  deleteUse(String id){
        myRef.child("user").child(id).removeValue();
        Toast.makeText(getApplicationContext(), "Usuario Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


}