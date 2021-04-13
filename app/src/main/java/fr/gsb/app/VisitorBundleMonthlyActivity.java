package fr.gsb.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class VisitorBundleMonthlyActivity extends AppCompatActivity {

    private Button btn_dcnx;
    private Button btn_praticien;
    private Button btn_rdv;
    private Button btn_frais;
    private Button btn_profil;
    private TextView tv_ident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_bundle_monthly);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_praticien = findViewById(R.id.praticien);
        btn_rdv = findViewById(R.id.rdv);
        btn_frais = findViewById(R.id.frais);
        btn_profil = findViewById(R.id.profil);
        tv_ident = findViewById(R.id.tv_ident);

         Intent i_recu = getIntent();
         String ident_recu = i_recu.getStringExtra("ident");
         tv_ident.setText(ident_recu);

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(VisitorBundleMonthlyActivity.this, MainActivity.class);
                startActivity(connexion);
            }
        });
        btn_praticien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent praticien = new Intent(VisitorBundleMonthlyActivity.this, VisitorPractitionersActivity.class);
                praticien.putExtra("ident", valeur);
                startActivity(praticien);
            }
        });

        btn_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent rdv = new Intent(VisitorBundleMonthlyActivity.this, VisitorCalendarActivity.class);
                rdv.putExtra("ident", valeur);
                startActivity(rdv);
            }
        });
        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent profil = new Intent(VisitorBundleMonthlyActivity.this, VisitorProfilActivity.class);
                profil.putExtra("ident", valeur);
                startActivity(profil);
            }
        });
    }
}