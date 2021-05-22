package fr.gsb.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.conscrypt.Conscrypt;

import java.security.Security;

public class MyAppCompatActivity extends AppCompatActivity {
    private String name;
    private String firstName;
    private String role;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Security.insertProviderAt(Conscrypt.newProvider(), 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
