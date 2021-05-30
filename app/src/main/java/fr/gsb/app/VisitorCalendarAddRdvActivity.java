package fr.gsb.app;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VisitorCalendarAddRdvActivity extends AppCompatActivity {
    private EditText etDate;
    private Button btnAddRdv;

    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_calendar_add_rdv);

        this.etDate = this.findViewById(R.id.et_date);
        this.btnAddRdv = this.findViewById(R.id.btn_add_rdv);

        this.btnAddRdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate();
            }
        });

        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
    }

    private void buttonSelectDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

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


}


