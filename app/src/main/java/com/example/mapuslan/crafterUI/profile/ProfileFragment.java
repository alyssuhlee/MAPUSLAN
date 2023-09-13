package com.example.mapuslan.crafterUI.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.mapuslan.Welcome;
import com.example.mapuslan.databinding.FragmentProfileCrafterBinding;


public class ProfileFragment extends Fragment {

    private FragmentProfileCrafterBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileCrafterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("username");

        TextView qrText = binding.qrText;
        qrText.setText(username);

        // logout button
        Button logOutButton = binding.logOutButton;
        logOutButton.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent((Context) getActivity(), Welcome.class);
                ProfileFragment.this.startActivity(intent);
                getActivity().finish();
            }
        }));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
