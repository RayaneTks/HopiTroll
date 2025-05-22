package Class;
import java.util.List;

import Interfaces.Regenerable;

public class Zombie extends Creature implements Regenerable {
    public Zombie(String nomComplet, String sexe, double poids, double taille, int age, int moral) {
        super(nomComplet, sexe, poids, taille, age, moral);
    }

    @Override
    public void regenerer() {
        this.moral = Math.min(moral + 30, 100);
        System.out.println(nomComplet + " se régénère et revient à la vie !");
    }
}
