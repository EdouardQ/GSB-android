package fr.gsb.app;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class Practitioner implements Serializable {
    private String name;
    private String firstName;
    private String address;
    private String city;
    private String postalCode;
    private String workplace;
    private double coeffReputation;

    public Practitioner() {
    }

    public Practitioner(DocumentSnapshot document) {
        this.name = document.getString("name");
        this.firstName = document.getString("firstName");
        this.address = document.getString("address");;
        this.city = document.getString("city");;
        this.postalCode = document.getString("postalCode");
        this.workplace = document.getString("workplace");
        this.coeffReputation = document.getDouble("coeffReputation");
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public double getCoeffReputation() {
        return coeffReputation;
    }

    public void setCoeffReputation(double coeffReputation) {
        this.coeffReputation = coeffReputation;
    }
}
