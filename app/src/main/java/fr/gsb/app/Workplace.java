package fr.gsb.app;

import com.google.firebase.firestore.DocumentSnapshot;

public class Workplace {
    private String wording;

    public Workplace(DocumentSnapshot document) {
        this.wording = document.getString("wording");
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }
}
