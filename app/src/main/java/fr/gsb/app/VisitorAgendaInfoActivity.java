package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

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
        tv_rdv = findViewById(R.id.tv_name);
        tv_user = findViewById(R.id.tv_firstName);
        tv_practitioner = findViewById(R.id.tv_coeffReputation);

        // affichage du rdv -> set
        tv_rdv.setText(String.format("%1$td-%1$tm-%1$tY", currentAgenda.getRdv()));
        tv_user.setText(currentAgenda.getUser().getFirstName() + currentAgenda.getUser().getName());
        tv_practitioner.setText(String.valueOf(currentAgenda.getPractitioner().getFirstName() + currentAgenda.getPractitioner().getName())); // double -> String

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

        btn_del_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setAgenda = new Intent(VisitorAgendaInfoActivity.this, VisitorSetPractitionerActivity.class);
                setAgenda.putExtra("currentUser", currentUser);
                setAgenda.putExtra("agendaInfo", (Serializable) currentAgenda);
                startActivity(setAgenda);
            }
        });
    }
}
