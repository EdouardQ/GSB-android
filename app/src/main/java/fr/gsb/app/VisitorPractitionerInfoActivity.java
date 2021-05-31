package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class VisitorPractitionerInfoActivity extends AppCompatActivity {
    private Button btn_dcnx;
    private Button btn_praticien;
    private Button btn_rdv;
    private Button btn_frais;
    private Button btn_profil;
    private Practitioner currentPractitioner;
    private TextView tv_ident;
    private TextView tv_name;
    private TextView tv_firstName;
    private TextView tv_coeffReputation;
    private TextView tv_workplace;
    private TextView tv_address;
    private TextView tv_city;
    private TextView tv_postalCode;
    private Button btn_set_practitioner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_practitioner_info);

        Intent i_recu = getIntent();
        currentPractitioner = (Practitioner) i_recu.getSerializableExtra("practitionerInfo");

        btn_set_practitioner = findViewById(R.id.btn_set_practitioner);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_praticien = findViewById(R.id.praticien);
        btn_rdv = findViewById(R.id.rdv);
        btn_frais = findViewById(R.id.frais);
        btn_profil = findViewById(R.id.profil);
        tv_ident = findViewById(R.id.tv_ident);

        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        // affichage du praticien -> find
        tv_name = findViewById(R.id.tv_name);
        tv_firstName = findViewById(R.id.tv_firstName);
        tv_coeffReputation = findViewById(R.id.tv_coeffReputation);
        tv_workplace = findViewById(R.id.tv_workplace);
        tv_address = findViewById(R.id.tv_address);
        tv_city = findViewById(R.id.tv_city);
        tv_postalCode = findViewById(R.id.tv_postalCode);

        // affichage du praticien -> set
        tv_name.setText(currentPractitioner.getName());
        tv_firstName.setText(currentPractitioner.getFirstName());
        tv_coeffReputation.setText(String.valueOf(currentPractitioner.getCoeffReputation())); // double -> String
        tv_workplace.setText(currentPractitioner.getWorkplace().getWording());
        tv_address.setText(currentPractitioner.getAddress());
        tv_city.setText(currentPractitioner.getCity());
        tv_postalCode.setText(currentPractitioner.getPostalCode());

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(VisitorPractitionerInfoActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });
        btn_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rdv = new Intent(VisitorPractitionerInfoActivity.this, VisitorCalendarActivity.class);
                rdv.putExtra("currentUser", currentUser);
                startActivity(rdv);
            }
        });

        btn_praticien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent praticien = new Intent(VisitorPractitionerInfoActivity.this, VisitorPractitionersActivity.class);
                praticien.putExtra("currentUser", currentUser);
                startActivity(praticien);
            }
        });

        btn_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais = new Intent(VisitorPractitionerInfoActivity.this, VisitorExpenseFormActivity.class);
                frais.putExtra("currentUser", currentUser);
                startActivity(frais);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(VisitorPractitionerInfoActivity.this, VisitorProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });

        btn_set_practitioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setPractitioner = new Intent(VisitorPractitionerInfoActivity.this, VisitorSetPractitionerActivity.class);
                setPractitioner.putExtra("currentUser", currentUser);
                setPractitioner.putExtra("practitionerInfo", currentPractitioner);
                startActivity(setPractitioner);
            }
        });
    }
}
