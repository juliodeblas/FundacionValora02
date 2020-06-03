package com.example.fundacionvalora02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText edit_email, edit_password;
    Button button_login;
    //TextView text_login;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instancias();
        acciones();
    }

    private void acciones() {
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        /*text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });*/
    }

    private void login() {
        String email = edit_email.getText().toString();
        String password = edit_password.getText().toString();

        if (email.isEmpty()) {
            edit_email.setError("Debe introducir un email.");
            edit_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edit_email.setError("Debe introducir un email correcto.");
            edit_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edit_password.setError("Debe introducir una contraseña.");
            edit_password.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edit_password.setError("La contraseña debe tener al menos 6 caracteres.");
            edit_password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "El email o la contraseña no son correctos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void instancias() {
        edit_email = findViewById(R.id.edit_email_login);
        edit_password = findViewById(R.id.edit_password_login);
        button_login = findViewById(R.id.button_login);
        // text_login = findViewById(R.id.text_signup);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar_login);
    }
}
