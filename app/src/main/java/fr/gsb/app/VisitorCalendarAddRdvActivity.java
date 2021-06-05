package fr.gsb.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VisitorCalendarAddRdvActivity extends AppCompatActivity {
    private EditText etDate, etTime;
    private TextView tv_ident;
    private Button btnAddDate, btnAddTime, btnAddRdv, btn_dcnx, btn_praticien, btn_rdv, btn_frais, btn_profil;
    private Spinner spPractitioner;
    private ArrayAdapter<String> adapter;



    private int lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth;
    private int lastSelectedHour = -1, lastSelectedMinute = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_calendar_add_rdv);

        Intent i_recu = getIntent();

        // Partie sur la navigation de l'appli
        this.tv_ident = findViewById(R.id.tv_ident);
        this.btn_dcnx = findViewById(R.id.deconnexion);
        this.btn_praticien = findViewById(R.id.praticien);
        this.btn_rdv = findViewById(R.id.rdv);
        this.btn_frais = findViewById(R.id.frais);
        this.btn_profil = findViewById(R.id.profil);

        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());


        this.btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(VisitorCalendarAddRdvActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });
        this.btn_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rdv = new Intent(VisitorCalendarAddRdvActivity.this, VisitorCalendarActivity.class);
                rdv.putExtra("currentUser", currentUser);
                startActivity(rdv);
            }
        });

        this.btn_praticien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent praticien = new Intent(VisitorCalendarAddRdvActivity.this, VisitorPractitionersActivity.class);
                praticien.putExtra("currentUser", currentUser);
                startActivity(praticien);
            }
        });

        this.btn_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais = new Intent(VisitorCalendarAddRdvActivity.this, VisitorExpenseFormActivity.class);
                frais.putExtra("currentUser", currentUser);
                startActivity(frais);
            }
        });

        this.btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(VisitorCalendarAddRdvActivity.this, VisitorProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });

        // Partie sur la sélection de la date (Jour/Mois/Année)
        this.etDate = this.findViewById(R.id.et_date);
        this.btnAddDate = this.findViewById(R.id.btn_add_date);

        this.btnAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate();
            }
        });

        // Partie sur la sélection de l'heure
        this.etTime = this.findViewById(R.id.et_time);
        this.btnAddTime = this.findViewById(R.id.btn_add_time);

        this.btnAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectTime();
            }
        });

        // Initialisation du spinner et de la requête
        this.spPractitioner = this.findViewById(R.id.sp_practitioner);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<String> practitioners = new ArrayList<String>();

        db.collection("practitioners").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        practitioners.add(new Practitioner(document).getName()+" "+new Practitioner(document).getFirstName());
                    }
                    adapter = new ArrayAdapter<String>(VisitorCalendarAddRdvActivity.this, android.R.layout.simple_spinner_dropdown_item, practitioners);
                    spPractitioner.setAdapter(adapter);
                } else {
                    Log.d("FIREC", "Error getting documents: ", task.getException());
                }
            }
        });

        // Partie sur la soumission du formulaire
        this.btnAddRdv = this.findViewById(R.id.btn_add_rdv);
        btnAddRdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDate.getText().length() == 0 || etTime.getText().length() == 0) {
                    Log.w("EditText", "failure: champs vide(s)");
                    Toast.makeText(VisitorCalendarAddRdvActivity.this, "Au moins un des champs est vide",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String toConvert = etDate.getText().toString() + " " + etTime.getText().toString();
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    Date date = null;
                    try {
                        date = df.parse(toConvert);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Timestamp ts = new Timestamp(date);

                    Map<String, Object> agendaMap = new HashMap<>();
                    agendaMap.put("rdv", ts);
                    agendaMap.put("userId", currentUser.getId());
                    agendaMap.put("userName", currentUser.getName()+" "+currentUser.getFirstName());
                    agendaMap.put("practitioner", practitioners.get(spPractitioner.getSelectedItemPosition()));

                    db.collection("agenda").document(GenerateRandomString.randomString(20)).set(agendaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("FIREC", "DocumentSnapshot successfully written!");

                            // redirection vers la liste des praticiens -> VisitorSetPractitionerActivity
                            Intent calendar = new Intent(VisitorCalendarAddRdvActivity.this, VisitorCalendarActivity.class);
                            calendar.putExtra("currentUser", currentUser);
                            startActivity(calendar);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FIREC", "Error writing document", e);
                        }
                    });
                }
            }
        });
    }

    private void buttonSelectDate() {
        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;
        datePickerDialog = new DatePickerDialog(this, dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        datePickerDialog.show();
    }

    private void buttonSelectTime() {
        if(this.lastSelectedHour == -1) {
            final Calendar c = Calendar.getInstance();
            this.lastSelectedHour = c.get(Calendar.HOUR_OF_DAY);
            this.lastSelectedMinute = c.get(Calendar.MINUTE);
        }

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                etTime.setText(hourOfDay + ":" + minute);
                lastSelectedHour = hourOfDay;
                lastSelectedMinute = minute;
            }
        };

        TimePickerDialog timePickerDialog = null;
        timePickerDialog = new TimePickerDialog(this, timeSetListener, lastSelectedHour, lastSelectedMinute, true);
        timePickerDialog.show();
    }
}


