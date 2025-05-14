import java.util.List;

/**
 * Représente un Reptilien, créature sensible à la démoralisation.
 */
public class Reptilien extends Creature {
    /**
     * Peut démoraliser d'autres créatures (si besoin selon l'énoncé).
     */
    public void demoraliser(List<Creature> autresCreatures) {
        for (Creature c : autresCreatures) {
            if (c != this) {  // On ne diminue pas le moral du Reptilien lui-même
                int ancienMoral = c.moral;
                c.moral -= 5;  // Diminuer de 5 points, tu peux ajuster la valeur selon tes besoins
                if (c.moral < 0) c.moral = 0;
                System.out.println(c.nomComplet + " voit son moral passer de " + ancienMoral + " à " + c.moral);
            }
        }
    }
}
