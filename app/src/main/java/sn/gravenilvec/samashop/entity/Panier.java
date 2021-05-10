package sn.gravenilvec.samashop.entity;

import java.util.Date;
import java.util.List;

/**
 * Cette classe permet de sauvegarder la liste des achats
 * Chaque panier contient un ou plusieurs articles
 */

public class Panier {

    private int idPanier;
    private int personneId;
    private String libelle;
    private String dateCreation;
    private String dateModification;
    private Double montantPrevu;

    public Panier() {
    }

    public Panier(int personneId, String libelle, String dateCreation, String dateModification, Double montantPrevu) {
        this.personneId = personneId;
        this.libelle = libelle;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.montantPrevu = montantPrevu;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public int getPersonneId() {
        return personneId;
    }

    public void setPersonneId(int personneId) {
        this.personneId = personneId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateModification() {
        return dateModification;
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }

    public Double getMontantPrevu() {
        return montantPrevu;
    }

    public void setMontantPrevu(Double montantPrevu) {
        this.montantPrevu = montantPrevu;
    }
}
