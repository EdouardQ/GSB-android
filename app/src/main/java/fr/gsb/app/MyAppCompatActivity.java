package fr.gsb.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import org.conscrypt.Conscrypt;

import java.security.Security;

public class MyAppCompatActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Security.insertProviderAt(Conscrypt.newProvider(), 1);

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
