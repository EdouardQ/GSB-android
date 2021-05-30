package fr.gsb.app;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;

public class BundleMonthly implements Serializable {
    private String id;
    private Date date;
    private Integer km;
    private String otherCost;
    private double paid;
    private User user;
    private State state;

    public BundleMonthly() {
    }

    public BundleMonthly(DocumentSnapshot document) {
        this.id = document.getId();
        this.date = document.getDate("date");
        this.km = Integer.parseInt(document.getString("km")); // String -> int
        this.otherCost = document.getString("otherCost");
        this.paid = document.getDouble("paid");
        ((DocumentReference) document.get("user")).addSnapshotListener((workplaceSnapshot, e) ->
                this.user = workplaceSnapshot.toObject(User.class));
        ((DocumentReference) document.get("state")).addSnapshotListener((workplaceSnapshot, e1) ->
                this.state = workplaceSnapshot.toObject(State.class));
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

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
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
