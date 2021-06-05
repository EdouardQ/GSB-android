package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class VisitorCalendarActivity extends AppCompatActivity {

    private Button btn_consult_rdv, btn_add_rdv, btn_dcnx, btn_praticien, btn_rdv, btn_frais, btn_profil;
    private TextView tv_ident;
    private DatePicker dp_agenda;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_calendar);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_praticien = findViewById(R.id.praticien);
        btn_frais = findViewById(R.id.frais);
        btn_profil = findViewById(R.id.profil);
        btn_add_rdv = findViewById(R.id.btn_add_rdv);
        btn_consult_rdv = findViewById(R.id.btn_consult_rdv);
        tv_ident = findViewById(R.id.tv_ident);
        dp_agenda = findViewById(R.id.dp_agenda);

        Intent i_recu = getIntent();
        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(VisitorCalendarActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });
        btn_praticien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent praticien = new Intent(VisitorCalendarActivity.this, VisitorPractitionersActivity.class);
                praticien.putExtra("currentUser", currentUser);
                startActivity(praticien);
            }
        });
        btn_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais = new Intent(VisitorCalendarActivity.this, VisitorExpenseFormActivity.class);
                frais.putExtra("currentUser", currentUser);
                startActivity(frais);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(VisitorCalendarActivity.this, VisitorProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });

        btn_add_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_rdv = new Intent(VisitorCalendarActivity.this, VisitorCalendarAddRdvActivity.class);
                add_rdv.putExtra("currentUser", currentUser);
                startActivity(add_rdv);
            }
        });

        btn_consult_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consult_agenda = new Intent(VisitorCalendarActivity.this, VisitorCalendarDateDetailActivity.class);
                consult_agenda.putExtra("currentUser", currentUser);
                startActivity(consult_agenda);
            }
        });
    }
}