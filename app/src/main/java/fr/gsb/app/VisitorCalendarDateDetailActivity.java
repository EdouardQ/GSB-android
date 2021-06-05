package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class VisitorCalendarDateDetailActivity extends AppCompatActivity {

    private TextView tv_ident;
    private Button btn_dcnx, btn_praticien, btn_rdv, btn_frais, btn_profil;
    private ListView lv_rdv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_calendar_date_detail);

        Intent i_recu = getIntent();

        String date_select;
        date_select = i_recu.getStringExtra("date_select");

        // Partie sur la navigation de l'appli
        this.tv_ident = findViewById(R.id.tv_ident);
        this.btn_dcnx = findViewById(R.id.deconnexion);
        this.btn_praticien = findViewById(R.id.praticien);
        this.btn_rdv = findViewById(R.id.rdv);
        this.btn_frais = findViewById(R.id.frais);
        this.btn_profil = findViewById(R.id.profil);
        this.lv_rdv = findViewById(R.id.lv_rdv);

        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<Agenda> agendaList = new ArrayList<Agenda>();

        db.collection("agenda")
                .get() // récupère tout les rdv de la collection
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Agenda currentAgenda = new Agenda(document);

                                if ((String.format("%1$td-%1$tm-%1$tY", currentAgenda.getRdv())).equals(date_select)) {
                                    agendaList.add(currentAgenda);
                                }
                            }
                            // ajoute la liste des rdv dans la listview
                            AgendaAdapter agenAdap = new AgendaAdapter(VisitorCalendarDateDetailActivity.this, agendaList);
                            lv_rdv.setAdapter(agenAdap); // affiche la listview
                        } else {
                            Log.d("FIREC", "Error getting documents: ", task.getException());
                        }
                    }
                });

        this.lv_rdv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Agenda current = (Agenda) parent.getAdapter().getItem(position);
                if (current.getUserId().equals(currentUser.getId())){ // si le rdv appartient à l'utilisateur actuel
                    Intent agendaInfo = new Intent(VisitorCalendarDateDetailActivity.this, VisitorAgendaInfoActivity.class);
                    agendaInfo.putExtra("currentUser", currentUser);
                    agendaInfo.putExtra("agendaInfo", (Serializable) current);
                    startActivity(agendaInfo);
                }
                else {
                    Toast.makeText(VisitorCalendarDateDetailActivity.this, "Ce rendez-vous ne vous est pas assigné.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(VisitorCalendarDateDetailActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });
        this.btn_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rdv = new Intent(VisitorCalendarDateDetailActivity.this, VisitorCalendarActivity.class);
                rdv.putExtra("currentUser", currentUser);
                startActivity(rdv);
            }
        });

        this.btn_praticien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent praticien = new Intent(VisitorCalendarDateDetailActivity.this, VisitorPractitionersActivity.class);
                praticien.putExtra("currentUser", currentUser);
                startActivity(praticien);
            }
        });

        this.btn_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais = new Intent(VisitorCalendarDateDetailActivity.this, VisitorExpenseFormActivity.class);
                frais.putExtra("currentUser", currentUser);
                startActivity(frais);
            }
        });

        this.btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(VisitorCalendarDateDetailActivity.this, VisitorProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });
    }
}
