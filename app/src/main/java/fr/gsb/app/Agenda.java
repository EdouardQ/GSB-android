package fr.gsb.app;

import com.google.firebase.firestore.DocumentReference;
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
        ((DocumentReference) document.get("user")).addSnapshotListener((userSnapshot, e) -> this.user = userSnapshot.toObject(User.class));
        ((DocumentReference) document.get("practitioner")).addSnapshotListener((practitionerSnapshot, e) -> this.practitioner = practitionerSnapshot.toObject(Practitioner.class));
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Date getRdv() { return rdv; }

    public void setRdv(Date rdv) { this.rdv = rdv; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Practitioner getPractitioner() { return practitioner; }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }
}
