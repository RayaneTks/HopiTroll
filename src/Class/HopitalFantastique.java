package Class;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe principale représentant l'hôpital fantastique.
 */
public class HopitalFantastique {
    private String nom;
    private int capaciteServices;
    private List<ServiceMedical> services;
    private List<Medecin> medecins;

    public HopitalFantastique(String nom, int capaciteServices) {
        this.nom = nom;
        this.capaciteServices = capaciteServices;
        this.services = new ArrayList<>();
        this.medecins = new ArrayList<>();
    }

    public void afficherNbCreatures() {
        int total = 0;
        for (ServiceMedical s : services) {
            total += s.getCreatures().size();
        }
        System.out.println("Nombre total de créatures : " + total);
    }

    public void afficherToutesCreatures() {
        for (ServiceMedical s : services) {
            s.afficherInfos();
        }
    }

    public void simuler() {
        // Simulation simple : chaque service soigne ses créatures
        for (ServiceMedical s : services) {
            s.soignerCreatures();
        }
        System.out.println("Simulation terminée !");
    }

    public void ajouterService(ServiceMedical service) {
        if (services.size() < capaciteServices) {
            services.add(service);
        }
    }

    public void ajouterMedecin(Medecin medecin) {
        medecins.add(medecin);
    }
}
