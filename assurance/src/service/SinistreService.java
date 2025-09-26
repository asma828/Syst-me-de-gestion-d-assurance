package service;
import DAO.SinistreDAO;
import DAO.ContratDAO;
import Models.Sinistre;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SinistreService {
    private SinistreDAO sinistreDAO = new SinistreDAO();
    private ContratDAO contratDAO = new ContratDAO();

    public int ajouterSinistre(Sinistre sinistre) {
        return sinistreDAO.create(sinistre);
    }

    public boolean supprimerSinistre(int id) {
        return sinistreDAO.delete(id);
    }

    // Calcul des coûts totaux des sinistres d'un client (Stream API)
    public double calculerCoutsTotauxParClientId(int clientId) {
        return sinistreDAO.findAll().stream()
                .filter(sinistre -> sinistre.getContrat() != null &&
                        sinistre.getContrat().getClient() != null &&
                        sinistre.getContrat().getClient().getId() == clientId)
                .mapToDouble(Sinistre::getCout)
                .sum();
    }

    // Recherche par ID avec Optional
    public Optional<Sinistre> rechercherSinistreParId(int id) {
        return sinistreDAO.findById(id);
    }

    // Liste des sinistres d'un contrat (Stream API)
    public List<Sinistre> afficherSinistresParContratId(int contratId) {
        return sinistreDAO.findAll().stream()
                .filter(sinistre -> sinistre.getContrat() != null &&
                        sinistre.getContrat().getId() == contratId)
                .collect(Collectors.toList());
    }

    // Liste des sinistres triés par montant décroissant (Stream API)
    public List<Sinistre> afficherSinistresTriesParMontant() {
        return sinistreDAO.findAll().stream()
                .sorted(Comparator.comparing(Sinistre::getCout).reversed())
                .collect(Collectors.toList());
    }

    // Liste des sinistres d'un client (Stream API)
    public List<Sinistre> afficherSinistresParClientId(int clientId) {
        return sinistreDAO.findAll().stream()
                .filter(sinistre -> sinistre.getContrat() != null &&
                        sinistre.getContrat().getClient() != null &&
                        sinistre.getContrat().getClient().getId() == clientId)
                .collect(Collectors.toList());
    }

    // Sinistres avant une date donnée (Stream API)
    public List<Sinistre> afficherSinistresAvantDate(LocalDateTime date) {
        return sinistreDAO.findAll().stream()
                .filter(sinistre -> sinistre.getDateTime().isBefore(date))
                .collect(Collectors.toList());
    }

    // Sinistres avec coût supérieur à un montant (Stream API)
    public List<Sinistre> afficherSinistresAvecCoutSuperieur(double montant) {
        return sinistreDAO.findAll().stream()
                .filter(sinistre -> sinistre.getCout() > montant)
                .collect(Collectors.toList());
    }
}
