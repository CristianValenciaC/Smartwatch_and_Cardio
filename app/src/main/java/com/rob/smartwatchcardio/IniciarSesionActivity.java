package com.rob.smartwatchcardio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class IniciarSesionActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginBtn;
    ProgressBar progressBar;
    TextView registerBtnTextView;

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

        loginBtn.setOnClickListener((v) -> loginUser());
        registerBtnTextView.setOnClickListener((v) -> startActivity(new Intent(IniciarSesionActivity.this, RegistroCuenta.class)));

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
                        guardarUsuario(firebaseAuth);
                        startActivity(new Intent(IniciarSesionActivity.this, InicioPrincipal.class));

                    }else{
                        Utility.showToast(IniciarSesionActivity.this, "El email no ha sido verificado. Por favor, revisa tu correo");
                    }

                }else{
                    //Fallo en el login
                    Utility.showToast(IniciarSesionActivity.this, task.getException().getLocalizedMessage());

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
    void guardarUsuario(FirebaseAuth firebaseAuth){
        FirebaseUser usuario = firebaseAuth.getCurrentUser();
        String uidUsuario = usuario.getUid();

        database.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               boolean existe = false;
                //verificar si existe usuarios
               if (dataSnapshot.exists()){
                    //buscar usuario para saber si existe
                   existeUsuarioBase(uidUsuario);
                  Log.i("base",  "a");

                   /*esto funciona para crear pero no llega a reconocer que existe ya
                   * for (DataSnapshot ds : dataSnapshot.getChildren()) {
                       String uid = ds.child("uid").getValue().toString();
                        // si existe lo detenemos
                       if (uid == uidUsuario){
                           Log.i("base", "usuario existe");
                          existe= true;
                          break;
                       }

                   }
                   if (!existe){
                       Log.i("base", "No existe usuario, creando");
                       Map<String, Object> usuario = new HashMap<>();
                       usuario.put("nombre","manolo");
                       usuario.put("apellido","dominguez");
                       usuario.put("uid", uidUsuario);
                       database.child("Usuario").push().setValue(usuario);
                   }
*/




               } else {
                   Log.i("base", "directamente no existen usuarios, creando primero");
                   Map<String, Object> usuario = new HashMap<>();
                   usuario.put("nombre","manolo");
                   usuario.put("apellido","dominguez");
                   usuario.put("uid", uidUsuario);
                    database.setValue("Usuario");
                   database.child("Usuario").push().setValue(usuario);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    void existeUsuarioBase(String uidUsuario){
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