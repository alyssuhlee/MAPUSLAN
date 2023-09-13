package com.example.mapuslan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import org.jetbrains.annotations.Nullable;

public final class Welcome extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_welcome);

        Button loginButton = (Button)this.findViewById(R.id.homelogin_btn);
        Button signupButton = (Button)this.findViewById(R.id.homesignup_btn);
        loginButton.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent((Context)Welcome.this, LogIn.class);
                Welcome.this.startActivity(intent);
            }
        }));
        signupButton.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent((Context)Welcome.this, SignUp.class);
                Welcome.this.startActivity(intent);
            }
        }));
    }

}

