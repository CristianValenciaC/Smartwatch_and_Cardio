package com.rob.smartwatchcardio.ui.inicioFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.rob.smartwatchcardio.IniciarSesionActivity;
import com.rob.smartwatchcardio.R;
import com.rob.smartwatchcardio.RegistroCuenta;
import com.rob.smartwatchcardio.Requisitos;
import com.rob.smartwatchcardio.databinding.FragmentInicioBinding;

public class inicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private ImageButton iniciarTest;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inicioViewModel homeViewModel =
                new ViewModelProvider(this).get(inicioViewModel.class);

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniciarTest= getView().findViewById(R.id.iniciarTest1);
        iniciarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Requisitos.class));
            }
        });
    }
}