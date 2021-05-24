package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VisitorPractitionersActivity extends AppCompatActivity {

    private Button btn_dcnx;
    private Button btn_praticien;
    private Button btn_rdv;
    private Button btn_frais;
    private Button btn_profil;
    private TextView tv_ident;
    private ListView lv_practitioners;
    private List<Practitioner> practitionerList;
    private Button btn_set_practitioner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_practitioners);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_praticien = findViewById(R.id.praticien);
        btn_rdv = findViewById(R.id.rdv);
        btn_frais = findViewById(R.id.frais);
        btn_profil = findViewById(R.id.profil);
        tv_ident = findViewById(R.id.tv_ident);
        lv_practitioners = findViewById(R.id.lv_practitioner);
        btn_set_practitioner = findViewById(R.id.btn_set_practitioner);

        Intent i_recu = getIntent();
        String ident_recu = i_recu.getStringExtra("ident");
        tv_ident.setText(ident_recu);

        // listage des praticiens

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        practitionerList = new ArrayList<>();

        db.collection("practitioners")
                .get() // récupère tout les praticiens de la collection
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("FIREC", document.getId() + " => " + document.getData());
                                practitionerList.add(new Practitioner(document));
                                //Log.d("testPendant", practitionerList.size()+""); // taille de la liste
                            }
                            // ajoute la liste des praticiens dans la listview
                            PractitionerAdapter pracAdap = new PractitionerAdapter(VisitorPractitionersActivity.this, practitionerList);
                            lv_practitioners.setAdapter(pracAdap); // affiche la listview
                        } else {
                            Log.d("FIREC", "Error getting documents: ", task.getException());
                        }
                    }
                });

        lv_practitioners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Practitioner current = (Practitioner) parent.getAdapter().getItem(position);
                String valeur = tv_ident.getText().toString();
                Intent practititionInfo = new Intent(VisitorPractitionersActivity.this, VisitorPractitionerInfoActivity.class);
                practititionInfo.putExtra("ident", valeur);
                practititionInfo.putExtra("practitionerInfo", current);
                startActivity(practititionInfo);
            }
        });

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(VisitorPractitionersActivity.this, MainActivity.class);
                startActivity(connexion);
            }
        });
        btn_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent rdv = new Intent(VisitorPractitionersActivity.this, VisitorCalendarActivity.class);
                rdv.putExtra("ident", valeur);
                startActivity(rdv);
            }
        });

        btn_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent frais = new Intent(VisitorPractitionersActivity.this, VisitorBundleMonthlyActivity.class);
                frais.putExtra("ident", valeur);
                startActivity(frais);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent profil = new Intent(VisitorPractitionersActivity.this, VisitorProfilActivity.class);
                profil.putExtra("ident", valeur);
                startActivity(profil);
            }
        });

        btn_set_practitioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent profil = new Intent(VisitorPractitionersActivity.this, VisitorSetPractitionerActivity.class);
                profil.putExtra("ident", valeur);
                startActivity(profil);
            }
        });

    }
}