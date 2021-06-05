package fr.gsb.app;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.Date;

public class Agenda implements Serializable {
    private String id;
    private Date rdv;
    private String userId;
    private String userName;
    private String practitioner;

    public Agenda() {
    }

    public Agenda(DocumentSnapshot document) {
        this.id = document.getId();
        this.rdv = document.getDate("rdv");
        this.userId = document.getString("userId");
        this.userName = document.getString("userName");
        this.practitioner = document.getString("practitioner");
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Date getRdv() { return rdv; }

    public void setRdv(Date rdv) { this.rdv = rdv; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }
}
