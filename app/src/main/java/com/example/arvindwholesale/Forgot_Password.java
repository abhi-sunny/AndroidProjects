package com.example.arvindwholesale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText username;
    Button Reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        Reset = findViewById(R.id.Button_Reset);
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.EditText_EmailID);
    }

    public void Reset(View view) {
        if (username.getText().toString().equals("") || !username.getText().toString().contains("@")) {
            username.setError("Enter a Valid Register Email ID");
            CustomToast cT = new CustomToast(getApplicationContext(), "Enter a Valid Register Email ID", Toast.LENGTH_LONG, false);
            cT.T.show();
            return;
        }
        username.setEnabled(false);
        mAuth.sendPasswordResetEmail(username.getText().toString().trim()).addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onBackPressed();
                            CustomToast cT = new CustomToast(getApplicationContext(), "A reset Mail have been sent", Toast.LENGTH_LONG, true);
                            cT.T.show();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                CustomToast cT = new CustomToast(getApplicationContext(), "Mail can not be sent, Reason" + e.getMessage(), Toast.LENGTH_LONG, false);
                cT.T.show();
                username.setEnabled(true);
            }
        });
    }
}
