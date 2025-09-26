package View;

import Models.Contrat;
import Models.Sinistre;
import enums.TypeSinistre;
import service.ContratService;
import service.SinistreService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SinistreView {
    private SinistreService sinistreService = new SinistreService();
    private ContratService contratService = new ContratService();
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void menuSinistre() {
        while (true) {
            System.out.println("\n=== GESTION DES SINISTRES ===");
            System.out.println("1. Ajouter un sinistre");
            System.out.println("2. Supprimer un sinistre");
            System.out.println("3. Calculer coûts totaux des sinistres d'un client");
            System.out.println("4. Rechercher un sinistre par ID");
            System.out.println("5. Afficher sinistres d'un contrat");
            System.out.println("6. Afficher sinistres triés par montant décroissant");
            System.out.println("7. Afficher sinistres d'un client");
            System.out.println("8. Afficher sinistres avant une date");
            System.out.println("9. Afficher sinistres avec coût supérieur à un montant");
            System.out.println("0. Retour au menu principal");
            System.out.print("Choisissez une option: ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterSinistre();
                    break;
                case 2:
                    supprimerSinistre();
                    break;
                case 3:
                    calculerCoutsTotaux();
                    break;
                case 4:
                    rechercherSinistre();
                    break;
                case 5:
                    afficherSinistresContrat();
                    break;
                case 6:
                    afficherSinistresTriesParMontant();
                    break;
                case 7:
                    afficherSinistresClient();
                    break;
                case 8:
                    afficherSinistresAvantDate();
                    break;
                case 9:
                    afficherSinistresAvecCoutSuperieur();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Option invalide!");
            }
        }
    }

    private void ajouterSinistre() {
        System.out.println("Types de sinistre disponibles:");
        for (TypeSinistre type : TypeSinistre.values()) {
            System.out.println("- " + type.name());
        }
        System.out.print("Type de sinistre: ");
        String typeStr = scanner.nextLine().toUpperCase();

        try {
            TypeSinistre typeSinistre = TypeSinistre.valueOf(typeStr);
            System.out.print("Date et heure (yyyy-MM-dd HH:mm): ");
            String dateTimeStr = scanner.nextLine();
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

            System.out.print("Coût: ");
            double cout = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("ID du contrat: ");
            int contratId = scanner.nextInt();

            Optional<Contrat> contrat = contratService.afficherContratParId(contratId);
            if (contrat.isPresent()) {
                Sinistre sinistre = new Sinistre(typeSinistre, dateTime, cout, description, contrat.get());
                int id = sinistreService.ajouterSinistre(sinistre);

                if (id > 0) {
                    System.out.println("Sinistre ajouté avec succès! ID: " + id);
                } else {
                    System.out.println("Erreur lors de l'ajout du sinistre.");
                }
            } else {
                System.out.println("Contrat introuvable avec cet ID.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Type de sinistre invalide.");
        } catch (DateTimeParseException e) {
            System.out.println("Format de date/heure invalide. Utilisez yyyy-MM-dd HH:mm");
        }
    }

    private void supprimerSinistre() {
        System.out.print("ID du sinistre à supprimer: ");
        int id = scanner.nextInt();

        if (sinistreService.supprimerSinistre(id)) {
            System.out.println("Sinistre supprimé avec succès!");
        } else {
            System.out.println("Erreur lors de la suppression ou sinistre introuvable.");
        }
    }

    private void calculerCoutsTotaux() {
        System.out.print("ID du client: ");
        int clientId = scanner.nextInt();

        double coutTotal = sinistreService.calculerCoutsTotauxParClientId(clientId);
        System.out.println("Coût total des sinistres du client: " + coutTotal + " €");
    }

    private void rechercherSinistre() {
        System.out.print("ID du sinistre: ");
        int id = scanner.nextInt();

        Optional<Sinistre> sinistre = sinistreService.rechercherSinistreParId(id);
        if (sinistre.isPresent()) {
            System.out.println("Sinistre trouvé: " + sinistre.get());
        } else {
            System.out.println("Aucun sinistre trouvé avec cet ID.");
        }
    }

    private void afficherSinistresContrat() {
        System.out.print("ID du contrat: ");
        int contratId = scanner.nextInt();

        List<Sinistre> sinistres = sinistreService.afficherSinistresParContratId(contratId);
        if (sinistres.isEmpty()) {
            System.out.println("Aucun sinistre trouvé pour ce contrat.");
        } else {
            System.out.println("Sinistres du contrat:");
            sinistres.forEach(System.out::println);
        }
    }

    private void afficherSinistresTriesParMontant() {
        List<Sinistre> sinistres = sinistreService.afficherSinistresTriesParMontant();
        if (sinistres.isEmpty()) {
            System.out.println("Aucun sinistre trouvé.");
        } else {
            System.out.println("Sinistres triés par montant décroissant:");
            sinistres.forEach(System.out::println);
        }
    }

    private void afficherSinistresClient() {
        System.out.print("ID du client: ");
        int clientId = scanner.nextInt();

        List<Sinistre> sinistres = sinistreService.afficherSinistresParClientId(clientId);
        if (sinistres.isEmpty()) {
            System.out.println("Aucun sinistre trouvé pour ce client.");
        } else {
            System.out.println("Sinistres du client:");
            sinistres.forEach(System.out::println);
        }
    }

    private void afficherSinistresAvantDate() {
        System.out.print("Date limite (yyyy-MM-dd HH:mm): ");
        String dateStr = scanner.nextLine();

        try {
            LocalDateTime date = LocalDateTime.parse(dateStr, formatter);
            List<Sinistre> sinistres = sinistreService.afficherSinistresAvantDate(date);

            if (sinistres.isEmpty()) {
                System.out.println("Aucun sinistre trouvé avant cette date.");
            } else {
                System.out.println("Sinistres avant " + date + ":");
                sinistres.forEach(System.out::println);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Format de date/heure invalide. Utilisez yyyy-MM-dd HH:mm");
        }
    }

    private void afficherSinistresAvecCoutSuperieur() {
        System.out.print("Montant minimum: ");
        double montant = scanner.nextDouble();

        List<Sinistre> sinistres = sinistreService.afficherSinistresAvecCoutSuperieur(montant);
        if (sinistres.isEmpty()) {
            System.out.println("Aucun sinistre trouvé avec un coût supérieur à " + montant + " €");
        } else {
            System.out.println("Sinistres avec coût supérieur à " + montant + " €:");
            sinistres.forEach(System.out::println);
        }
    }
}
