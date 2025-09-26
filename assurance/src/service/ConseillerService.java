package service;

import DAO.ClientDAO;
import DAO.ConseillerDAO;
import Models.Client;
import Models.Conseiller;

import java.util.List;
import java.util.Optional;

public class ConseillerService {
    private ConseillerDAO conseillerDAO = new ConseillerDAO();
    private ClientDAO clientDAO = new ClientDAO();

    public int ajouterConseiller(Conseiller conseiller) {
        return conseillerDAO.create(conseiller);
    }

    public boolean deleteConseiller(int id) {
        return conseillerDAO.delete(id);
    }

    public Optional<Conseiller> rechercherConseillerParId(int id) {
        return conseillerDAO.findById(id);
    }

    public List<Client> afficherClientsParConseillerId(int conseillerId) {
        return clientDAO.findByConseillerId(conseillerId);
    }
}
