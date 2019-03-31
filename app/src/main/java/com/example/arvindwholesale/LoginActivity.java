package com.example.arvindwholesale;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    public void AttemptLogin(View view) {
        // Users CurrentUser=new Users(this.UserName.getText().toString(),this.PassWord.getText().toString());
        progressBar.setVisibility(View.VISIBLE);
        if (this.UserName.getText().toString().contains("@")) {
            mAuth.signInWithEmailAndPassword(this.UserName.getText().toString(), this.PassWord.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast toast = Toast.makeText(getApplicationContext(), "LogIn Success", Toast.LENGTH_LONG);
                                toast.show();
                                Intent itemlist = new Intent(getApplicationContext(), itemlist2.class);
                                startActivity(itemlist);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast toast = Toast.makeText(getApplicationContext(), "LogIn failed", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }


                    });
        } else
        {
            UserName.setError("Please Enter a Valid Email ID");
            progressBar.setVisibility(View.GONE);
        }

    }
    public static void LogOut(){
        mAuth.signOut();
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
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() !=null)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Valid User: "+ mAuth.getCurrentUser().getDisplayName(),Toast.LENGTH_SHORT);
            toast.show();
            Intent itemlist = new Intent(getApplicationContext(),itemlist2.class);
            startActivity(itemlist);
        }
    }
}
