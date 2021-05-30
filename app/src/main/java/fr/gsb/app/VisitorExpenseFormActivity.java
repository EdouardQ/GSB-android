package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VisitorExpenseFormActivity extends AppCompatActivity {

    private Button btn_dcnx;
    private Button btn_praticien;
    private Button btn_rdv;
    private Button btn_frais;
    private Button btn_profil;
    private TextView tv_ident;

    private EditText et_km;
    private EditText et_otherCost;
    private EditText et_paid;
    private Button btn_add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_bundle_monthly);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_praticien = findViewById(R.id.praticien);
        btn_rdv = findViewById(R.id.rdv);
        btn_frais = findViewById(R.id.frais);
        btn_profil = findViewById(R.id.profil);
        tv_ident = findViewById(R.id.tv_ident);

        et_km = findViewById(R.id.et_km);
        et_otherCost = findViewById(R.id.et_otherCost);
        et_paid = findViewById(R.id.et_paid);
        btn_add = findViewById(R.id.btn_add);

        Intent i_recu = getIntent();
        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si les champs ont bien été renseignés
                Boolean verif = Boolean.FALSE;
                if (et_otherCost.getText().length()!=0 && et_paid.getText().length()!=0){
                    verif = Boolean.TRUE;
                }
                if ((et_otherCost.getText().length()==0 && et_paid.getText().length()==0) && et_km.getText().length()!=0){
                    verif = Boolean.TRUE;
                }
                if (verif){
                    Map<String, Object> expenseFormMap = new HashMap<>();
                    expenseFormMap.put("date", new Timestamp(new Date()));
                    if (et_km.getText().length()==0){ // pour la conversion String -> Integer
                        et_km.setText("0");
                    }
                    expenseFormMap.put("km", Double.parseDouble(et_km.getText().toString()));  // String -> double
                    expenseFormMap.put("otherCost", et_otherCost.getText().toString());
                    if (et_paid.getText().length()==0){ // pour la conversion String -> double
                        et_paid.setText("0");
                    }
                    expenseFormMap.put("paid", Double.parseDouble(et_paid.getText().toString())); // String -> double
                    expenseFormMap.put("state", "Saisie clôturée");
                    expenseFormMap.put("userName", currentUser.getName());
                    expenseFormMap.put("userFirstName", currentUser.getFirstName());
                    expenseFormMap.put("userId", currentUser.getId());

                    db.collection("expense_forms")
                            .document(GenerateRandomString.randomString(20))
                            .set(expenseFormMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("FIREC", "DocumentSnapshot successfully written!");

                                    // redirection vers l'accueil
                                    Intent index = new Intent(VisitorExpenseFormActivity.this, VisitorIndexActivity.class);
                                    index.putExtra("currentUser", currentUser);
                                    startActivity(index);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("FIREC", "Error writing document", e);
                                }
                            });


                }
            }
        });


        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(VisitorExpenseFormActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });
        btn_praticien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent praticien = new Intent(VisitorExpenseFormActivity.this, VisitorPractitionersActivity.class);
                praticien.putExtra("currentUser", currentUser);
                startActivity(praticien);
            }
        });

        btn_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rdv = new Intent(VisitorExpenseFormActivity.this, VisitorCalendarActivity.class);
                rdv.putExtra("currentUser", currentUser);
                startActivity(rdv);
            }
        });
        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(VisitorExpenseFormActivity.this, VisitorProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });
    }
}