package com.example.arvindwholesale;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private EditText UserName;
    private EditText PassWord;
    private EditText displayName;
    private EditText Address;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UserName = findViewById(R.id.EmailEditText);
        PassWord = findViewById(R.id.PassWordEditText);
        displayName =findViewById(R.id.DisplayNameEditText);
        Address = findViewById(R.id.AddressEditText);
        mAuth= FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        db=FirebaseFirestore.getInstance();
    }
    public void Reset(View view)
    {
        UserName.setText("");
        PassWord.setText("");
        displayName.setText("");
        Address.setText("");
        Toast toast =Toast.makeText(getApplicationContext(),"All Fields Cleared",Toast.LENGTH_LONG);
        toast.show();
    }
    public void Register(View view) {
        CollectionReference userDetails=db.collection("UsersDetails");
        if(!(PassWord.getText().toString().length()>=6))
        {
            PassWord.setError("Password Should be minimum 6 character long");
            Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Proper Details", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (UserName.getText().toString().contains("@") && !displayName.getText().toString().equals("") && !PassWord.getText().toString().equals("") && !Address.getText().toString() .equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(this.UserName.getText().toString(), this.PassWord.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {// Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName.getText().toString())
                                        .build();
                                if(user.updateProfile(profileUpdates).isSuccessful())
                            Toast.makeText(getApplicationContext(), "Profile update Success", Toast.LENGTH_LONG).show();
                                Toast toast = Toast.makeText(getApplicationContext(), "SignUP Success", Toast.LENGTH_LONG);

                                toast.show();
                                onBackPressed();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "SignUP failed, "+e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        } else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Proper Details", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
