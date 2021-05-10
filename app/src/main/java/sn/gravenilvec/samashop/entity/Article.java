package sn.gravenilvec.samashop.entity;


public class Article {
    private int idArticle;
    private int panierId;
    private String libelle;
    private String photo;
    private String date;
    private Double montant;
    private int quantite;
    private int estAchete;

    public Article() {
    }

    public Article(int panierId, String libelle, String photo, String date, Double montant, int quantite, int estAchete) {
        this.panierId = panierId;
        this.libelle = libelle;
        this.photo = photo;
        this.date = date;
        this.montant = montant;
        this.quantite = quantite;
        this.estAchete = estAchete;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getPanierId() {
        return panierId;
    }

    public void setPanierId(int panierId) {
        this.panierId = panierId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getEstAchete() {
        return estAchete;
    }

    public void setEstAchete(int estAchete) {
        this.estAchete = estAchete;
    }
}
