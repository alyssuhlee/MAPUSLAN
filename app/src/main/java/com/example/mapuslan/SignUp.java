package com.example.mapuslan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mapuslan.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 7, 1},
        k = 1,
        d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"},
        d2 = {"Lcom/example/reglogin/SignUp;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/reglogin/databinding/ActivitySignUpBinding;", "database", "Lcom/google/firebase/database/DatabaseReference;", "type", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "ObjectDetection.app.main"}
)
public final class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private DatabaseReference database;
    private String type = "User";
    private int point;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpBinding var10001 = ActivitySignUpBinding.inflate(this.getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(var10001, "ActivitySignUpBinding.inflate(layoutInflater)");
        this.binding = var10001;
        var10001 = this.binding;
        if (var10001 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }

        this.setContentView(var10001.getRoot());
        ActivitySignUpBinding var10000 = this.binding;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }

        SignUp.this.point = 0;

        var10000.radioUser.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                SignUp.this.type = "User";
                RadioButton var10000 = SignUp.access$getBinding$p(SignUp.this).radioCrafter;
                Intrinsics.checkNotNullExpressionValue(var10000, "binding.radioCrafter");
                var10000.setChecked(false);
            }
        }));
        var10000 = this.binding;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }

        var10000.radioCrafter.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                SignUp.this.type = "Crafter";
                RadioButton var10000 = SignUp.access$getBinding$p(SignUp.this).radioUser;
                Intrinsics.checkNotNullExpressionValue(var10000, "binding.radioUser");
                var10000.setChecked(false);
            }
        }));
        var10000 = this.binding;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }

        var10000.signupBtn.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                EditText var10000 = SignUp.access$getBinding$p(SignUp.this).username;
                Intrinsics.checkNotNullExpressionValue(var10000, "binding.username");
                String username = var10000.getText().toString();
                var10000 = SignUp.access$getBinding$p(SignUp.this).email;
                Intrinsics.checkNotNullExpressionValue(var10000, "binding.email");
                String email = var10000.getText().toString();
                var10000 = SignUp.access$getBinding$p(SignUp.this).password;
                Intrinsics.checkNotNullExpressionValue(var10000, "binding.password");
                String password = var10000.getText().toString();
                var10000 = SignUp.access$getBinding$p(SignUp.this).number;
                Intrinsics.checkNotNullExpressionValue(var10000, "binding.number");
                String number = var10000.getText().toString();
                String usertype = SignUp.this.type;
                SignUp var8 = SignUp.this;
                DatabaseReference var10001 = FirebaseDatabase.getInstance().getReference("Users");
                Intrinsics.checkNotNullExpressionValue(var10001, "FirebaseDatabase.getInst…e().getReference(\"Users\")");
                var8.database = var10001;

                CharSequence usernameChar = (CharSequence)username;
                CharSequence emailChar = (CharSequence)email;
                CharSequence passwordChar = (CharSequence)password;
                CharSequence numberChar = (CharSequence)number;
                if (usernameChar.length() <= 0 || emailChar.length() <= 0 || passwordChar.length() <= 0 || numberChar.length() <= 0 ) {
                    Toast.makeText((Context)SignUp.this, (CharSequence)"Please fill out all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(username, email, password, number, usertype, point);
                    SignUp.access$getDatabase$p(SignUp.this).child(username).setValue(user).addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
                        // $FF: synthetic method
                        // $FF: bridge method
                        public void onSuccess(Object var1) {
                            this.onSuccess((Void)var1);
                        }

                        public final void onSuccess(Void it) {
                            EditText var10000 = SignUp.access$getBinding$p(SignUp.this).username;
                            Intrinsics.checkNotNullExpressionValue(var10000, "binding.username");
                            var10000.getText().clear();
                            var10000 = SignUp.access$getBinding$p(SignUp.this).email;
                            Intrinsics.checkNotNullExpressionValue(var10000, "binding.email");
                            var10000.getText().clear();
                            var10000 = SignUp.access$getBinding$p(SignUp.this).password;
                            Intrinsics.checkNotNullExpressionValue(var10000, "binding.password");
                            var10000.getText().clear();
                            var10000 = SignUp.access$getBinding$p(SignUp.this).number;
                            Intrinsics.checkNotNullExpressionValue(var10000, "binding.number");
                            var10000.getText().clear();
                            Toast.makeText((Context)SignUp.this, (CharSequence)"Successfully Saved", Toast.LENGTH_SHORT).show();

                            Intent intent;
                            intent = new Intent((Context)SignUp.this, LogIn.class);
                            SignUp.this.startActivity(intent);

                        }
                    })).addOnFailureListener((OnFailureListener)(new OnFailureListener() {
                        public final void onFailure(@NotNull Exception it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            Toast.makeText((Context)SignUp.this, (CharSequence)"Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }
                    }));
                }
            }
        }));
        TextView loginRedirectText = (TextView)this.findViewById(R.id.loginRedirectText);
        loginRedirectText.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent((Context)SignUp.this, LogIn.class);
                SignUp.this.startActivity(intent);
            }
        }));
    }

    // $FF: synthetic method
    public static final ActivitySignUpBinding access$getBinding$p(SignUp $this) {
        ActivitySignUpBinding var10000 = $this.binding;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }

        return var10000;
    }

    // $FF: synthetic method
    public static final void access$setBinding$p(SignUp $this, ActivitySignUpBinding var1) {
        $this.binding = var1;
    }

    // $FF: synthetic method
    public static final DatabaseReference access$getDatabase$p(SignUp $this) {
        DatabaseReference var10000 = $this.database;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("database");
        }

        return var10000;
    }
}
