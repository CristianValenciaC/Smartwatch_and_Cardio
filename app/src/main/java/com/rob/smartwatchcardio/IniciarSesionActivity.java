package com.rob.smartwatchcardio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import com.rob.smartwatchcardio.authorizationwatch.AuthorizationComprobate;

public class IniciarSesionActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private boolean passwordVisibility;
    private Button loginBtn;
    private ProgressBar progressBar;
    private TextView registerBtnTextView;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        database = FirebaseDatabase.getInstance("https://smartwatch-and-cardio-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginBtn = findViewById(R.id.iniciarSesion_button);
        progressBar = findViewById(R.id.progress_bar);
        registerBtnTextView = findViewById(R.id.register_text_view_btn);

        loginBtn.setOnClickListener(v -> loginUser());
        registerBtnTextView.setOnClickListener(v -> startActivity(new Intent(IniciarSesionActivity.this, RegistroCuenta.class)));

        passwordEditText.setOnTouchListener((view, event) -> {
            final int Right = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[Right].getBounds().width()) {
                    int selection = passwordEditText.getSelectionEnd();

                    if (passwordVisibility) {
                        passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);
                        passwordEditText.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                        passwordVisibility = false;
                    } else {
                        passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);
                        passwordEditText.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                        passwordVisibility = true;
                    }

                    passwordEditText.setSelection(selection);
                    return true;
                }
            }

            return false;
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean isValidated = validateData(email, password);

        if (!isValidated) {
            return;
        }

        loginAccountInFirebase(email, password);
    }

    private void loginAccountInFirebase(String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            changeInProgress(false);

            if (task.isSuccessful()) {
                // Login correcto
                startActivity(new Intent(IniciarSesionActivity.this, InicioPrincipal.class));
                // Ir al inicio de la aplicacion y comprobar que el acceso al reloj es correcto
                Intent i = new Intent(IniciarSesionActivity.this, AuthorizationComprobate.class);
                i.putExtra("stage", 0);
                startActivity(i);
            } else {
                // Fallo en el login
                Utility.showToast(IniciarSesionActivity.this, task.getException().getLocalizedMessage());
            }
        });
    }


    private void changeInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateData(String email, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email incorrecto");
            return false;
        }

        if (password.length() < 6) {
            passwordEditText.setError("La contraseña debe tener al menos 6 caracteres");
            return false;
        }

        return true;
    }

    private void guardarUsuario(FirebaseAuth firebaseAuth) {
        FirebaseUser usuario = firebaseAuth.getCurrentUser();
        String uidUsuario = usuario.getUid();

        database.child("Usuario").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean existe = false;

                if (dataSnapshot.exists()) {
                    existeUsuarioBase(uidUsuario);
                    Log.i("base", "a");
                } else {
                    Log.i("base", "directamente no existen usuarios, creando primero");
                    Map<String, Object> usuario = new HashMap<>();
                    usuario.put("uid", uidUsuario);
                    usuario.put("nombreCompleto", "manolo");
                    usuario.put("edad", 22);
                    usuario.put("genero", "m");

                    database.setValue("Usuario");
                    database.child("Usuario").push().setValue(usuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void existeUsuarioBase(String uidUsuario) {
        Query query = database.child("Usuario").orderByChild("uid").equalTo(uidUsuario).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("caso", "curioso");
                if (dataSnapshot.exists()) {
                    Log.i("existe", "si");
                    // El resultado de la consulta se encuentra en el dataSnapshot
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    }
                } else {
                    // No se encontraron resultados
                    System.out.println("No se encontraron resultados");
                    Log.i("existe", "no");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Se produjo un error al realizar la consulta
                System.out.println("Error al realizar la consulta: " + databaseError.getMessage());
            }
        });
    }
}
