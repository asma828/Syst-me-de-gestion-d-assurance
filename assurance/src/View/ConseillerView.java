package View;

import Models.Client;
import Models.Conseiller;
import service.ConseillerService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConseillerView {
    private ConseillerService conseillerService = new ConseillerService();
    private Scanner scanner = new Scanner(System.in);

    public void menuConseiller() {
        while (true) {
            System.out.println("\n=== GESTION DES CONSEILLER ===");
            System.out.println("1. Ajouter un conseiller");
            System.out.println("2. Supprimer un conseiller");
            System.out.println("3. Rechercher un conseiller par ID");
            System.out.println("4. Afficher les clients d'un conseiller");
            System.out.println("0. Retour au menu principal");
            System.out.print("Choisissez une option: ");

            int choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix) {
                case 1:
                    ajouterConseiller();
                    break;
                case 2:
                    supprimerConseiller();
                    break;
                case 3:
                    rechercherConseiller();
                    break;
                case 4:
                    afficherClientsConseiller();
                    break;
                case 5:
                    ;
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Option invalide!");

            }
        }
    }

    private void ajouterConseiller(){
        System.out.println("Nom: ");
        String nom = scanner.nextLine();
        System.out.println("Prenom: ");
        String prenom = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        Conseiller conseiller = new Conseiller(nom, prenom, email);
        int id = conseillerService.ajouterConseiller(conseiller);

        if (id > 0) {
            System.out.println("Conseiller ajouté avec succès! ID: " + id);
        } else {
            System.out.println("Erreur lors de l'ajout du conseiller.");
        }
    }

    private void supprimerConseiller() {
        System.out.print("ID du conseiller à supprimer: ");
        int id = scanner.nextInt();

        if (conseillerService.deleteConseiller(id)) {
            System.out.println("Conseiller supprimé avec succès!");
        } else {
            System.out.println("Erreur lors de la suppression ou conseiller introuvable.");
        }
    }

    private void rechercherConseiller() {
        System.out.print("ID du conseiller: ");
        int id = scanner.nextInt();

        Optional<Conseiller> conseiller = conseillerService.rechercherConseillerParId(id);
        if (conseiller.isPresent()) {
            System.out.println("Conseiller trouvé: " + conseiller.get());
        } else {
            System.out.println("Aucun conseiller trouvé avec cet ID.");
        }
    }

    private void afficherClientsConseiller() {
        System.out.print("ID du conseiller: ");
        int id = scanner.nextInt();

        List<Client> clients = conseillerService.afficherClientsParConseillerId(id);
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé pour ce conseiller.");
        } else {
            System.out.println("Clients du conseiller:");
            clients.forEach(System.out::println);
        }
    }
    }

