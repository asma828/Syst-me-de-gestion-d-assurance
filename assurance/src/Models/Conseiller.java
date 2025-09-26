package Models;

public class Conseiller extends Person {
    private int id;

    // Constructor  pour creé un nouveaux Conseiller
    public Conseiller(String nom, String prenom, String email) {
        super(nom, prenom, email);
    }

    // Constructeur utilisé lors de la lecture à partir de la base de données (avec id)
    public Conseiller(int id, String nom, String prenom, String email) {
        super(nom, prenom, email);
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Conseiller {" +
                "id=" + id +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
