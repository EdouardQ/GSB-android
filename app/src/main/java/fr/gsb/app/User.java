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
    private String city;
    private String postalCode;

    public User() {
    }

    public User(DocumentSnapshot document) {
        this.id = document.getId();
        this.name = document.getString("name");
        this.firstName = document.getString("firstName");
        this.role = document.getString("role");
        this.phone = document.getString("phone");
        this.city = document.getString("city");
        this.postalCode = document.getString("postalCode");
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
