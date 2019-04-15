package com.example.arvindwholesale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
        displayName = findViewById(R.id.DisplayNameEditText);
        Address = findViewById(R.id.AddressEditText);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        db = FirebaseFirestore.getInstance();
    }

    public void Reset(View view) {
        UserName.setText("");
        PassWord.setText("");
        displayName.setText("");
        Address.setText("");
        CustomToast cT = new CustomToast(getApplicationContext(), "All fields cleared", Toast.LENGTH_LONG, true);
        cT.T.show();
    }

    public void Register(View view) {
        CollectionReference userDetails = db.collection("UsersDetails");
        if (!(PassWord.getText().toString().length() >= 6)) {
            PassWord.setError("Password Should be minimum 6 character long");
            CustomToast cT = new CustomToast(getApplicationContext(), "Password Should be minimum 6 character long", Toast.LENGTH_LONG, false);
            cT.T.show();
            return;
        }
        if (UserName.getText().toString().contains("@") && !displayName.getText().toString().equals("") && !PassWord.getText().toString().equals("") && !Address.getText().toString().equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(this.UserName.getText().toString(), this.PassWord.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName.getText().toString())
                                        .build();
                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        CustomToast cT = new CustomToast(getApplicationContext(), "Updated Name", Toast.LENGTH_LONG, true);
                                        cT.T.show();
                                    }
                                });
                                CustomToast cT = new CustomToast(getApplicationContext(), "SignUP Success", Toast.LENGTH_LONG, true);
                                cT.T.show();
                                onBackPressed();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    CustomToast cT = new CustomToast(getApplicationContext(), "SignUP failed, " + e.getMessage(), Toast.LENGTH_LONG, false);
                    cT.T.show();
                }
            });
        } else {
            CustomToast cT = new CustomToast(getApplicationContext(), "Enter Proper Details", Toast.LENGTH_LONG, false);
            cT.T.show();
        }
    }
}
