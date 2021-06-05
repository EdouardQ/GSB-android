package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class VisitorCalendarActivity extends AppCompatActivity {

    private Button btn_consult_rdv, btn_add_rdv, btn_dcnx, btn_praticien, btn_frais, btn_profil;
    private TextView tv_ident;
    private EditText et_date;
    private DatePicker dp_agenda;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_calendar);

        btn_dcnx = findViewById(R.id.deconnexion);
        btn_praticien = findViewById(R.id.praticien);
        btn_frais = findViewById(R.id.frais);
        btn_profil = findViewById(R.id.profil);
        btn_add_rdv = findViewById(R.id.btn_add_rdv);
        btn_consult_rdv = findViewById(R.id.btn_consult_rdv);
        tv_ident = findViewById(R.id.tv_ident);
        et_date = findViewById(R.id.et_date);
        dp_agenda = findViewById(R.id.dp_agenda);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month  = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        this.dp_agenda.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePickerChange(  dp_agenda,   year,   monthOfYear,   dayOfMonth);
            }
        });


        Intent i_recu = getIntent();
        User currentUser = (User) i_recu.getSerializableExtra("currentUser");
        tv_ident.setText(currentUser.getName() + " " + currentUser.getFirstName());

        this.btn_dcnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent connexion = new Intent(VisitorCalendarActivity.this, MainActivity.class);
                connexion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(connexion);
            }
        });
        this.btn_praticien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent praticien = new Intent(VisitorCalendarActivity.this, VisitorPractitionersActivity.class);
                praticien.putExtra("currentUser", currentUser);
                startActivity(praticien);
            }
        });
        this.btn_frais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent frais = new Intent(VisitorCalendarActivity.this, VisitorExpenseFormActivity.class);
                frais.putExtra("currentUser", currentUser);
                startActivity(frais);
            }
        });

        this.btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profil = new Intent(VisitorCalendarActivity.this, VisitorProfilActivity.class);
                profil.putExtra("currentUser", currentUser);
                startActivity(profil);
            }
        });

        this.btn_add_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_rdv = new Intent(VisitorCalendarActivity.this, VisitorCalendarAddRdvActivity.class);
                add_rdv.putExtra("currentUser", currentUser);
                startActivity(add_rdv);
            }
        });

        this.btn_consult_rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consult_agenda = new Intent(VisitorCalendarActivity.this, VisitorCalendarDateDetailActivity.class);
                consult_agenda.putExtra("currentUser", currentUser);
                consult_agenda.putExtra("date_select", et_date.getText().toString());
                startActivity(consult_agenda);
            }
        });
    }

    private void datePickerChange(DatePicker dp_agenda, int year, int month, int dayOfMonth) {
        //Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
        String monthStr = String.valueOf(month+1);
        if (month<=8) { // month + 1 = le mois reel
            monthStr = "0"+monthStr;
        }
        this.et_date.setText(dayOfMonth +"-" + monthStr + "-" + year);
    }
}