package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.List;

public class AccountantListExpenseFormLeftActivity extends AppCompatActivity {

    private Button btn_dcnx;
    private Button btn_frais_mois;
    private Button btn_profil;
    private TextView tv_ident;
    private ListView lv_expenseform;
    private List<ExpenseForm> expenseformList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountant_list_expense_form_left);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_frais_mois = findViewById(R.id.frais_mois);
        btn_profil = findViewById(R.id.profil_compt);
        tv_ident = findViewById(R.id.tv_ident);
        lv_expenseform = findViewById(R.id.lv_expenseform);


// listage des fiches de frais non validées

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        expenseformList = new ArrayList<>();


        db.collection("expense_forms")
                .get() // récupère tout les fiches de frais de la collection
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("FIREC", document.getId() + " => " + document.getData());
                                ExpenseForm tmp_expenseform = new ExpenseForm(document);
                                if (tmp_expenseform.getState().getWording().equals("Saisie clôturée")) {
                                    expenseformList.add(tmp_expenseform);
                                }


                            }
                            // ajoute la liste des fiches frais dans la listview
                            ExpenseFormAdapter expenseAdapt = new ExpenseFormAdapter(AccountantListExpenseFormLeftActivity.this, expenseformList);
                            lv_expenseform.setAdapter(expenseAdapt); // affiche la listview
                        } else {
                            Log.d("FIREC", "Error getting documents: ", task.getException());
                        }
                    }
                });




        //récupère les infos pour le nom prénom
        Intent i_recu = getIntent();
        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());
        

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(AccountantListExpenseFormLeftActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });

        btn_frais_mois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais_mois = new Intent(AccountantListExpenseFormLeftActivity.this, AccountantBundleMontlyActivity.class);
                frais_mois.putExtra("currentUser", currentUser);
                startActivity(frais_mois);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(AccountantListExpenseFormLeftActivity.this, AccountantProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });

    }
}