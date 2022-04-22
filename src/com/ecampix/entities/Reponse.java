package com.ecampix.entities;


import com.ecampix.utils.RelationObject;

public class Reponse {

    private int id;
    private RelationObject admin;
    private RelationObject reclamationId;
    private String text;

    public Reponse(int id, RelationObject admin, RelationObject reclamationId, String text) {
        this.id = id;
        this.admin = admin;
        this.reclamationId = reclamationId;
        this.text = text;
    }

    public Reponse(RelationObject admin, RelationObject reclamationId, String text) {
        this.admin = admin;
        this.reclamationId = reclamationId;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RelationObject getAdmin() {
        return admin;
    }

    public void setAdmin(RelationObject admin) {
        this.admin = admin;
    }

    public RelationObject getReclamationId() {
        return reclamationId;
    }

    public void setReclamationId(RelationObject reclamationId) {
        this.reclamationId = reclamationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}