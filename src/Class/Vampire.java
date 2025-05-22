package Class;
import java.util.List;


import Interfaces.Demoralisante;
import Interfaces.Regenerable;

public class Vampire extends Creature implements Regenerable, Demoralisante {
    public Vampire(String nomComplet, String sexe, double poids, double taille, int age, int moral) {
        super(nomComplet, sexe, poids, taille, age, moral);
    }

    @Override
    public void regenerer() {
        this.moral = Math.min(moral + 40, 100);
        System.out.println(nomComplet + " se régénère puissamment et revient à la vie !");
    }

    @Override
    public void demoraliser(List<Creature> creatures) {
        for (Creature c : creatures) {
            if (c != this) {
                c.setMoral(Math.max(0, c.getMoral() - 10));
            }
        }
        System.out.println(nomComplet + " démoralise fortement les autres créatures !");
    }
}
