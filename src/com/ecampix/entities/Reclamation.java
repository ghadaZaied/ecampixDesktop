package com.ecampix.entities;


import com.ecampix.utils.RelationObject;

import java.time.LocalDate;

public class Reclamation {

    private int id;
    private RelationObject clientId;
    private String titre;
    private String text;
    private LocalDate date;

    public Reclamation(int id, RelationObject clientId, String titre, String text, LocalDate date) {
        this.id = id;
        this.clientId = clientId;
        this.titre = titre;
        this.text = text;
        this.date = date;
    }

    public Reclamation(RelationObject clientId, String titre, String text, LocalDate date) {
        this.clientId = clientId;
        this.titre = titre;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RelationObject getClientId() {
        return clientId;
    }

    public void setClientId(RelationObject clientId) {
        this.clientId = clientId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}