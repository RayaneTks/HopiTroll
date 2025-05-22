package Class;
import java.util.List;

/**
 * Représente un Reptilien, créature sensible à la démoralisation.
 */
public class Reptilien extends Creature {
    public Reptilien(String nomComplet, String sexe, double poids, double taille, int age, int moral) {
        super(nomComplet, sexe, poids, taille, age, moral);
    }
    public void demoraliser(List<Creature> creatures) {
        for (Creature c : creatures) {
            c.setMoral(Math.max(0, c.getMoral() - 1));
        }
        System.out.println(nomComplet + " démoralise les autres créatures !");
    }
}
