import java.util.ArrayList;
import java.util.List;

/**
 * Représente un Elfe, créature sensible à la démoralisation.
 */
public class Elfe extends Creature {
    /**
     * Démoralise une partie des créatures du service lors du trépas.
     * Peut impacter le moral des autres créatures.
     */
    public Elfe(String nom, String sexe, double poids, double taille, int age, int moral) {
        this.nomComplet = nom;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.moral = moral;
        this.maladies = new ArrayList<>();
    }

    public void demoraliser(List<Creature> autresCreatures) {
        // TODO: Implémenter la logique de démoralisation spécifique aux Elfes.
        // assert: le moral des autres créatures doit baisser si un Elfe meurt.
        System.out.println(nomComplet + " a démoralisé les autres créatures par sa disparition.");
        for (Creature creature : autresCreatures) {
            creature.moral -= 10;
            if (creature.moral < 0) creature.moral = 0;
        }

    }
}
