package com.example.gsb_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
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

    private EditText edt_email;
    private EditText edt_password;
    private Button btn_connexion;
    private Button btn_creer_compte;

    private static final String TAG = "EmailPassword";


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_connexion = (Button) findViewById(R.id.btn_connexion);
        btn_creer_compte = (Button) findViewById(R.id.btn_creer_compte);

        edt_password.setTransformationMethod(new PasswordTransformationMethod());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(MainActivity.this, "Authentication succeed.",
                                            Toast.LENGTH_SHORT).show();
                                    // FirebaseUser user = mAuth.getCurrentUser();
                                   // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                            }
                        });
            }
        });

        btn_creer_compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(MainActivity.this, "Creation succeed.",
                                            Toast.LENGTH_SHORT).show();
                                    // FirebaseUser user = mAuth.getCurrentUser();
                                    // updateUI(user);
                                } else {

                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Creation failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                            }
                        });
            }
        });






    }
/*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
*/
}