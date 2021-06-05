package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AccountantBundleMontlyActivity extends AppCompatActivity {

    private Button btn_dcnx;
    private Button btn_val_frais;
    private Button btn_profil;
    private TextView tv_ident;
    private Spinner sp_month;
    private ArrayAdapter<String> adapter;
    private Button btn_validate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountant_bundle_montly);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_val_frais = findViewById(R.id.validation_frais);
        btn_profil = findViewById(R.id.profil_compt);
        tv_ident = findViewById(R.id.tv_ident);
        sp_month = findViewById(R.id.sp_month);
        btn_validate = findViewById(R.id.btn_validate);

        Intent i_recu = getIntent();
        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<String> dateList = new ArrayList<String>();

        db.collection("expense_forms")
                .get() // récupère tout les expense forms de la collection
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("FIREC", document.getId() + " => " + document.getData());
                                String date = String.format("%1$tm-%1$tY", new ExpenseForm(document).getDate());
                                if (!dateList.contains(date)) {
                                    dateList.add(date);
                                }
                            }
                            // ajoute la liste des workplace dans le spinner
                            adapter = new ArrayAdapter<String>(AccountantBundleMontlyActivity.this, android.R.layout.simple_spinner_dropdown_item, dateList); // simple_spinner_dropdown_item existe de base
                            sp_month.setAdapter(adapter); // affiche le spinner
                        } else {
                            Log.d("FIREC", "Error getting documents: ", task.getException());
                        }
                    }
                });

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateSelected = dateList.get(sp_month.getSelectedItemPosition());
                Log.d("test", sp_month.getSelectedItemPosition()+"");
                Intent bundleMontlyInfo = new Intent(AccountantBundleMontlyActivity.this, AccountantBundleMontlyInfoActivity.class);
                bundleMontlyInfo.putExtra("currentUser", currentUser);
                bundleMontlyInfo.putExtra("dateSelected", dateSelected);
                startActivity(bundleMontlyInfo);
            }
        });


        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(AccountantBundleMontlyActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });

        btn_val_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent val_frais = new Intent(AccountantBundleMontlyActivity.this, AccountantListExpenseFormLeftActivity.class);
                val_frais.putExtra("currentUser", currentUser);
                startActivity(val_frais);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(AccountantBundleMontlyActivity.this, AccountantProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });



    }
}