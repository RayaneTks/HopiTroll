package Class;
import java.util.List;

/**
 * Représente un Orque, créature bestiale pouvant contaminer.
 */
public class Orque extends Creature {
    public Orque(String nomComplet, String sexe, double poids, double taille, int age, int moral) {
        super(nomComplet, sexe, poids, taille, age, moral);
    }
    public void contaminer(Creature cible, Maladie maladie) {
        cible.tomberMalade(maladie);
        System.out.println(nomComplet + " contamine " + cible.getNomComplet());
    }
}