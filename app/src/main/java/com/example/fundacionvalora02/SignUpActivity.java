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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity {

    EditText edit_email_signup, edit_password_signup;
    Button button_signup;
    TextView text_login;

    ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        instancias();
        acciones();
    }

    private void acciones() {
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registrarUsuario() {
        String email = edit_email_signup.getText().toString().trim();
        String password = edit_password_signup.getText().toString().trim();

        if (email.isEmpty()) {
            edit_email_signup.setError("Debe introducir un email");
            edit_email_signup.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edit_email_signup.setError("Debe introducir un email correcto");
            edit_email_signup.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edit_password_signup.setError("Debe introducir una contraseña");
            edit_password_signup.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edit_password_signup.setError("La contraseña debe tener más de 6 caracteres");
            edit_password_signup.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Ese email ya está registrado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void instancias() {
        edit_email_signup = findViewById(R.id.edit_email_signup);
        edit_password_signup = findViewById(R.id.edit_password_signup);
        button_signup = findViewById(R.id.button_signup);
        text_login = findViewById(R.id.text_login);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar_signup);
    }
}
