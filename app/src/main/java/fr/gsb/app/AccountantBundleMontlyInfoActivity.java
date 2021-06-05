package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class AccountantBundleMontlyInfoActivity extends AppCompatActivity {
    private Button btn_dcnx;
    private Button btn_val_frais;
    private Button btn_profil;
    private Button btn_frais_mois;
    private TextView tv_ident;

    private String dateSelected;
    private TextView tv_date;
    private TextView tv_expense_form_count;
    private TextView tv_km;
    private TextView tv_paid;

    private Double totalKm;
    private Double totalPaid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountant_bundle_monthly_info);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_val_frais = findViewById(R.id.validation_frais);
        btn_profil = findViewById(R.id.profil_compt);
        btn_frais_mois = findViewById(R.id.frais_mois);
        tv_ident = findViewById(R.id.tv_ident);

        Intent i_recu = getIntent();
        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        dateSelected = i_recu.getStringExtra("dateSelected");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        tv_date = findViewById(R.id.tv_date);
        tv_expense_form_count = findViewById(R.id.tv_expense_form_count);
        tv_km = findViewById(R.id.tv_km);
        tv_paid = findViewById(R.id.tv_paid);
        tv_date.setText(dateSelected);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<ExpenseForm> expenseFormList = new ArrayList<ExpenseForm>();

        db.collection("expense_forms")
                .get() // récupère tout les expense forms de la collection
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task){
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FIREC", document.getId() + " => " + document.getData());
                                ExpenseForm currentExpenseForm = new ExpenseForm(document);
                                // si la date et le status correspondent
                                if ((String.format("%1$tm-%1$tY", currentExpenseForm.getDate())).equals(dateSelected) &&
                                        currentExpenseForm.getState().equals("Remboursé")) {
                                    expenseFormList.add(currentExpenseForm);
                                }
                            }
                            Integer listLenght = 0;
                            listLenght = expenseFormList.size();
                            totalKm = 0.0;
                            totalPaid = 0.0;
                            tv_expense_form_count.setText(String.valueOf(listLenght));
                            for(int i=0; i<listLenght; i++){
                                totalKm = totalKm + expenseFormList.get(i).getKm();
                                totalPaid = totalPaid + expenseFormList.get(i).getPaid();
                            }
                            tv_km.setText(String.valueOf(totalKm));
                            tv_paid.setText(String.valueOf(totalPaid));
                        } else {
                            Log.d("FIREC", "Error getting documents: ", task.getException());
                        }
                    }
                });

        btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(AccountantBundleMontlyInfoActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });

        btn_val_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent val_frais = new Intent(AccountantBundleMontlyInfoActivity.this, AccountantListExpenseFormLeftActivity.class);
                val_frais.putExtra("currentUser", currentUser);
                startActivity(val_frais);
            }
        });

        btn_frais_mois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais_mois = new Intent(AccountantBundleMontlyInfoActivity.this, AccountantBundleMontlyActivity.class);
                frais_mois.putExtra("currentUser", currentUser);
                startActivity(frais_mois);
            }
        });

        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(AccountantBundleMontlyInfoActivity.this, AccountantProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });
    }
}
