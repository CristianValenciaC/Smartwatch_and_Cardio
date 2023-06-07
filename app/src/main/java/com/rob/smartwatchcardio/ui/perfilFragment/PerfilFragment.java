package com.rob.smartwatchcardio.ui.perfilFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageButton;
import androidx.appcompat.widget.PopupMenu;
import com.google.firebase.auth.FirebaseAuth;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rob.smartwatchcardio.IniciarSesionActivity;
import com.rob.smartwatchcardio.R;
import com.rob.smartwatchcardio.SplashActivity;
import com.rob.smartwatchcardio.authorizationwatch.AuthorizationComprobate;
import com.rob.smartwatchcardio.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    FragmentPerfilBinding binding;
    ImageButton menuBtn;
    TextView nombreCompletoText, edadText, sexoText, emailText;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel notificationsViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        menuBtn = root.findViewById(R.id.logout_btn);
        menuBtn.setOnClickListener((v) -> showMenu());

        nombreCompletoText = root.findViewById(R.id.text_view_nombre_completo);
        edadText = root.findViewById(R.id.text_view_edad);
        sexoText = root.findViewById(R.id.text_view_sexo);
        emailText = root.findViewById(R.id.text_view_email);

        databaseReference = FirebaseDatabase.getInstance("https://smartwatch-and-cardio-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Obtención de datos

                if(dataSnapshot.exists()){
                    String nombreCompleto = dataSnapshot.child(firebaseUser.getUid()).child("nombre_completo").getValue().toString();
                    String edad = dataSnapshot.child(firebaseUser.getUid()).child("edad").getValue().toString();
                    String email = dataSnapshot.child(firebaseUser.getUid()).child("email").getValue().toString();
                    String sexo = dataSnapshot.child(firebaseUser.getUid()).child("sexo").getValue().toString();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nombreCompletoText.setText(nombreCompleto);
                            edadText.setText(edad);
                            sexoText.setText(sexo);
                            emailText.setText(email);
                        }
                    }, 100);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }

    void showMenu() {
        PopupMenu popupMenu = new PopupMenu(requireContext(), menuBtn);
        popupMenu.getMenu().add("Cerrar sesión");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Cerrar sesión")) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(requireContext(), IniciarSesionActivity.class));
                    requireActivity().finish();
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
