package com.example.arvindwholesale;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {
 FirebaseAuth mAuth;
 EditText username;
 Button Reset;
 TextView UserInteraction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        Reset=findViewById(R.id.Button_Reset);
        UserInteraction=findViewById(R.id.userInterraction);
        mAuth= FirebaseAuth.getInstance();
        username=findViewById(R.id.EditText_EmailID);
    }

    public void Reset(View view) {

        UserInteraction.setVisibility(View.VISIBLE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            UserInteraction.setBackgroundColor(getColor(R.color.colorAccent));
        }
        mAuth.sendPasswordResetEmail(username.getText().toString().trim()).addOnCompleteListener(this,
                new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Reset.setVisibility(View.INVISIBLE);
                    UserInteraction.setVisibility(View.VISIBLE);
                    UserInteraction.setText(getString(R.string.Mail_sent_text));
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                      UserInteraction.setBackgroundColor(getColor(R.color.colorPrimary));
                    }
                }else
                {
                    UserInteraction.setText(getString(R.string.Invalid_mail));
                    UserInteraction.setVisibility(View.VISIBLE);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        UserInteraction.setBackgroundColor(getColor(R.color.colorAccent));
                    }
                }
            }
        });
    }
}
