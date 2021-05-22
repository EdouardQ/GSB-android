package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AccountantBundleMontlyActivity extends AppCompatActivity {

    private Button btn_dcnx;
    private Button btn_val_frais;
    private Button btn_profil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountant_bundle_montly);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_val_frais = findViewById(R.id.validation_frais);
        btn_profil = findViewById(R.id.profil_compt);

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(AccountantBundleMontlyActivity.this, MainActivity.class);
                startActivity(connexion);
            }
        });

        btn_val_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent val_frais = new Intent(AccountantBundleMontlyActivity.this, AccountantListExpenseFormLeftActivity.class);
                startActivity(val_frais);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(AccountantBundleMontlyActivity.this, AccountantProfilActivity.class);
                startActivity(profil);
            }
        });

    }
}