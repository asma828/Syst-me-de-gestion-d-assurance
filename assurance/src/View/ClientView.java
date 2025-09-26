package View;

import Models.Client;
import Models.Conseiller;
import service.ClientService;
import service.ConseillerService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientView {
    private ClientService clientService = new ClientService();
    private ConseillerService conseillerService = new ConseillerService();
    private Scanner scanner = new Scanner(System.in);

    public void menuClient() {
        while (true) {
            System.out.println("\n=== GESTION DES CLIENTS ===");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Supprimer un client");
            System.out.println("3. Rechercher un client par nom");
            System.out.println("4. Rechercher un client par ID");
            System.out.println("5. Afficher les clients d'un conseiller");
            System.out.println("0. Retour au menu principal");
            System.out.print("Choisissez une option: ");

            int choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix) {
                case 1:
                    ajouterClient();
                    break;
                case 2:
                    supprimerClient();
                    break;
                case 3:
                    rechercherClientParNom();
                    break;
                case 4:
                    rechercherClientParId();
                    break;
                case 5:
                    afficherClientsConseiller();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Option invalide!");

            }
        }
    }

    private void ajouterClient() {
        System.out.print("Nom: ");
        String nom = scanner.nextLine();
        System.out.print("Prénom: ");
        String prenom = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("ID du conseiller: ");
        int conseillerId = scanner.nextInt();
        scanner.nextLine();
        Optional<Conseiller> conseiller = conseillerService.rechercherConseillerParId(conseillerId);
        if (conseiller.isPresent()) {
            Client client = new Client(nom, prenom, email, conseiller.get());
            int id = clientService.ajouterClient(client);

            if (id > 0) {
                System.out.println("Client ajouté avec succès! ID: " + id);
            } else {
                System.out.println("Erreur lors de l'ajout du client.");
            }
        } else {
            System.out.println("Conseiller introuvable avec cet ID.");
        }
    }

    private void supprimerClient() {
        System.out.print("ID du client à supprimer: ");
        int id = scanner.nextInt();

        if (clientService.supprimerClient(id)) {
            System.out.println("Client supprimé avec succès!");
        } else {
            System.out.println("Erreur lors de la suppression ou client introuvable.");
        }
    }

    private void rechercherClientParNom() {
        System.out.print("Nom du client: ");
        String nom = scanner.nextLine();

        List<Client> clients = clientService.rechercherClientParNom(nom);
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé avec ce nom.");
        } else {
            System.out.println("Clients trouvés:");
            clients.forEach(System.out::println);
        }
    }

    private void rechercherClientParId() {
        System.out.print("ID du client: ");
        int id = scanner.nextInt();

        Optional<Client> client = clientService.rechercherClientParId(id);
        if (client.isPresent()) {
            System.out.println("Client trouvé: " + client.get());
        } else {
            System.out.println("Aucun client trouvé avec cet ID.");
        }
    }

    private void afficherClientsConseiller() {
        System.out.print("ID du conseiller: ");
        int id = scanner.nextInt();

        List<Client> clients = clientService.afficherClientsParConseillerId(id);
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé pour ce conseiller.");
        } else {
            System.out.println("Clients du conseiller:");
            clients.forEach(System.out::println);
        }
    }
    }


