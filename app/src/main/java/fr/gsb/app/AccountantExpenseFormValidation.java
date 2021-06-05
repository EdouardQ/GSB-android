package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountantExpenseFormValidation extends AppCompatActivity {
    private Button btn_dcnx;
    private Button btn_val_frais;
    private Button btn_frais_mois;
    private Button btn_profil;
    private TextView tv_ident;

    private TextView tv_visitor;
    private TextView tv_date;
    private TextView tv_km;
    private TextView tv_otherCost;
    private TextView tv_paid;
    private Button btn_refund;
    private Button btn_refuse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountant_expensive_form_validation);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_val_frais = findViewById(R.id.validation_frais);
        btn_frais_mois = findViewById(R.id.frais_mois);
        btn_profil = findViewById(R.id.profil_compt);
        tv_ident = findViewById(R.id.tv_ident);

        Intent i_recu = getIntent();
        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        ExpenseForm expenseFormInfo = (ExpenseForm) i_recu.getSerializableExtra("expenseFormInfo");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        tv_visitor = findViewById(R.id.tv_visitor);
        tv_date = findViewById(R.id.tv_date);
        tv_km = findViewById(R.id.tv_km);
        tv_otherCost = findViewById(R.id.tv_otherCost);
        tv_paid = findViewById(R.id.tv_paid);
        btn_refund = findViewById(R.id.btn_refund);
        btn_refuse = findViewById(R.id.btn_refuse);

        tv_visitor.setText(expenseFormInfo.getUserName()+" "+expenseFormInfo.getUserFirstName());
        tv_date.setText(String.format("%1$td-%1$tm-%1$tY", expenseFormInfo.getDate()));
        tv_km.setText(expenseFormInfo.getKm()+""); // +"" pour Integer -> String
        tv_otherCost.setText(expenseFormInfo.getOtherCost()+""); // +"" pour si null
        tv_paid.setText(expenseFormInfo.getPaid()+""); // +"" pour Integer -> String

        btn_refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference expenseFormRef = db.collection("expense_forms").document(expenseFormInfo.getId());

                expenseFormRef.update("state", "Remboursé")
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("FIREC", "DocumentSnapshot successfully updated!");
                            Intent val_frais = new Intent(AccountantExpenseFormValidation.this, AccountantListExpenseFormLeftActivity.class);
                            val_frais.putExtra("currentUser", currentUser);
                            startActivity(val_frais);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FIREC", "Error updating document", e);
                        }
                    });
            }
        });

        btn_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference expenseFormRef = db.collection("expense_forms").document(expenseFormInfo.getId());

                expenseFormRef.update("state", "Refusé")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("FIREC", "DocumentSnapshot successfully updated!");
                                Intent val_frais = new Intent(AccountantExpenseFormValidation.this, AccountantListExpenseFormLeftActivity.class);
                                val_frais.putExtra("currentUser", currentUser);
                                startActivity(val_frais);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("FIREC", "Error updating document", e);
                            }
                        });
            }
        });


        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(AccountantExpenseFormValidation.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });

        btn_val_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent val_frais = new Intent(AccountantExpenseFormValidation.this, AccountantListExpenseFormLeftActivity.class);
                val_frais.putExtra("currentUser", currentUser);
                startActivity(val_frais);
            }
        });

        btn_frais_mois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais_mois = new Intent(AccountantExpenseFormValidation.this, AccountantBundleMontlyActivity.class);
                frais_mois.putExtra("currentUser", currentUser);
                startActivity(frais_mois);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(AccountantExpenseFormValidation.this, AccountantProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });

    }
}
