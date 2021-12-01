package com.itla.vamoachatea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //Atributos de logueo
    private EditText edtUsername, edtPassword, edtEmail;
    private Button btnSubmit;
    private TextView txtLoginInfo;
    private boolean isSigningUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializando las variables
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
        btnSubmit = findViewById(R.id.btnSubmit);
        txtLoginInfo = findViewById(R.id.txtLoginInfo);

       if (FirebaseAuth.getInstance().getCurrentUser()!=null){
           startActivity(new Intent(MainActivity.this,MenuPrincipal.class));
            finish();
        }

        btnSubmit.setOnClickListener(view -> {

            if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()){
                if (isSigningUp && edtUsername.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (isSigningUp){
                handleSignUp();
            }else {
                handleLogin();
            }
        });

        //Metodo para saber si se logueara o registrara la persona
        txtLoginInfo.setOnClickListener(view -> {
            if (isSigningUp){
                isSigningUp = false;
                edtUsername.setVisibility(View.GONE);
                btnSubmit.setText("Loguearse");
                txtLoginInfo.setText("¿No tienes cuenta? Registrate");
            }else {
                isSigningUp = true;
                edtUsername.setVisibility(View.VISIBLE);
                btnSubmit.setText("Registrarse");
                txtLoginInfo.setText("¿Tienes cuenta? logueate");
            }
        });


    }
    //Para manejar el registro
    private void handleSignUp(){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                  FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new Usuario(edtUsername.getText().toString(),edtEmail.getText().toString(),""));
                   startActivity(new Intent(MainActivity.this,MenuPrincipal.class));
                    Toast.makeText(MainActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Para manejar el logueo
    private void handleLogin(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,MenuPrincipal.class));
                    Toast.makeText(MainActivity.this, "Logueado Correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}