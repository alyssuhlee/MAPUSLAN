package com.example.mapuslan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mapuslan.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 5, 1},
        k = 1,
        d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000e"},
        d2 = {"Lcom/example/reglogin/LogIn;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/reglogin/databinding/ActivityLogInBinding;", "database", "Lcom/google/firebase/database/DatabaseReference;", "lookup", "", "username", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}
)
public final class LogIn extends AppCompatActivity {
    private ActivityLogInBinding binding;
    private DatabaseReference database;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLogInBinding var10001 = ActivityLogInBinding.inflate(this.getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(var10001, "ActivityLogInBinding.inflate(layoutInflater)");
        this.binding = var10001;
        var10001 = this.binding;
        if (var10001 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }

        this.setContentView((View)var10001.getRoot());
        TextView signupRedirectText = (TextView)this.findViewById(R.id.signupRedirectText);
        ActivityLogInBinding var10000 = this.binding;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }

        var10000.loginBtn.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                EditText var10000 = LogIn.access$getBinding$p(LogIn.this).loginUsername;
                Intrinsics.checkNotNullExpressionValue(var10000, "binding.loginUsername");
                String username = var10000.getText().toString();
                CharSequence var3 = (CharSequence)username;
                if (var3.length() > 0) {
                    LogIn.this.lookup(username);
                } else {
                    Toast.makeText((Context)LogIn.this, (CharSequence)"Please enter Username", Toast.LENGTH_SHORT).show();
                }

            }
        }));
        signupRedirectText.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent((Context)LogIn.this, SignUp.class);
                LogIn.this.startActivity(intent);
            }
        }));
    }

    private final void lookup(String username) {
        DatabaseReference var10001 = FirebaseDatabase.getInstance().getReference("Users");
        Intrinsics.checkNotNullExpressionValue(var10001, "FirebaseDatabase.getInst…e().getReference(\"Users\")");
        this.database = var10001;
        DatabaseReference var10000 = this.database;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("database");
        }

        var10000.child(username).get().addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
            // $FF: synthetic method
            // $FF: bridge method
            public void onSuccess(Object var1) {
                this.onSuccess((DataSnapshot)var1);
            }

            public final void onSuccess(DataSnapshot it) {
                if (it.exists()) {
                    DataSnapshot var10000 = it.child("password");
                    Intrinsics.checkNotNullExpressionValue(var10000, "it.child(\"password\")");
                    String password = String.valueOf(var10000.getValue());
                    var10000 = it.child("usertype");
                    Intrinsics.checkNotNullExpressionValue(var10000, "it.child(\"usertype\")");
                    String type = String.valueOf(var10000.getValue());
                    EditText loginPassword = (EditText)LogIn.this.findViewById(R.id.login_password);
                    EditText loginUsername = (EditText)LogIn.this.findViewById(R.id.login_username);
                    Intrinsics.checkNotNullExpressionValue(loginPassword, "loginPassword");
                    String loginPW = loginPassword.getText().toString();
                    if (Intrinsics.areEqual(password, loginPW)) {
                        Intent intent;
                        if (Intrinsics.areEqual(type, "User")) {
//                            intent = new Intent((Context)LogIn.this, UserHome.class);
                            intent = new Intent((Context)LogIn.this, UserHomeNav.class);
                            Intrinsics.checkNotNullExpressionValue(loginUsername, "loginUsername");
                            String loginUN = loginUsername.getText().toString();
                            intent.putExtra("username", loginUN);
                            LogIn.this.startActivity(intent);
                            finish();
                        } else {
//                            intent = new Intent((Context)LogIn.this, CrafterHome.class);
                            intent = new Intent((Context)LogIn.this, CrafterHomeNav.class);
                            String loginUN = loginUsername.getText().toString();
                            intent.putExtra("username", loginUN);
                            LogIn.this.startActivity(intent);
                            finish();
                        }
                    } else {
                        System.out.println(password);
                        System.out.println(loginPassword);
                        Toast.makeText((Context)LogIn.this, (CharSequence)"Username and Password Don't Match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText((Context)LogIn.this, (CharSequence)"User Doesn't Exist", Toast.LENGTH_SHORT).show();
                }

            }
        })).addOnFailureListener((OnFailureListener)(new OnFailureListener() {
            public final void onFailure(@NotNull Exception it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Toast.makeText((Context)LogIn.this, (CharSequence)"Failed", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    // $FF: synthetic method
    public static final ActivityLogInBinding access$getBinding$p(LogIn $this) {
        ActivityLogInBinding var10000 = $this.binding;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }

        return var10000;
    }

    // $FF: synthetic method
    public static final void access$setBinding$p(LogIn $this, ActivityLogInBinding var1) {
        $this.binding = var1;
    }
}
