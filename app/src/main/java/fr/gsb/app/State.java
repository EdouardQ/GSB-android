package fr.gsb.app;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class State implements Serializable {
    private int id;
    private String wording;

    public State() {
    }

    public State(DocumentSnapshot document) {
        this.id = Integer.parseInt(document.getId());
        this.wording = document.getString("wording");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }
}
