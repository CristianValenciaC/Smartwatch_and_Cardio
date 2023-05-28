package com.rob.smartwatchcardio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class RegistroCuenta extends AppCompatActivity {

    EditText emailEditText, passwordEditText, confirmPasswordEditText;
    Spinner genderSpinner;
    Button createAccountBtn;
    ProgressBar progressBar;
    TextView loginBtnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirmPassword_edit_text);
        genderSpinner = findViewById(R.id.gender_spinner);
        createAccountBtn = findViewById(R.id.createAccount_button);
        progressBar = findViewById(R.id.progress_bar);
        loginBtnTextView = findViewById(R.id.register_text_view_link);

        createAccountBtn.setOnClickListener((v) -> createAccount());
        loginBtnTextView.setOnClickListener((v) -> startActivity(new Intent(RegistroCuenta.this, IniciarSesionActivity.class)));


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.sexos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        genderSpinner.setAdapter(adapter);

    }

    void createAccount(){

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        boolean isValidated = validateData(email, password, confirmPassword);

        if(!isValidated){
            return;
        }

        createAccountInFirebase(email, password);
    }


    void createAccountInFirebase(String email, String password){
        changeInProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistroCuenta.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //La creacion de cuenta ha terminado
                            Utility.showToast(RegistroCuenta.this, "Cuenta creada correctamente, revise su correo para verificar la cuenta");
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();

                        }else{
                            //Fallo en crear la cuenta
                            Utility.showToast(RegistroCuenta.this, task.getException().getLocalizedMessage());

                        }
                    }
                }
        );
    }

    /**
     * Este metodo ocultara o no el elemento ProgressBar cuando
     * se este procesando la petición
     * @param inProgress
     */
    void changeInProgress(boolean inProgress){

        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            createAccountBtn.setVisibility(View.GONE);

        }else{
            progressBar.setVisibility(View.GONE);
            createAccountBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Metodo para validar los datos de entrada de un usuario
     * @param email
     * @param password
     * @param confirmPassword
     * @return true o false dependiendo de si la validacion es correcta
     */
    boolean validateData(String email, String password, String confirmPassword){

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email incorrecto");

            return false;
        }

        if(password.length() < 6 ){
            passwordEditText.setError("La contraseña debe tener al menos 6 caracteres");

            return false;
        }

        if(!password.equals(confirmPassword)){
            confirmPasswordEditText.setError("Contraseñas distintas");

            return false;
        }

        return true;
    }

}