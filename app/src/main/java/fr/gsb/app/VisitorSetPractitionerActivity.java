package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisitorSetPractitionerActivity extends AppCompatActivity {
    private Button btn_dcnx;
    private Button btn_praticien;
    private Button btn_rdv;
    private Button btn_frais;
    private Button btn_profil;
    private Practitioner currentPractitioner;
    private TextView tv_ident;
    private EditText et_name;
    private EditText et_firstName;
    private EditText et_coeffReputation;
    private Spinner sp_workplace;
    private EditText et_address;
    private EditText et_city;
    private EditText et_postalCode;
    private Button btn_submit;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_set_practitioner);

        Intent i_recu = getIntent();

        btn_submit = findViewById(R.id.btn_submit);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_praticien = findViewById(R.id.praticien);
        btn_rdv = findViewById(R.id.rdv);
        btn_frais = findViewById(R.id.frais);
        btn_profil = findViewById(R.id.profil);
        tv_ident = findViewById(R.id.tv_ident);

        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        et_name = findViewById(R.id.et_name);
        et_firstName = findViewById(R.id.et_firstName);
        et_coeffReputation = findViewById(R.id.et_coeffReputation);
        sp_workplace = findViewById(R.id.sp_workplace);
        et_address = findViewById(R.id.et_address);
        et_city = findViewById(R.id.et_city);
        et_postalCode = findViewById(R.id.et_postalCode);
        btn_submit = findViewById(R.id.btn_submit);

        // initialisation du spinner et de la requete
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<String> workplaces = new ArrayList<String>();

        db.collection("workplaces")
                .get() // récupère tout les workplaces de la collection
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("FIREC", document.getId() + " => " + document.getData());
                                workplaces.add(new Workplace(document).getWording());
                            }
                            // ajoute la liste des workplace dans le spinner
                            adapter = new ArrayAdapter<String>(VisitorSetPractitionerActivity.this, android.R.layout.simple_spinner_dropdown_item, workplaces); // simple_spinner_dropdown_item existe de base
                            sp_workplace.setAdapter(adapter); // affiche le spinner
                        } else {
                            Log.d("FIREC", "Error getting documents: ", task.getException());
                        }

                        // s'il s'agit d'un éditage
                        if (i_recu.hasExtra("practitionerInfo")){
                            currentPractitioner = (Practitioner) getIntent().getSerializableExtra("practitionerInfo");
                            et_name.setText(currentPractitioner.getName());
                            et_firstName.setText(currentPractitioner.getFirstName());
                            et_coeffReputation.setText(Double.toString(currentPractitioner.getCoeffReputation())); // double -> String

                            // récupère la position de l'item "workplace" dans le spinner et le met en 1ère position
                            int spinnerPosition = adapter.getPosition(currentPractitioner.getWorkplace().getWording());
                            sp_workplace.setSelection(spinnerPosition);

                            et_address.setText(currentPractitioner.getAddress());
                            et_city.setText(currentPractitioner.getCity());
                            et_postalCode.setText(currentPractitioner.getPostalCode());
                        }
                        else{
                            // génère un id aléatoire sur 20 caractères
                            currentPractitioner = new Practitioner();
                            currentPractitioner.setId(GenerateRandomString.randomString(20));
                        }
                    }
                });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si un des EditText est vide
                if (et_name.getText().length()==0 || et_firstName.getText().length()==0 || et_coeffReputation.getText().length()==0 ||
                        et_address.getText().length()==0 || et_city.getText().length()==0 || et_postalCode.getText().length()==0){
                    Log.w("EditText", "failure: champs vide(s)");
                    Toast.makeText(VisitorSetPractitionerActivity.this, "Au moins un champs est vide",
                            Toast.LENGTH_SHORT).show();
                }
                // Si les champs ont bien tous été renseignés
                else{
                    Map<String, Object> practitionerMap = new HashMap<>();
                    practitionerMap.put("name", et_name.getText().toString());
                    practitionerMap.put("firstName", et_firstName.getText().toString());
                    practitionerMap.put("coeffReputation", Double.parseDouble(et_coeffReputation.getText().toString())); // String -> double
                    practitionerMap.put("workplace", db.document("workplaces/"+(sp_workplace.getSelectedItemPosition() + 1))); // +1 car la liste commence a l'index 0
                    practitionerMap.put("address", et_address.getText().toString());
                    practitionerMap.put("city", et_city.getText().toString());
                    practitionerMap.put("postalCode", et_postalCode.getText().toString());


                    db.collection("practitioners")
                            .document(currentPractitioner.getId())
                            .set(practitionerMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("FIREC", "DocumentSnapshot successfully written!");

                                    // redirection vers la liste des praticiens -> VisitorSetPractitionerActivity
                                    Intent praticien = new Intent(VisitorSetPractitionerActivity.this, VisitorPractitionersActivity.class);
                                    praticien.putExtra("currentUser", currentUser);
                                    startActivity(praticien);
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
                Intent connexion = new Intent(VisitorSetPractitionerActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });
        btn_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rdv = new Intent(VisitorSetPractitionerActivity.this, VisitorCalendarActivity.class);
                rdv.putExtra("currentUser", currentUser);
                startActivity(rdv);
            }
        });

        btn_praticien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent praticien = new Intent(VisitorSetPractitionerActivity.this, VisitorPractitionersActivity.class);
                praticien.putExtra("currentUser", currentUser);
                startActivity(praticien);
            }
        });

        btn_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais = new Intent(VisitorSetPractitionerActivity.this, VisitorExpenseFormActivity.class);
                frais.putExtra("currentUser", currentUser);
                startActivity(frais);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(VisitorSetPractitionerActivity.this, VisitorProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });
    }
}
