package fr.gsb.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VisitorCalendarAddRdvActivity extends AppCompatActivity {
    private EditText etDate, etTime;
    private Button btnAddDate, btnAddTime;

    private int lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth;
    private int lastSelectedHour = -1, lastSelectedMinute = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_calendar_add_rdv);

        // Partie sur la sélection de la date (Jour/Mois/Année)
        this.etDate = this.findViewById(R.id.et_date);
        this.btnAddDate = this.findViewById(R.id.btn_add_date);

        this.btnAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate();
            }
        });

        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        // Partie sur la sélection de l'heure
        this.etTime = this.findViewById(R.id.et_time);
        this.btnAddTime = this.findViewById(R.id.btn_add_time);

        this.btnAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectTime();
            }
        });
    }

    private void buttonSelectDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                etDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

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


