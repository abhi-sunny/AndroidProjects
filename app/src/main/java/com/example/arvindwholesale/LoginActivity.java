package com.example.arvindwholesale;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public static FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private   EditText UserName;
    private   EditText PassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        UserName = findViewById(R.id.EditText_UserName);
        PassWord = findViewById(R.id.EditText_Password);
        progressBar = findViewById(R.id.progressBar);
    }

    public static void LogOut() {
        mAuth.signOut();
    }

    public void AttemptLogin(View view) {
        // Users CurrentUser=new Users(this.UserName.getText().toString(),this.PassWord.getText().toString());
        progressBar.setVisibility(View.VISIBLE);
        freezLayout(true);
        if (this.UserName.getText().toString().contains("@")) {
            mAuth.signInWithEmailAndPassword(this.UserName.getText().toString(), this.PassWord.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                /*Toast toast = Toast.makeText(getApplicationContext(), "LogIn Success", Toast.LENGTH_LONG);
                                View v=toast.getView();
                                v.setBackground(UserName.getBackground());
                                toast.setView(v);
                                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                                toast.show();*/
                                CustomToast C=new CustomToast(getApplicationContext()," Custom Toast Success",Toast.LENGTH_LONG,true);
                                C.T.show();
                                Intent itemlist = new Intent(getApplicationContext(), itemlist2.class);
                                startActivity(itemlist);
                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "LogIn failed," + e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }).addOnCanceledListener(this, new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Toast toast = Toast.makeText(getApplicationContext(), "LogIn failed,", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        } else {
            UserName.setError("Please Enter a Valid Email ID");

        }
        freezLayout(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void freezLayout(boolean freez) {
        findViewById(R.id.Button_Login).setEnabled(!freez);
        findViewById(R.id.Button_Register).setEnabled(!freez);
        findViewById(R.id.Button_Reset).setEnabled(!freez);
        UserName.setEnabled(!freez);
        PassWord.setEnabled(!freez);


    }
    private void launchForgotPassword() {
        Intent intent = new Intent(getApplicationContext(),Forgot_Password.class);
        startActivity(intent);
    }
    public void Register(View view)
    {
        mAuth.signOut();
        Intent Register = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(Register);
    }

    public void Reset(View view)
    {
        this.launchForgotPassword();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() !=null)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Welcome: "+ (mAuth.getCurrentUser().getDisplayName()!=null &&
                            !mAuth.getCurrentUser().getDisplayName().equals("null") ?
                             mAuth.getCurrentUser().getDisplayName()
                              :""),Toast.LENGTH_SHORT);
            toast.show();
            Intent itemlist = new Intent(getApplicationContext(),itemlist2.class);
            startActivity(itemlist);
        }
    }
}
