package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class VisitorAgendaInfoActivity extends AppCompatActivity {
    private Button btn_dcnx, btn_praticien, btn_rdv, btn_frais, btn_profil, btn_del_rdv;
    private Agenda currentAgenda;
    private TextView tv_ident, tv_rdv, tv_user, tv_practitioner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_agenda_info);

        Intent i_recu = getIntent();
        currentAgenda = (Agenda) i_recu.getSerializableExtra("agendaInfo");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btn_del_rdv = findViewById(R.id.btn_delete_rdv);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_praticien = findViewById(R.id.praticien);
        btn_rdv = findViewById(R.id.rdv);
        btn_frais = findViewById(R.id.frais);
        btn_profil = findViewById(R.id.profil);
        tv_ident = findViewById(R.id.tv_ident);

        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        // affichage du rdv -> find
        tv_rdv = findViewById(R.id.tv_rdv);
        tv_user = findViewById(R.id.tv_user);
        tv_practitioner = findViewById(R.id.tv_practitioner);

        // affichage du rdv -> set
        tv_rdv.setText("Le "+String.format("%1$td-%1$tm-%1$tY", currentAgenda.getRdv())+" à "+String.format("%1$tR", currentAgenda.getRdv()));
        tv_user.setText(currentAgenda.getUserName());
        tv_practitioner.setText(currentAgenda.getPractitioner());

        btn_del_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("agenda").document(currentAgenda.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("FIREC", "DocumentSnapshot successfully deleted!");
                                Intent rdv = new Intent(VisitorAgendaInfoActivity.this, VisitorCalendarActivity.class);
                                rdv.putExtra("currentUser", currentUser);
                                Toast.makeText(VisitorAgendaInfoActivity.this, "Rendez-vous supprimé.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(rdv);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("FIREC", "Error deleting document", e);
                            }
                        });
            }
        });

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(VisitorAgendaInfoActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });
        btn_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rdv = new Intent(VisitorAgendaInfoActivity.this, VisitorCalendarActivity.class);
                rdv.putExtra("currentUser", currentUser);
                startActivity(rdv);
            }
        });

        btn_praticien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent praticien = new Intent(VisitorAgendaInfoActivity.this, VisitorPractitionersActivity.class);
                praticien.putExtra("currentUser", currentUser);
                startActivity(praticien);
            }
        });

        btn_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais = new Intent(VisitorAgendaInfoActivity.this, VisitorExpenseFormActivity.class);
                frais.putExtra("currentUser", currentUser);
                startActivity(frais);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(VisitorAgendaInfoActivity.this, VisitorProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });

    }
}
