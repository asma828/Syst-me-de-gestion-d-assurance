package service;

import DAO.ContratDAO;
import Models.Contrat;

import java.util.List;
import java.util.Optional;

public class ContratService {
    private ContratDAO contratDAO = new ContratDAO();

    public int ajouterContrat(Contrat contrat) {
        return contratDAO.create(contrat);
    }

    public boolean supprimerContrat(int id) {
        return contratDAO.delete(id);
    }

    public Optional<Contrat> afficherContratParId(int id) {
        return contratDAO.findById(id);
    }

    public List<Contrat> afficherContratsParClientId(int clientId) {
        return contratDAO.findByClientId(clientId);
    }
}
