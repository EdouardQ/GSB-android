package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AccountantProfilActivity extends AppCompatActivity {

    private Button btn_dcnx;
    private Button btn_val_frais;
    private Button btn_frais_mois;
    private TextView tv_ident;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountant_profil);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_val_frais = findViewById(R.id.validation_frais);
        btn_frais_mois = findViewById(R.id.frais_mois);
        tv_ident = findViewById(R.id.tv_ident);

        Intent i_recu = getIntent();
        String ident_recu = i_recu.getStringExtra("ident");
        tv_ident.setText(ident_recu);

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(AccountantProfilActivity.this, MainActivity.class);
                startActivity(connexion);
            }
        });

        btn_val_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent val_frais = new Intent(AccountantProfilActivity.this, AccountantListExpenseFormLeftActivity.class);
                val_frais.putExtra("ident", valeur);
                startActivity(val_frais);
            }
        });

        btn_frais_mois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valeur = tv_ident.getText().toString();
                Intent frais_mois = new Intent(AccountantProfilActivity.this, AccountantBundleMontlyActivity.class);
                frais_mois.putExtra("ident", valeur);
                startActivity(frais_mois);
            }
        });


    }
}