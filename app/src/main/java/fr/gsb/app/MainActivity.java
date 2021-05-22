package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.conscrypt.Conscrypt;

import java.security.Security;

public class MainActivity extends AppCompatActivity {

    private EditText edt_email, edt_password;
    private Button btn_login;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Security.insertProviderAt(Conscrypt.newProvider(), 1);
        setContentView(R.layout.main_activity);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            FirebaseAuth.getInstance().signOut();
        }

        this.edt_email = findViewById(R.id.identifiant);
        this.edt_password = findViewById(R.id.password);
        this.btn_login = findViewById(R.id.connexion);

        this.mAuth = FirebaseAuth.getInstance();

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


                                    DocumentReference docRef = db.collection("user").document(user.getEmail());
                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    Log.d("FIREC", "DocumentSnapshot data: " + document.getData());

                                                    User userFC = new User(document);

                                                    //Log.d("test", userFC.getRole());

                                                    /*if (getRole()=="visitor"){
                                                        Intent iVisitor = new Intent(MainActivity.this, VisitorIndexActivity.class);
                                                        startActivity(iVisitor);
                                                    }
                                                    else if (getRole()=="accountant"){
                                                        Intent iAcc = new Intent(MainActivity.this, AccountantIndexActivity.class);
                                                        startActivity(iAcc);
                                                    }*/
                                                    Intent i = new Intent(MainActivity.this, VisitorIndexActivity.class);
                                                    i.putExtra("userId", userFC.getId());
                                                    startActivity(i);

                                                } else {
                                                    Log.d("FIREC", "No such document");
                                                }
                                            } else {
                                                Log.d("FIREC", "get failed with ", task.getException());
                                            }
                                        }
                                    });



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