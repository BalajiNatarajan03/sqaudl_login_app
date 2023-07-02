package com.example.sqaudl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private EditText userNameEdt, passwordEdt;
    private Button loginBtn;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are getting data from our edit text.
                String userName = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                // checking if the entered text is empty or not.
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                    Toast.makeText(LogInActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
                }else {

                    //Toast.makeText(LogInActivity.this, logIn.getText(), Toast.LENGTH_SHORT).show();

                    loginUser(userName, password);

                }
            }
        });

    }

    private void loginUser(String userName, String password) {
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful
                            // Handle the user or navigate to the next screen
                            Toast.makeText(LogInActivity.this, "logged in.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Log.d("tagg",task.getException().toString());
                            if(task.getException().toString().contains("The email address is badly formatted")){
                                Toast.makeText(LogInActivity.this, "The email address is badly formatted",
                                        Toast.LENGTH_SHORT).show();
                            }
                            if(task.getException().toString().contains("The password is invalid")){
                                Toast.makeText(LogInActivity.this, "The password is invalid",
                                        Toast.LENGTH_SHORT).show();
                            }
                            if(task.getException().toString().contains("There is no user record corresponding to this identifier")){
                                registerUser(userName, password);
                            }
                        }
                    }
                });
    }

    private void registerUser(String userName, String password){
        mAuth
                .createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this,
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();
                            startActivity(new Intent(LogInActivity.this, MainActivity.class));
                            finish();
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            LogInActivity.this,
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                        }
                    }
                });
    }


}