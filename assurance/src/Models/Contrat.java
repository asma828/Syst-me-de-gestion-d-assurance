package Models;


import enums.TypeContrat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Contrat {
    private int id;
    private TypeContrat typeContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Client client;

    public Contrat(int id, TypeContrat typeContrat, LocalDate dateDebut, LocalDate dateFin, Client client) {
        this.id = id;
        this.typeContrat = typeContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.client = client;
    }

    public Contrat(TypeContrat typeContrat, LocalDate dateDebut, LocalDate dateFin, Client client) {
        this.typeContrat = typeContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + id +
                ", typeContrat=" + typeContrat +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", client=" + (client != null ? client.getNom() + " " + client.getPrenom() : "null") +
                '}';
    }

}

