package com.example.mapuslan.userUI.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.mapuslan.databinding.FragmentHomeUserBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // points-material conversion list
        final Button buttonMaterials = binding.materialButton;
        buttonMaterials.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent = new Intent(getContext(), MaterialList.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}