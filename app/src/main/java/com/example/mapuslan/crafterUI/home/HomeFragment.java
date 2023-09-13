package com.example.mapuslan.crafterUI.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mapuslan.databinding.FragmentHomeCrafterBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


public class HomeFragment extends Fragment {

    private FragmentHomeCrafterBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeCrafterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button buttonScanQR = binding.scanBtn;
        buttonScanQR.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               scanCode();
            }
        });

        return root;
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result ->
    {
        if (result.getContents() != null) {
            String userUsername = result.getContents();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

            checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        final Intent intent = new Intent(getContext(), AddPoint.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("un", userUsername);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(getActivity(),"Username does not exist",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
    });


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}