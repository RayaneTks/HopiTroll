package Class;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe abstraite pour un service médical.
 */
public abstract class ServiceMedical {
    protected String nom;
    protected double superficie;
    protected int capaciteMax;
    protected List<Creature> creatures;
    protected Budget budget;
    protected TypeCreature typeCreature;

    public ServiceMedical(String nom, double superficie, int capaciteMax, Budget budget, TypeCreature typeCreature) {
        this.nom = nom;
        this.superficie = superficie;
        this.capaciteMax = capaciteMax;
        this.budget = budget;
        this.typeCreature = typeCreature;
        this.creatures = new ArrayList<>();
    }
    
    public String getNom() { return nom; }


    public void afficherInfos() {
        System.out.println("Service : " + nom + " (" + typeCreature + ")");
        System.out.println("Budget : " + budget + ", Capacité : " + capaciteMax);
        for (Creature c : creatures) {
            System.out.println(" - " + c.getNomComplet() + " (moral : " + c.getMoral() + ")");
        }
    }

    /**
     * Ajoute une créature au service si la capacité n'est pas atteinte et si le type correspond.
     * @param c la créature à ajouter
     * @return true si ajoutée, false sinon
     */
    public boolean ajouterCreature(Creature c) {
        if (c == null) return false;
        if (creatures.size() >= capaciteMax) return false;
        // Vérifie le type de créature
        String nomClasse = c.getClass().getSimpleName().toUpperCase();
        if (!nomClasse.equals(typeCreature.name())) return false;
        creatures.add(c);
        return true;
    }

    public void enleverCreature(Creature c) {
        creatures.remove(c);
    }

    public void soignerCreatures() {
        for (Creature c : creatures) {
            c.etreSoigne();
        }
    }

    public abstract void reviserBudget();

    public List<Creature> getCreatures() { return creatures; }
}
