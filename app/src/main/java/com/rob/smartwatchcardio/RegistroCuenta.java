package com.rob.smartwatchcardio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroCuenta extends AppCompatActivity {

    // VARIABLES CON REF A LOS CAMPOS
    private EditText nombreCompletoEditText, emailEditText, passwordEditText, confirmPasswordEditText, edadEditText;
    private boolean passwordVisibility;
    private Spinner genderSpinner;
    private Button createAccountBtn;
    private ProgressBar progressBar;
    private TextView loginBtnTextView;

    // VARIABLES DE DATOS A REGISTRAR EN FIREBASE
    private String nombreCompleto, email, password, confirmPassword, edad, sexo;

    // VARIABLES PARA LA AUTENTICACIÓN EN FIREBASE
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cuenta);

        nombreCompletoEditText = findViewById(R.id.nombrecompleto_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirmPassword_edit_text);
        edadEditText = findViewById(R.id.edad_edit_text);
        genderSpinner = findViewById(R.id.gender_spinner);

        createAccountBtn = findViewById(R.id.createAccount_button);
        progressBar = findViewById(R.id.progress_bar);
        loginBtnTextView = findViewById(R.id.register_text_view_link);

        createAccountBtn.setOnClickListener(v -> createAccount());
        loginBtnTextView.setOnClickListener(v -> startActivity(new Intent(RegistroCuenta.this, IniciarSesionActivity.class)));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sexos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        genderSpinner.setAdapter(adapter);

        setupPasswordVisibilityListener(passwordEditText);
        setupPasswordVisibilityListener(confirmPasswordEditText);
    }

    private void setupPasswordVisibilityListener(EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= editText.getRight() - editText.getCompoundDrawables()[Right].getBounds().width()) {
                    togglePasswordVisibility(editText);
                    return true;
                }
            }
            return false;
        });
    }

    private void togglePasswordVisibility(EditText editText) {
        int selection = editText.getSelectionEnd();

        if (passwordVisibility) {
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordVisibility = false;
        } else {
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordVisibility = true;
        }

        editText.setSelection(selection);
    }

    private void createAccount() {
        nombreCompleto = nombreCompletoEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confirmPassword = confirmPasswordEditText.getText().toString();
        edad = edadEditText.getText().toString();
        sexo = genderSpinner.getSelectedItem().toString();

        if (!validateData(email, password, confirmPassword, nombreCompleto, edad, sexo)) {
            return;
        }

        createAccountInFirebase(nombreCompleto, email, password, edad, sexo);
    }

    private void createAccountInFirebase(String nombreCompleto, String email, String password, String edad, String sexo) {
        changeInProgress(true);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Validación de credenciales del usuario en Firebase
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistroCuenta.this,
                task -> {
                    if (task.isSuccessful()) {
                        //La creacion de cuenta ha terminado
                        Utility.showToast(RegistroCuenta.this, "Cuenta creada correctamente, revise su correo para verificar la cuenta");
                        firebaseAuth.getCurrentUser().sendEmailVerification();
                        firebaseAuth.signOut();

                        String idUserFirebase = firebaseAuth.getCurrentUser().getUid();

                        Map<String, Object> map = new HashMap<>();
                        map.put("nombre_completo", nombreCompleto);
                        map.put("email", email);
                        map.put("password", password);
                        map.put("edad", edad);
                        map.put("sexo", sexo);

                        // Validación en la creación del user en Firebase
                        databaseReference.child("Users").child(idUserFirebase).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task2) {
                                if(task2.isSuccessful()){
                                    startActivity(new Intent(RegistroCuenta.this, InicioPrincipal.class));
                                    finish();

                                }else{
                                    Utility.showToast(RegistroCuenta.this, "No se pudieron registrar los datos correctamente");
                                }
                            }
                        });

                    } else {
                        //Fallo en crear la cuenta
                        Utility.showToast(RegistroCuenta.this, task.getException().getLocalizedMessage());
                    }
                });
    }

    private void changeInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            createAccountBtn.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            createAccountBtn.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateData(String email, String password, String confirmPassword, String nombreCompleto, String edadString, String genderString) {
        if (nombreCompleto.isEmpty()) {
            nombreCompletoEditText.setError("Ingrese su nombre completo");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email incorrecto");
            return false;
        }

        if (password.length() < 6) {
            passwordEditText.setError("La contraseña debe tener al menos 6 caracteres");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Contraseñas distintas");
            return false;
        }

        if (edadString.isEmpty()) {
            edadEditText.setError("Ingrese su edad");
            return false;
        }

        int edad = Integer.parseInt(edadString);
        if (edad <= 0) {
            edadEditText.setError("Edad incorrecta");
            return false;
        }

        if (genderString.isEmpty()) {
            edadEditText.setError("Ingrese su sexo");
            return false;
        }

        return true;
    }
}