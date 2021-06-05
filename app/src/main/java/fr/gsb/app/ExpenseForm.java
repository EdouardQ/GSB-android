package fr.gsb.app;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;

public class ExpenseForm implements Serializable {
    private String id;
    private Date date;
    private double km;
    private String otherCost;
    private double paid;
    private String userName;
    private String userFirstName;
    private String UserId;
    private String state;

    public ExpenseForm() {
    }

    public ExpenseForm(DocumentSnapshot document) {
        this.id = document.getId();
        this.date = document.getDate("date");
        this.km = document.getDouble("km");
        this.otherCost = document.getString("otherCost");
        this.paid = document.getDouble("paid");
        this.userName = document.getString("userName");
        this.userFirstName = document.getString("userFirstName");
        this.UserId = document.getString("UserId");
        this.state = document.getString("state");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public String getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(String otherCost) {
        this.otherCost = otherCost;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return UserId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
