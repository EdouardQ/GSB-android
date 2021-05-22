package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends MyAppCompatActivity {

    private EditText edt_email, edt_password;
    private Button btn_login;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        this.edt_email = findViewById(R.id.identifiant);
        this.edt_password = findViewById(R.id.password);
        this.btn_login = findViewById(R.id.connexion);

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
                            public void onComplete(@NonNull Task<AuthResult> taskGetUser) {
                                if (taskGetUser.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("FIREB", "signInWithEmail:success");

                                    FirebaseUser user = mAuth.getCurrentUser();

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                                    db.collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .get().addOnCompleteListener(taskGetData -> {
                                        if(taskGetData.isSuccessful() && taskGetData.getResult() != null){
                                            setName(taskGetData.getResult().getString("name"));
                                            setFirstName(taskGetData.getResult().getString("firstName"));
                                            setRole(taskGetData.getResult().getString("role"));
                                            //other stuff
                                        }else{
                                            //deal with error
                                        }
                                    });

                                    Intent i = new Intent(MainActivity.this, VisitorIndexActivity.class);
                                    startActivity(i);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("FIREB", "signInWithEmail:failure", taskGetUser.getException());
                                    Toast.makeText(MainActivity.this, "Email ou mot de passe faux.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }
}