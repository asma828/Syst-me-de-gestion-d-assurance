
import Models.Contrat;
import View.*;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ClientView clientView = new ClientView();
        ConseillerView conseillerView = new ConseillerView();
        ContratView contratView = new ContratView();
        SinistreView sinistreView = new SinistreView();

        boolean run = true;

        while (run) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gérer les conseillers");
            System.out.println("2. Gérer les clients");
            System.out.println("3. Gérer les contrats");
            System.out.println("4. Gérer les sinistres");
            System.out.println("0. Quitter");
            System.out.print("Choisissez une option: ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    conseillerView.menuConseiller();
                    break;
                case 2:
                    clientView.menuClient();
                    break;
                case 3:
                    contratView.menuContrat();
                    break;
                case 4:
                    sinistreView.menuSinistre();
                    break;
                case 0:
                    System.out.println(" Au revoir !");
                    run = false;
                    break;
                default:
                    System.out.println(" Option invalide !");
            }
        }
    }
}
