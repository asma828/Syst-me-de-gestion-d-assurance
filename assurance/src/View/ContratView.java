package View;

import Models.Client;
import Models.Conseiller;
import Models.Contrat;
import service.ClientService;
import service.ContratService;
import enums.TypeContrat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ContratView {
    private ContratService contratService = new ContratService();
    private ClientService clientService = new ClientService();
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void menuContrat() {
        while (true) {
            System.out.println("\n=== GESTION DES CONTRATS ===");
            System.out.println("1. Ajouter un contrat");
            System.out.println("2. Supprimer un contrat");
            System.out.println("3. Afficher un contrat par ID");
            System.out.println("4. Afficher les contrats d'un client");
            System.out.println("0. Retour au menu principal");
            System.out.print("Choisissez une option: ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterContrat();
                    break;
                case 2:
                    supprimerContrat();
                    break;
                case 3:
                    afficherContrat();
                    break;
                case 4:
                    afficherContratsClient();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Option invalide!");
            }
        }
    }

    private void ajouterContrat() {
        System.out.println("Types de contrat disponibles:");
        for (TypeContrat type : TypeContrat.values()) {
            System.out.println("- " + type.name());
        }
        System.out.print("Type de contrat: ");
        String typeStr = scanner.nextLine().toUpperCase();

        try {
            TypeContrat typeContrat = TypeContrat.valueOf(typeStr);
            System.out.print("Date de début (yyyy-MM-dd): ");
            String dateDebutStr = scanner.nextLine();
            LocalDate dateDebut = LocalDate.parse(dateDebutStr, formatter);

            System.out.print("Date de fin (yyyy-MM-dd): ");
            String dateFinStr = scanner.nextLine();
            LocalDate dateFin = LocalDate.parse(dateFinStr, formatter);

            System.out.print("ID du client: ");
            int clientId = scanner.nextInt();

            Optional<Client> client = clientService.rechercherClientParId(clientId);
            if (client.isPresent()) {
                Contrat contrat = new Contrat(typeContrat, dateDebut, dateFin, client.get());
                int id = contratService.ajouterContrat(contrat);

                if (id > 0) {
                    System.out.println("Contrat ajouté avec succès! ID: " + id);
                } else {
                    System.out.println("Erreur lors de l'ajout du contrat.");
                }
            } else {
                System.out.println("Client introuvable avec cet ID.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Type de contrat invalide.");
        } catch (DateTimeParseException e) {
            System.out.println("Format de date invalide. Utilisez yyyy-MM-dd");
        }
    }

    private void supprimerContrat() {
        System.out.print("ID du contrat à supprimer: ");
        int id = scanner.nextInt();

        if (contratService.supprimerContrat(id)) {
            System.out.println("Contrat supprimé avec succès!");
        } else {
            System.out.println("Erreur lors de la suppression ou contrat introuvable.");
        }
    }

    private void afficherContrat() {
        System.out.print("ID du contrat: ");
        int id = scanner.nextInt();

        Optional<Contrat> contrat = contratService.afficherContratParId(id);
        if (contrat.isPresent()) {
            System.out.println("Contrat trouvé: " + contrat.get());
        } else {
            System.out.println("Aucun contrat trouvé avec cet ID.");
        }
    }

    private void afficherContratsClient() {
        System.out.print("ID du client: ");
        int clientId = scanner.nextInt();

        List<Contrat> contrats = contratService.afficherContratsParClientId(clientId);
        if (contrats.isEmpty()) {
            System.out.println("Aucun contrat trouvé pour ce client.");
        } else {
            System.out.println("Contrats du client:");
            contrats.forEach(System.out::println);
        }
    }
}

