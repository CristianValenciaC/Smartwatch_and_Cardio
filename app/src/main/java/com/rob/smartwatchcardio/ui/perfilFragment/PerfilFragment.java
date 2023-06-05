package com.rob.smartwatchcardio.ui.perfilFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import androidx.appcompat.widget.PopupMenu;
import com.google.firebase.auth.FirebaseAuth;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.rob.smartwatchcardio.IniciarSesionActivity;
import com.rob.smartwatchcardio.R;
import com.rob.smartwatchcardio.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    ImageButton menuBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel notificationsViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        menuBtn = root.findViewById(R.id.menu_btn);
        menuBtn.setOnClickListener((v) -> showMenu());

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
