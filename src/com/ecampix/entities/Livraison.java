package com.ecampix.entities;


import com.ecampix.utils.RelationObject;

import java.time.LocalDate;

public class Livraison {

    private int id;
    private RelationObject livreurId;
    private String numSerie;
    private String adresse;
    private LocalDate date;

    public Livraison(int id, RelationObject livreurId, String numSerie, String adresse, LocalDate date) {
        this.id = id;
        this.livreurId = livreurId;
        this.numSerie = numSerie;
        this.adresse = adresse;
        this.date = date;
    }

    public Livraison(RelationObject livreurId, String numSerie, String adresse, LocalDate date) {
        this.livreurId = livreurId;
        this.numSerie = numSerie;
        this.adresse = adresse;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RelationObject getLivreurId() {
        return livreurId;
    }

    public void setLivreurId(RelationObject livreurId) {
        this.livreurId = livreurId;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}