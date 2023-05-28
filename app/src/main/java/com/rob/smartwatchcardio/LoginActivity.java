package com.rob.smartwatchcardio;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginBtn;
    ProgressBar progressBar;
    TextView registerBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginBtn = findViewById(R.id.iniciarSesion_button);
        progressBar = findViewById(R.id.progress_bar);
        registerBtnTextView = findViewById(R.id.register_text_view_btn);

        loginBtn.setOnClickListener((v) -> loginUser());
        registerBtnTextView.setOnClickListener((v) -> startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class)));

    }

    void loginUser(){

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean isValidated = validateData(email, password);

        if(!isValidated){
            return;
        }

        loginAccountInFirebase(email, password);
    }

    void loginAccountInFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);

                if(task.isSuccessful()){
                    //Login correcto

                    if(firebaseAuth.getCurrentUser().isEmailVerified()){

                        //Ir al inicio de la aplicacion
                        startActivity(new Intent(LoginActivity.this, InicioPrincipal.class));

                    }else{
                        Utility.showToast(LoginActivity.this, "El email no ha sido verificado. Por favor, revisa tu correo");
                    }

                }else{
                    //Fallo en el login
                    Utility.showToast(LoginActivity.this, task.getException().getLocalizedMessage());

                }
            }
        }));
    }

    /**
     * Este metodo ocultara o no el elemento ProgressBar cuando
     * se este procesando la petición
     * @param inProgress
     */
    void changeInProgress(boolean inProgress){

        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);

        }else{
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Metodo para validar los datos de entrada de un usuario
     * @param email
     * @param password
     * @return true o false dependiendo de si la validacion es correcta
     */
    boolean validateData(String email, String password){

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email incorrecto");

            return false;
        }

        if(password.length() < 6 ){
            passwordEditText.setError("La contraseña debe tener al menos 6 caracteres");

            return false;
        }

        return true;
    }
}