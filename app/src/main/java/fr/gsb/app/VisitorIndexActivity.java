package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class VisitorIndexActivity extends AppCompatActivity {

    private Button btn_dcnx;
    private Button btn_praticien;
    private Button btn_rdv;
    private Button btn_frais;
    private Button btn_profil;
    private TextView tv_ident;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_index);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_praticien = findViewById(R.id.praticien);
        btn_rdv = findViewById(R.id.rdv);
        btn_frais = findViewById(R.id.frais);
        btn_profil = findViewById(R.id.profil);
        tv_ident = findViewById(R.id.tv_ident);

        Intent i_recu = getIntent();
        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(VisitorIndexActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });

        btn_praticien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent praticien = new Intent(VisitorIndexActivity.this, VisitorPractitionersActivity.class);
                praticien.putExtra("currentUser", currentUser);
                startActivity(praticien);
            }
        });

        btn_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rdv = new Intent(VisitorIndexActivity.this, VisitorCalendarActivity.class);
                rdv.putExtra("currentUser", currentUser);
                startActivity(rdv);
            }
        });

        btn_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais = new Intent(VisitorIndexActivity.this, VisitorExpenseFormActivity.class);
                frais.putExtra("currentUser", currentUser);
                startActivity(frais);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(VisitorIndexActivity.this, VisitorProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });


    }
}