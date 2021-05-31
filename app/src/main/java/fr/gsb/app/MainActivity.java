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
                //message d'erreur si champ vide
                if (edt_email.getText().length()==0 || edt_password.getText().length()==0){
                    Log.w("FIREB", "signInWithEmail:failure champs vide");
                    Toast.makeText(MainActivity.this, "\"Email et/ou mot de passe incorrect(s).\"",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    mAuth.signInWithEmailAndPassword(edt_email.getText().toString(), edt_password.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> taskGetUser) {
                                    if (taskGetUser.isSuccessful()) {
                                        // Succès de connexion
                                        Log.d("FIREB", "signInWithEmail:success");

                                        FirebaseUser user = mAuth.getCurrentUser();

                                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                                        DocumentReference docRef = db.collection("users").document(user.getEmail());
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        //Log.d("FIREC", "DocumentSnapshot data: " + document.getData());

                                                        User userFC = new User(document);
                                                        //envoi vers le menu visiteur avec les infos visiteur
                                                        if (userFC.getRole().equals("visitor")) {
                                                            Intent iVisitor = new Intent(MainActivity.this, VisitorIndexActivity.class);
                                                            iVisitor.putExtra("currentUser", userFC);
                                                            startActivity(iVisitor);
                                                        }
                                                        //envoi vers le menu comptable avec les infos comptable
                                                        else if (userFC.getRole().equals("accountant")) {
                                                            Intent iAccountant = new Intent(MainActivity.this, AccountantIndexActivity.class);
                                                            iAccountant.putExtra("currentUser", userFC);
                                                            startActivity(iAccountant);
                                                        }

                                                    } else {
                                                        Log.d("FIREC", "No such document");
                                                    }
                                                } else {
                                                    Log.d("FIREC", "get failed with ", task.getException());
                                                }
                                            }
                                        });


                                    } else {
                                        // Message erreur de login.
                                        Log.w("FIREB", "signInWithEmail:failure", taskGetUser.getException());
                                        Toast.makeText(MainActivity.this, "Email et/ou mot de passe incorrect(s).",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}