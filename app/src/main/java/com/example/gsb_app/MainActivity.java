package com.example.gsb_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edt_loging;
    private EditText edt_password;
    private Button btn_connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_loging = (EditText) findViewById(R.id.edt_loging);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_connexion = (Button) findViewById(R.id.btn_connexion);

        edt_password.setTransformationMethod(new PasswordTransformationMethod());
    }
}