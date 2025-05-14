import java.util.List;
import java.util.ArrayList;

interface Demoralisante {
    void demoraliser(List<Creature> creatures);
}

interface Contagieuse {
    void contaminer(Creature cible);
}

interface Regenerable {
    void regenerer();
}

/**

 Classe abstraite représentant une créature.
 */
public abstract class Creature {
    protected String nomComplet;
    protected String sexe;
    protected double poids;
    protected double taille;
    protected int age;
    protected int moral;
    protected List<Maladie> maladies;



    public Creature() {
        this.maladies = new ArrayList<>();
    }
    /**

     Fait attendre la créature, diminue le moral.

     assert moral >= 0 après appel.
     */
    public void attendre() {
        moral -= 10;
        if (moral < 0) moral = 0;
        System.out.println(nomComplet + " attend... Moral actuel : " + moral);
        if (moral == 0) {
            hurler();
        }
    }

    /**

     Fait hurler la créature si moral très bas.
     */
    public void hurler() {
        System.out.println(nomComplet + " hurle de désespoir !");
        sEmporter();
    }

    /**

     La créature s'emporte après plusieurs hurlements.
     */
    public void sEmporter() {
        System.out.println(nomComplet + " s'emporte !");
        // possibilité d’infecter d’autres créatures si implémente Contagieuse
    }

    /**

     Ajoute une maladie à la liste.

     @param m la maladie à ajouter
     */
    public void tomberMalade(Maladie m) {
        maladies.add(m);
        System.out.println(nomComplet + " est tombé malade : " + m.getNomComplet());
    }

    /**

     Soigne une maladie et redonne du moral.
     */
    public void etreSoigne() {
        if (!maladies.isEmpty()) {
            Maladie m = maladies.remove(0);
            System.out.println(nomComplet + " a été soigné(e) de " + m.getNomComplet());
            moral += 20;
            if (moral > 100) moral = 100;
        } else {
            System.out.println(nomComplet + " n’a pas de maladies à soigner.");
        }
    }

    /**

     La créature meurt si trop malade.
     */
    public boolean trepasser(List<Creature> autresCreatures) {
        for (Maladie maladie : maladies) {
            if (maladie.estLetale()) {
                System.out.println(nomComplet + " est mort(e) à cause de " + maladie.getNomComplet());

                // Démoralise d'autres créatures
                if (this instanceof Demoralisante) {
                    ((Demoralisante) this).demoraliser(autresCreatures);
                }

                // Contamine une autre créature aléatoire
                if (this instanceof Contagieuse && autresCreatures != null && !autresCreatures.isEmpty()) {
                    Creature cible = autresCreatures.get((int) (Math.random() * autresCreatures.size()));
                    ((Contagieuse) this).contaminer(cible);
                }

                // Régénération possible
                if (this instanceof Regenerable) {
                    ((Regenerable) this).regenerer();
                    return false; // ne reste pas mort
                }

                return true; // trépas confirmé
            }
        }
        return false; // pas de maladie létale détectée
    }
}
