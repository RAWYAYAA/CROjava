package com.example.testcro;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Data {
    private String Code_Banque_Remettant;
    private String Nom_Remettant;
    private String N_Cheque;
    private String Code_Agence;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String toString() {
        return "Data{" +
                "Code_Banque_Remettant='" + Code_Banque_Remettant + '\'' +
                ", Nom_Remettant='" + Nom_Remettant + '\'' +
                ", N_Cheque='" + N_Cheque + '\'' +
                ", Code_Agence='" + Code_Agence + '\'' +
                ", N_Compte='" + N_Compte + '\'' +
                ", Cle_Compte='" + Cle_Compte + '\'' +
                ", Montant='" + Montant + '\'' +
                '}';
    }

    private String N_Compte;
    private String Cle_Compte;
    private String Montant;

    public Data() {
    }

    public Data(String code_Banque_Remettant, String nom_Remettant, String n_Cheque, String code_Agence, String n_Compte, String cle_Compte, String montant) {
        Code_Banque_Remettant = code_Banque_Remettant;
        Nom_Remettant = nom_Remettant;
        N_Cheque = n_Cheque;
        Code_Agence = code_Agence;
        N_Compte = n_Compte;
        Cle_Compte = cle_Compte;
        Montant = montant;
    }

    public String getCode_Banque_Remettant() {
        return Code_Banque_Remettant;
    }

    public void setCode_Banque_Remettant(String code_Banque_Remettant) {
        Code_Banque_Remettant = code_Banque_Remettant;
    }

    public String getNom_Remettant() {
        return Nom_Remettant;
    }

    public void setNom_Remettant(String nom_Remettant) {
        Nom_Remettant = nom_Remettant;
    }

    public String getN_Cheque() {
        return N_Cheque;
    }

    public void setN_Cheque(String n_Cheque) {
        N_Cheque = n_Cheque;
    }

    public String getCode_Agence() {
        return Code_Agence;
    }

    public void setCode_Agence(String code_Agence) {
        Code_Agence = code_Agence;
    }

    public String getN_Compte() {
        return N_Compte;
    }

    public void setN_Compte(String n_Compte) {
        N_Compte = n_Compte;
    }

    public String getCle_Compte() {
        return Cle_Compte;
    }

    public void setCle_Compte(String cle_Compte) {
        Cle_Compte = cle_Compte;
    }

    public String getMontant() {
        return Montant;
    }

    public void setMontant(String montant) {
        Montant = montant;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
