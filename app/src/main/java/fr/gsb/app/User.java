package fr.gsb.app;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String password;
    private String name;
    private String firstName;
    private String role;
    private String phone;

    public User() {
    }

    public User(DocumentSnapshot document) {
        this.id = document.getId();
        this.name = document.getString("name");
        this.firstName = document.getString("firstName");
        this.role = document.getString("role");
        this.phone = document.getString("phone");
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
