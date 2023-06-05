package com.rob.smartwatchcardio;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.rob.smartwatchcardio.databinding.ActivityInicioPrincipalBinding;

public class InicioPrincipal extends AppCompatActivity {

    private ActivityInicioPrincipalBinding binding;

    ImageButton menuBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_inicio, R.id.navigation_historial, R.id.navigation_perfil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_inicio);
        NavigationUI.setupWithNavController(binding.navView, navController);


       /* menuBtn = findViewById(R.id.menu_btn);
        menuBtn.setOnClickListener((v)->showMenu());
        */
    }

    /*
    void showMenu(){

        PopupMenu popupMenu = new PopupMenu(InicioPrincipal.this, menuBtn);
        popupMenu.getMenu().add("Cerrar sesión");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle() == "Cerrar sesión") {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(InicioPrincipal.this, IniciarSesionActivity.class));
                    finish();
                }
                    return false;
            }
        });

    }*/
}