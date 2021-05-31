package fr.gsb.app;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class Workplace implements Serializable {
    private Integer id;
    private String wording;

    public Workplace() {
    }

    public Workplace(DocumentSnapshot document) {
        this.id = Integer.parseInt(document.getId());
        this.wording = document.getString("wording");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }
}
