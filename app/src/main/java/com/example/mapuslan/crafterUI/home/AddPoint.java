package com.example.mapuslan.crafterUI.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapuslan.R;
import com.example.mapuslan.databinding.ActivityAddPointBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddPoint extends AppCompatActivity {
    private ActivityAddPointBinding binding;
    DatabaseReference reference;

    int user_point, corr_point, weight_value;
    String weight_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPointBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String value = getIntent().getExtras().get("un").toString();
        TextView username = binding.username;
        username.setText(value);



        RadioGroup rg = binding.radioWaste;

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_glass:
                        corr_point = 6;
                        break;
                    case R.id.radio_paper:
                        corr_point = 3;
                        break;
                    case R.id.radio_cardboard:
                        corr_point = 5;
                        break;
                    case R.id.radio_plastic:
                        corr_point = 10;
                        break;
                    case R.id.radio_metal:
                        corr_point = 8;
                        break;

                }
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.child(value).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    DataSnapshot dataSnapshot = task.getResult();
                    Long value= (Long) dataSnapshot.child("point").getValue();
                    user_point = value.intValue();

                }else {
                    Toast.makeText(AddPoint.this,"Failed to read!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        final Button buttonEnter = binding.enterBtn;
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText weight = binding.weightEnter;
                weight_text = weight.getText().toString();
                weight_value = Integer.parseInt(weight_text);

                user_point = user_point + weight_value * corr_point;

                reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.child(value).child("point").setValue(user_point);

                Toast.makeText(AddPoint.this,"Points added successfully!",Toast.LENGTH_SHORT).show();
                AddPoint.this.finish();

            }
        });

    }
}