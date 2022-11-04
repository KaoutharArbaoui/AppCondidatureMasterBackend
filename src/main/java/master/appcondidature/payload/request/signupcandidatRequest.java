package master.appcondidature.payload.request;


import javax.persistence.Lob;
import javax.validation.constraints.*;

public class signupcandidatRequest {


    @NotBlank
    @Size(max = 20)
    private String nom;

    @NotBlank
    @Size(max = 50)
    private String prenom;

    @NotBlank
    @Size(max = 120)
    private String cin;

    @NotBlank
    @Size(max = 20)
    private String nationalite;

    @NotBlank
    @Size(max = 60)
    private String datedenaissance;

    @NotBlank
    @Size(max = 100)
    private String adresse;

    @NotBlank
    @Size(max = 20)
    private String telephone;


    @NotBlank
    @Size(max = 30)
    private String situationprofessionnelle;

    @NotBlank
    @Size(max = 60)
    private String anneebac;

    @NotBlank
    @Size(max = 60)
    private String filierelicence;

    @NotBlank
    @Size(max = 100)
    private String universite;

    @NotBlank
    @Size(max = 20)
    private String faculte;

    @NotBlank
    @Size(max = 60)
    private String anneepremiereinscription;

    @NotBlank
    @Size(max = 20)
    private Float notes1;
    @NotBlank
    @Size(max = 20)
    private String sessions1;

    @NotBlank
    @Size(max = 20)
    private Float notes2;
    @NotBlank
    @Size(max = 20)
    private String sessions2;

    @NotBlank
    @Size(max = 20)
    private Float notes3;
    @NotBlank
    @Size(max = 20)
    private String sessions3;

    @NotBlank
    @Size(max = 20)
    private Float notes4;
    @NotBlank
    @Size(max = 20)
    private String sessions4;

    @NotBlank
    @Size(max = 20)
    private Float notes5;
    @NotBlank
    @Size(max = 20)
    private String sessions5;

    @NotBlank
    @Size(max = 20)
    private Float notes6;
    @NotBlank
    @Size(max = 20)
    private String sessions6;

    @NotBlank
    @Size(max = 60)
    private String anneelicence;

    @NotBlank
    @Lob
    private byte[] diplomelicence;

    @NotBlank
    @Size(max = 60)
    private Float noteDossier;

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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getDatedenaissance() {
        return datedenaissance;
    }

    public void setDatedenaissance(String datedenaissance) {
        this.datedenaissance = datedenaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getSituationprofessionnelle() {
        return situationprofessionnelle;
    }

    public void setSituationprofessionnelle(String situationprofessionnelle) {
        this.situationprofessionnelle = situationprofessionnelle;
    }

    public String getAnneebac() {
        return anneebac;
    }

    public void setAnneebac(String anneebac) {
        this.anneebac = anneebac;
    }

    public String getFilierelicence() {
        return filierelicence;
    }

    public void setFilierelicence(String filierelicence) {
        this.filierelicence = filierelicence;
    }

    public String getUniversite() {
        return universite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public String getFaculte() {
        return faculte;
    }

    public void setFaculte(String faculte) {
        this.faculte = faculte;
    }

    public String getAnneepremiereinscription() {
        return anneepremiereinscription;
    }

    public void setAnneepremiereinscription(String anneepremiereinscription) {
        this.anneepremiereinscription = anneepremiereinscription;
    }

    public Float getNotes1() {
        return notes1;
    }

    public void setNotes1(Float notes1) {
        this.notes1 = notes1;
    }

    public String getSessions1() {
        return sessions1;
    }

    public void setSessions1(String sessions1) {
        this.sessions1 = sessions1;
    }

    public Float getNotes2() {
        return notes2;
    }

    public void setNotes2(Float notes2) {
        this.notes2 = notes2;
    }

    public String getSessions2() {
        return sessions2;
    }

    public void setSessions2(String sessions2) {
        this.sessions2 = sessions2;
    }

    public Float getNotes3() {
        return notes3;
    }

    public void setNotes3(Float notes3) {
        this.notes3 = notes3;
    }

    public String getSessions3() {
        return sessions3;
    }

    public void setSessions3(String sessions3) {
        this.sessions3 = sessions3;
    }

    public Float getNotes4() {
        return notes4;
    }

    public void setNotes4(Float notes4) {
        this.notes4 = notes4;
    }

    public String getSessions4() {
        return sessions4;
    }

    public void setSessions4(String sessions4) {
        this.sessions4 = sessions4;
    }

    public Float getNotes5() {
        return notes5;
    }

    public void setNotes5(Float notes5) {
        this.notes5 = notes5;
    }

    public String getSessions5() {
        return sessions5;
    }

    public void setSessions5(String sessions5) {
        this.sessions5 = sessions5;
    }

    public Float getNotes6() {
        return notes6;
    }

    public void setNotes6(Float notes6) {
        this.notes6 = notes6;
    }

    public String getSessions6() {
        return sessions6;
    }

    public void setSessions6(String sessions6) {
        this.sessions6 = sessions6;
    }

    public String getAnneelicence() {
        return anneelicence;
    }

    public void setAnneelicence(String anneelicence) {
        this.anneelicence = anneelicence;
    }

    public byte[] getDiplomelicence() {
        return diplomelicence;
    }

    public void setDiplomelicence(byte[] diplomelicence) {
        this.diplomelicence = diplomelicence;
    }

    public Float getNoteDossier() {
        return noteDossier;
    }

    public void setNoteDossier(Float noteDossier) {
        this.noteDossier = noteDossier;
    }
}

