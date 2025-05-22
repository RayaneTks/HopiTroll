package Class;

import Interfaces.Demoralisante;
import Interfaces.Contagieuse;
import Interfaces.Regenerable;
import java.util.*;

public class mainConsole {
    public static void main(String[] args) {
        System.out.println("=== Test complet du système HopiTroll avec logs détaillés ===");

        // Création de maladies
        System.out.print("Création de maladies... ");
        Maladie grippe = new Maladie("Grippe", "GRP", 0, 2);
        Maladie peste = new Maladie("Peste", "PST", 2, 2);
        Maladie rage = new Maladie("Rage", "RAG", 0, 1);
        System.out.println("OK");

        // Création de créatures de tous types
        System.out.print("Création de créatures... ");
        Elfe elfe = new Elfe("Legolas", "M", 70, 1.85, 120, 80);
        Nain nain = new Nain("Gimli", "M", 90, 1.40, 140, 90);
        Orque orque = new Orque("Ugluk", "M", 120, 2.00, 35, 70);
        Zombie zombie = new Zombie("Bob le Zombie", "M", 80, 1.75, 50, 40);
        Vampire vampire = new Vampire("Dracula", "M", 75, 1.90, 400, 60);
        Reptilien reptilien = new Reptilien("Lizzy", "F", 55, 1.60, 60, 85);
        HommeBete hommeBete = new HommeBete("Fenrir", "M", 110, 1.95, 40, 65);
        Lycanthrope lycanthrope = new Lycanthrope("LoupGarou", "M", 100, 2.10, 30, 70);
        System.out.println("OK");

        // Groupe pour tests d'interaction
        List<Creature> groupe = Arrays.asList(nain, orque, zombie, vampire, reptilien, hommeBete, lycanthrope);

        // Test maladie, soin, attente, hurlement, s'emporter
        System.out.println("--- Test maladie, soin, attente, hurlement, s'emporter ---");
        elfe.tomberMalade(grippe);
        elfe.tomberMalade(peste);
        System.out.println("Maladies de l'elfe : " + elfe.getMaladies().size() + " (attendu : 2) : " +
                (elfe.getMaladies().size() == 2 ? "OK" : "PB"));
        elfe.etreSoigne();
        System.out.println("Maladies de l'elfe après soin : " + elfe.getMaladies().size() + " (attendu : 1) : " +
                (elfe.getMaladies().size() == 1 ? "OK" : "PB"));
        for (int i = 0; i < 20; i++) {
            elfe.attendre();
            System.out.println("Moral de " + elfe.getNomComplet() + " après attente " + (i+1) + ": " + elfe.getMoral());
        }

        // Test trépas sans régénération
        System.out.println("--- Test trépas sans régénération ---");
        nain.tomberMalade(new Maladie("Peste", "PST", 2, 2));
        boolean mort = nain.trepasser(groupe);
        System.out.println("Nain est mort ? " + mort + " (attendu : true) : " + (mort ? "OK" : "PB"));

        // Test trépas avec régénération et contamination
        System.out.println("--- Test trépas avec régénération et contamination ---");
        zombie.tomberMalade(new Maladie("Virus mortel", "VM", 2, 2));
        boolean zombieMort = zombie.trepasser(groupe);
        System.out.println("Zombie est mort ? " + zombieMort + " (attendu : false) : " + (!zombieMort ? "OK" : "PB"));
        for (Creature c : groupe) {
            System.out.println(c.getNomComplet() + " a " + c.getMaladies().size() + " maladie(s)");
        }

        // Test contamination directe
        System.out.println("--- Test contamination directe ---");
        orque.contaminer(elfe, new Maladie("Rhume orque", "RHQ", 0, 1));
        System.out.println("Maladies de l'elfe : " + elfe.getMaladies().size());

        // Test démoralisation
        System.out.println("--- Test démoralisation ---");
        vampire.demoraliser(groupe);
        for (Creature c : groupe) {
            System.out.println(c.getNomComplet() + " moral : " + c.getMoral());
        }

        // Test régénération
        System.out.println("--- Test régénération ---");
        zombie.regenerer();
        vampire.regenerer();
        System.out.println("Moral du zombie après régénération : " + zombie.getMoral());
        System.out.println("Moral du vampire après régénération : " + vampire.getMoral());

        // Test ServiceMedical : type et capacité
        System.out.println("--- Test ServiceMedical : type et capacité ---");
        ServiceStandard serviceElfes = new ServiceStandard("Salle des Elfes", 50, 2, Budget.FAIBLE, TypeCreature.ELFE);
        boolean ajout1 = serviceElfes.ajouterCreature(elfe);
        System.out.println("Ajout Legolas (Elfe) : " + (ajout1 ? "OK" : "Refusé"));
        boolean ajout2 = serviceElfes.ajouterCreature(nain); // Devrait être refusé si vérification de type
        System.out.println("Ajout Gimli (Nain) : " + (ajout2 ? "OK" : "Refusé"));
        boolean ajout3 = serviceElfes.ajouterCreature(new Elfe("Elrond", "M", 68, 1.80, 300, 90)); // Devrait être accepté
        System.out.println("Ajout Elrond (Elfe) : " + (ajout3 ? "OK" : "Refusé"));
        boolean ajout4 = serviceElfes.ajouterCreature(new Elfe("Tauriel", "F", 60, 1.75, 120, 95)); // Devrait être refusé (capacité max)
        System.out.println("Ajout Tauriel (Elfe) : " + (ajout4 ? "OK" : "Refusé") + " (capacité max atteinte)");
        serviceElfes.afficherInfos();

        // Test CentreDeQuarantaine
        System.out.println("--- Test CentreDeQuarantaine ---");
        CentreDeQuarantaine quarantaine = new CentreDeQuarantaine("Quarantaine Orques", 30, 2, Budget.INSUFFISANT, TypeCreature.ORQUE, true);
        boolean ajout5 = quarantaine.ajouterCreature(orque);
        System.out.println("Ajout Ugluk (Orque) en quarantaine : " + (ajout5 ? "OK" : "Refusé"));
        quarantaine.afficherInfos();
        quarantaine.reviserBudget();
        System.out.println("Budget après révision (isolation) : " + quarantaine.budget);

        // Test Crypte
        System.out.println("--- Test Crypte ---");
        Crypte crypte = new Crypte("Crypte des Vampires", 40, 2, Budget.MEDIOCRE, TypeCreature.VAMPIRE, "Haute", 2.0);
        boolean ajout6 = crypte.ajouterCreature(vampire);
        System.out.println("Ajout Dracula (Vampire) en crypte : " + (ajout6 ? "OK" : "Refusé"));
        crypte.afficherInfos();
        crypte.reviserBudget();
        System.out.println("Budget après révision (température basse) : " + crypte.budget);

        // Test Medecin sur plusieurs services
        System.out.println("--- Test Medecin sur plusieurs services ---");
        Medecin medecin = new Medecin("House", "M", 45, TypeCreature.HOMMEBETE);
        medecin.examiner(serviceElfes);
        medecin.soigner(serviceElfes);
        medecin.examiner(quarantaine);
        medecin.soigner(quarantaine);

        // Test HopitalFantastique avec plusieurs services et médecins
        System.out.println("--- Test HopitalFantastique multi-services ---");
        HopitalFantastique hopital = new HopitalFantastique("HopiTroll", 5);
        hopital.ajouterService(serviceElfes);
        hopital.ajouterService(quarantaine);
        hopital.ajouterService(crypte);
        hopital.ajouterMedecin(medecin);
        hopital.afficherNbCreatures();
        hopital.afficherToutesCreatures();
        hopital.simuler();

        System.out.println("\n=== Fin des tests complets HopiTroll ===");
    }
}
