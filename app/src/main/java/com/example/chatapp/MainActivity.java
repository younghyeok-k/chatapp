package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText etpassword, etId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = (EditText) findViewById(R.id.etid);
        etpassword = (EditText) findViewById(R.id.etpassword);
        mAuth = FirebaseAuth.getInstance();

        Button btnlogin = (Button) findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stEmail = etId.getText().toString();
                String stPassword = etpassword.getText().toString();
                //   Toast.makeText(MainActivity.this,"Login",Toast.LENGTH_LONG).show();
                if (stEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please insert Email", Toast.LENGTH_LONG).show();

                    return;
                }
                if (stPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please insert password", Toast.LENGTH_LONG).show();

                    return;
                }


                mAuth.signInWithEmailAndPassword(stEmail, stPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithCredential:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String stUserEmail = user.getEmail();
                                    String stUserName = user.getDisplayName();
                                    Log.d(TAG, "stUserEmail: " + stUserEmail + ",stUserName" + stUserName);
                                    // updateUI(user);
                                    Intent in = new Intent(MainActivity.this, ChatActivity.class);
                                    startActivity(in);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                    //   updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stEmail = etId.getText().toString();
                String stPassword = etpassword.getText().toString();
                if (stEmail.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please insert Email", Toast.LENGTH_LONG).show();

                    return;
                }
                if (stPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please insert password", Toast.LENGTH_LONG).show();

                    return;
                }
                //register 할때 아이디 @ 붙여야하고 비밀번호 6자리 이상이여야한다.
                mAuth.createUserWithEmailAndPassword(stEmail, stPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWith:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithl:faile", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                    //   updateUI(null);
                                }

                                // ...
                            }
                        });

            }

        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //   updateUI(currentUser);
    }
}