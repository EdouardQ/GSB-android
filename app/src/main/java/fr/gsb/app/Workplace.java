package fr.gsb.app;

import java.io.Serializable;

public class Workplace implements Serializable {
    private String wording;

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }
}
