package fr.gsb.app;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;

public class ExpenseForm implements Serializable {
    private String id;
    private Date date;
    private double km;
    private String otherCost;
    private double paid;
    private User user;
    private State state;

    public ExpenseForm() {
    }

    public ExpenseForm(DocumentSnapshot document) {
        this.id = document.getId();
        this.date = document.getDate("date");
        this.km = document.getDouble("paid");
        this.otherCost = document.getString("otherCost");
        this.paid = document.getDouble("paid");
        ((DocumentReference) document.get("user")).addSnapshotListener((userSnapshot, e) ->
                this.user = userSnapshot.toObject(User.class));
        ((DocumentReference) document.get("state")).addSnapshotListener((stateSnapshot, e1) ->
                this.state = stateSnapshot.toObject(State.class));
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
