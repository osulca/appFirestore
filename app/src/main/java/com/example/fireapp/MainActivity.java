package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView tvResultado;
    FirebaseFirestore db;
    EditText etNombre, etApellido, etCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResultado = findViewById(R.id.tvResultado);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellidos);
        etCodigo = findViewById(R.id.etCodigo);

        db = FirebaseFirestore.getInstance();
        //db.collection("estudiantes").document("xer1UzuJhUxIiOylkvk6").delete();
        Map<String, Object> estudiante = new HashMap<>();
        estudiante.put("nombres", "Andrea");
        estudiante.put("apellidos", "Sanchez");
        estudiante.put("codigo", 1546329871);
        db.collection("estudiantes").document("vh7ujd3Wz0sjERC9YX8S")
                .update(estudiante);
    }

    public void TraerDatos(View view) {
        db.collection("estudiantes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            tvResultado.setText("");
                            for(QueryDocumentSnapshot document: task.getResult()){
                                tvResultado.append(
                                        document.getId()+"\n"+
                                        document.getData().get("nombres")+"\n"+
                                        document.getData().get("apellidos")+"\n"+
                                        document.getData().get("codigo")+"\n\n"
                                );
                            }
                        }else{
                            Log.e("FireApp", "Error", task.getException());
                        }
                    }
                });
    }

    public void EnviarDatos(View view) {
        String nombres = etNombre.getText().toString();
        String apellidos = etApellido.getText().toString();
        int codigo = Integer.parseInt(etCodigo.getText().toString());


        Map<String, Object> estudiante = new HashMap<>();
        estudiante.put("nombres", nombres);
        estudiante.put("apellidos", apellidos);
        estudiante.put("codigo", codigo);

        db.collection("estudiantes")
                .add(estudiante)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Guardado con exito, id: "+documentReference.getId(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FireApp", "Error", e);
                    }
                });
    }

    public void EliminarDatos(View view) {
    }
}