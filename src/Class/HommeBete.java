package Class;
import java.util.List;

/**
 * Représente un Homme-bête, créature bestiale pouvant contaminer.
 */
public class HommeBete extends Creature {
    public HommeBete(String nomComplet, String sexe, double poids, double taille, int age, int moral) {
        super(nomComplet, sexe, poids, taille, age, moral);
    }
    public void contaminer(Creature cible, Maladie maladie) {
        cible.tomberMalade(maladie);
        System.out.println(nomComplet + " contamine " + cible.getNomComplet());
    }
}
