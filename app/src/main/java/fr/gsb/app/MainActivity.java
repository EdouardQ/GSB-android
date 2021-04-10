package fr.gsb.app;

import androidx.annotation.NonNull;

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

public class MainActivity extends MyAppCompatActivity {

    private EditText edt_email, edt_password;
    private Button btn_login;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.edt_email = findViewById(R.id.edt_email);
        this.edt_password = findViewById(R.id.edt_password);
        this.btn_login = findViewById(R.id.btn_login);

        this.mAuth = FirebaseAuth.getInstance();

        this.btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mAuth.signInWithEmailAndPassword(edt_email.getText().toString(), edt_password.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("FIREB", "signInWithEmail:success");

                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Intent i = new Intent(MainActivity.this, VisiteurMainActivity.class);
                                    startActivity(i);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("FIREB", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Email ou mot de passe faux.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }
}