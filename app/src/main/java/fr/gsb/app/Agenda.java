package fr.gsb.app;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class Agenda {
    private String id;
    private Date rdv;
    private User user;
    private Practitioner practitioner;

    public Agenda() {
    }

    public Agenda(DocumentSnapshot document) {
        this.id = document.getId();
        this.rdv = document.getDate("rdv");
        this.user = new User(document);
        this.practitioner = new Practitioner(document);
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Date getRdv() { return rdv; }

    public void setRdv(Date rdv) { this.rdv = rdv; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Practitioner getPractitioner() { return practitioner; }

    public void setPractitioner(Practitioner practitioner) { this.practitioner = practitioner; }
}
