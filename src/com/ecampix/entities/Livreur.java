package com.ecampix.entities;

import com.ecampix.utils.Constants;

public class Livreur implements Comparable<Livreur> {

    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private float tarif;
    private String email;

    public Livreur(int id, String nom, String prenom, String adresse, float tarif, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.tarif = tarif;
        this.email = email;
    }

    public Livreur(String nom, String prenom, String adresse, float tarif, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.tarif = tarif;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public int compareTo(Livreur livreur) {
        switch (Constants.compareVar) {
            case "Nom":
                return livreur.getNom().compareTo(this.getNom());
            case "Prenom":
                return livreur.getPrenom().compareTo(this.getPrenom());
            case "Adresse":
                return livreur.getAdresse().compareTo(this.getAdresse());
            case "Tarif":
                return Float.compare(livreur.getTarif(), this.getTarif());
            case "Email":
                return livreur.getEmail().compareTo(this.getEmail());

            default:
                return 0;
        }
    }

}