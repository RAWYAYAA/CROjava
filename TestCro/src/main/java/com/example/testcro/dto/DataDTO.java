package com.example.testcro.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DataDTO {
    private Long id;
    private String Code_Banque_Remettant;
    private String Nom_Remettant;
    private String N_Cheque;
    private String Code_Agence;
    private String N_Compte;
    private String Cle_Compte;
    private String Montant;
}
