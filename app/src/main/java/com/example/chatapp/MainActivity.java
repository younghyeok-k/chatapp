package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText etpassword, etId;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        etId = (EditText) findViewById(R.id.etid);
        etpassword = (EditText) findViewById(R.id.etpassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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
                progressBar.setVisibility(View.VISIBLE);

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

                                    SharedPreferences sharedPref =getSharedPreferences("shared",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("email",stUserEmail);
                                    editor.commit();
                                    // updateUI(user);
                                    Intent in = new Intent(MainActivity.this, TapActivity.class);
                                    in.putExtra("email", stUserEmail);
                                    startActivity(in);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                    //   updateUI(null);
                                }
                                progressBar.setVisibility(View.GONE);
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
                progressBar.setVisibility(View.VISIBLE);
                //register ?????? ????????? @ ??????????????? ???????????? 6?????? ?????????????????????.
                mAuth.createUserWithEmailAndPassword(stEmail, stPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWith:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // updateUI(user);

                                    DatabaseReference myRef = database.getReference("users").child(user.getUid());


                                    Hashtable<String, String> numbers
                                            = new Hashtable<String, String>();
                                    numbers.put("email", user.getEmail());
                                    Toast.makeText(MainActivity.this,"Register Success",Toast.LENGTH_LONG).show();

                                    myRef.setValue(numbers);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithl:faile", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                    //   updateUI(null);
                                }
                                progressBar.setVisibility(View.GONE);
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