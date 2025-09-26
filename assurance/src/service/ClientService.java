package service;

import DAO.ClientDAO;
import Models.Client;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientService {
    private ClientDAO clientDAO = new ClientDAO();

    public int ajouterClient(Client client) {
        return clientDAO.create(client);
    }

    public boolean supprimerClient(int id) {
        return clientDAO.delete(id);
    }

    // Recherche par nom avec tri alphabétique (Stream API)
    public List<Client> rechercherClientParNom(String nom) {
        return clientDAO.findAll().stream()
                .filter(client -> client.getNom().toLowerCase().contains(nom.toLowerCase()))
                .sorted(Comparator.comparing(Client::getNom))
                .collect(Collectors.toList());
    }

    // Recherche par ID avec Optional
    public Optional<Client> rechercherClientParId(int id) {
        return clientDAO.findById(id);
    }

    // Afficher les clients d'un conseiller (Stream API)
    public List<Client> afficherClientsParConseillerId(int conseillerId) {
        return clientDAO.findAll().stream()
                .filter(client -> client.getConseiller() != null &&
                        client.getConseiller().getId() == conseillerId)
                .collect(Collectors.toList());
    }
}
