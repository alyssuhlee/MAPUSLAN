package com.example.mapuslan.userUI.camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.mapuslan.databinding.FragmentCameraUserBinding;
import com.example.mapuslan.objectdetection.UploadLiveActivity;
import com.example.mapuslan.objectdetection.detection.DetectorActivity;

public class CameraFragment extends Fragment {

    private FragmentCameraUserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCameraUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // real-time detection
        final Button buttonLive = binding.liveButton;
        buttonLive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent = new Intent(getContext(), DetectorActivity.class);
                startActivity(intent);
            }
        });

        // upload picture
        final Button buttonUpload = binding.uploadButton;
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent = new Intent(getContext(), UploadLiveActivity.class);
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