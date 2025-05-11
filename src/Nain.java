import java.util.ArrayList;
import java.util.List;

/**
 * Représente un Nain, créature robuste.
 */
public class Nain extends Creature {
    // Pas de comportement spécifique supplémentaire pour l'instant.
    public Nain(String nom, String sexe, double poids, double taille, int age, int moral) {
        this.nomComplet = nom;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.moral = moral;
        this.maladies = new ArrayList<>();
    }
    /**
     * Le Nain ne démoralise pas mais peut grogner ou s’énerver.
     */
    public void grogner() {
        System.out.println(nomComplet + " grogne bruyamment !");
    }
}
