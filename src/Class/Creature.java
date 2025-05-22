package Class;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Interfaces.Contagieuse;
import Interfaces.Demoralisante;
import Interfaces.Regenerable;

/**
 * Classe abstraite représentant une créature.
 */
public abstract class Creature {
    protected String nomComplet;
    protected String sexe;
    protected double poids;
    protected double taille;
    protected int age;
    protected int moral; // 0 à 100 par défaut
    protected List<Maladie> maladies;
    
    private boolean soigneCeJour = false;
    private int joursAttente = 0;
    private boolean critique = false;


    

    public Creature(String nomComplet, String sexe, double poids, double taille, int age, int moral) {
        this.nomComplet = nomComplet;
        this.sexe = sexe;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.moral = moral;
        this.maladies = new ArrayList<>();
    }

    

    public void setSoigneCeJour(boolean b) { soigneCeJour = b; }
    public boolean isSoigneCeJour() { return soigneCeJour; }
    public void incrementerJoursAttente() { joursAttente++; }
    public int getJoursAttente() { return joursAttente; }
    public boolean isCritique() { return critique; }
    public void setCritique(boolean c) { critique = c; }


    public void attendre() {
        moral -= 5;
        if (moral < 0) moral = 0;
        System.out.println(nomComplet + " attend... Moral actuel : " + moral);
        if (moral == 0) {
            hurler();
        }
    }

    public void hurler() {
        System.out.println(nomComplet + " hurle de désespoir !");
        sEmporter();
    }

    public void sEmporter() {
        System.out.println(nomComplet + " s'emporte !");
    }

    public void tomberMalade(Maladie m) {
        maladies.add(m);
        System.out.println(nomComplet + " est tombé malade : " + m.getNomComplet());
    }

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
     * La créature meurt si trop malade, ou se régénère si elle est régénérante.
     * @param autresCreatures la liste des autres créatures du service
     * @return true si la créature doit mourir, false sinon (ex: régénération)
     */
    public boolean trepasser(List<Creature> autresCreatures) {
        for (Maladie maladie : maladies) {
            if (maladie.estLetale()) {
                System.out.println(nomComplet + " est mort(e) à cause de " + maladie.getNomComplet());

                // Démoralise d'autres créatures
                if (this instanceof Demoralisante) {
                    ((Demoralisante) this).demoraliser(autresCreatures);
                }

                // Cas des régénérants
                if (this instanceof Regenerable) {
                    ((Regenerable) this).regenerer();
                    contaminerApresRegeneration(autresCreatures);
                    System.out.println(nomComplet + " s'est régénéré et a propagé ses maladies !");
                    return false; // ne reste pas mort
                }

                // Contamine une autre créature aléatoire (pour les bestiaux non régénérants)
                if (this instanceof Contagieuse && autresCreatures != null && !autresCreatures.isEmpty()) {
                    Creature cible;
                    do {
                        cible = autresCreatures.get((int) (Math.random() * autresCreatures.size()));
                    } while (cible == this && autresCreatures.size() > 1);
                    ((Contagieuse) this).contaminer(cible, maladie);
                }

                return true; // trépas confirmé
            }
        }
        return false; // pas de maladie létale détectée
    }

    /**
     * Propage une ou plusieurs maladies à un pourcentage aléatoire du service.
     */
    private void contaminerApresRegeneration(List<Creature> autresCreatures) {
        if (autresCreatures == null || autresCreatures.isEmpty() || maladies.isEmpty()) return;

        Random rand = new Random();
        double pourcentage = 0.1 + rand.nextDouble() * 0.4; // 10% à 50%
        int nbCibles = Math.max(1, (int) Math.round(autresCreatures.size() * pourcentage));

        List<Creature> cibles = new ArrayList<>(autresCreatures);
        cibles.remove(this);
        Collections.shuffle(cibles, rand);
        if (nbCibles > cibles.size()) nbCibles = cibles.size();

        for (int i = 0; i < nbCibles; i++) {
            Creature cible = cibles.get(i);
            for (Maladie m : maladies) {
                if (rand.nextDouble() < 0.5) { // 50% de chance de transmettre chaque maladie
                    cible.tomberMalade(new Maladie(m.getNomComplet(), m.getNomAbrege(), 0, m.getNiveauMax()));
                    System.out.println(nomComplet + " a transmis " + m.getNomComplet() + " à " + cible.getNomComplet());
                }
            }
        }
    }

    // Getters et setters utiles
    public int getMoral() { return moral; }
    public void setMoral(int moral) { this.moral = moral; }
    public List<Maladie> getMaladies() { return maladies; }
    public String getNomComplet() { return nomComplet; }
}
