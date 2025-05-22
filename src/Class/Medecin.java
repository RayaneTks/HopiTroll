package Class;
/**
 * Représente un médecin de l'hôpital.
 */
public class Medecin {
    private String nom;
    private String sexe;
    private int age;
    private TypeCreature typeCreature;

    public Medecin(String nom, String sexe, int age, TypeCreature typeCreature) {
        this.nom = nom;
        this.sexe = sexe;
        this.age = age;
        this.typeCreature = typeCreature;
    }
    
    public String getNom() { return nom; }


    public void examiner(ServiceMedical service) {
        service.afficherInfos();
    }

    public void soigner(ServiceMedical service) {
        service.soignerCreatures();
    }

    public void reviserBudget(ServiceMedical service) {
        service.reviserBudget();
    }

    public void transferer(Creature creature, ServiceMedical src, ServiceMedical dest) {
        src.enleverCreature(creature);
        dest.ajouterCreature(creature);
    }
}
