package com.example.mapuslan.userUI.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mapuslan.Welcome;
import com.example.mapuslan.databinding.FragmentProfileUserBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ProfileFragment extends Fragment {

    DatabaseReference reference;
    private FragmentProfileUserBinding binding;
    ValueEventListener valueEventListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("username");
        BarcodeEncoder encoder = new BarcodeEncoder();
        Bitmap bitmap = null;

        try {
            bitmap = encoder.encodeBitmap(username, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        ImageView qrCode = binding.myImageView;
        TextView qrText = binding.qrText;

        qrCode.setImageBitmap(bitmap);
        qrText.setText(username);

        Button logOutButton = binding.logOutButton;

        // logout button
        logOutButton.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent((Context) getActivity(), Welcome.class);
                ProfileFragment.this.startActivity(intent);
                getActivity().finish();
            }
        }));

        // automatically update the points in real-time once points have been added to user
        TextView userPoints = binding.pointText;
        reference = FirebaseDatabase.getInstance().getReference("Users");
        valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long value = dataSnapshot.child("point").getValue(Long.class);
                    if (value != null) {
                        String user_point = "Current Points: " + value.toString();
                        userPoints.setText(user_point);
                    } else {
                        Toast.makeText(getActivity(),"Failed to read!",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        reference.child(username).addValueEventListener(valueEventListener);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
