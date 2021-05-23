package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AccountantListExpenseFormLeftActivity extends AppCompatActivity {

    private Button btn_dcnx;
    private Button btn_frais_mois;
    private Button btn_profil;
    private TextView tv_ident;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountant_list_expense_form_left);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_frais_mois = findViewById(R.id.frais_mois);
        btn_profil = findViewById(R.id.profil_compt);
        tv_ident = findViewById(R.id.tv_ident);

        Intent i_recu = getIntent();
        String ident_recu = i_recu.getStringExtra("ident");
        tv_ident.setText(ident_recu);

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(AccountantListExpenseFormLeftActivity.this, MainActivity.class);
                startActivity(connexion);
            }
        });

        btn_frais_mois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent frais_mois = new Intent(AccountantListExpenseFormLeftActivity.this, AccountantBundleMontlyActivity.class);
                frais_mois.putExtra("ident", valeur);
                startActivity(frais_mois);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent profil = new Intent(AccountantListExpenseFormLeftActivity.this, AccountantProfilActivity.class);
                profil.putExtra("ident", valeur);
                startActivity(profil);
            }
        });

    }
}