package main;

import java.util.List;
import java.util.ArrayList;


/**

 Classe abstraite pour un service médical.
 */
public abstract class ServiceMedical {
    protected String nom;
    protected double superficie;
    protected int capaciteMax;
    protected List<Creature> creatures;
    protected Budget budget;
    protected TypeCreature typeCreature;

    /**

     Affiche les infos du service et des créatures.
     */
    public void afficherInfos() {}

    /**

     Ajoute une créature (vérifie le type).

     assert c.getClass() correspond à typeCreature.
     */
    public void ajouterCreature(Creature c) {}

    /**

     Enlève une créature.
     */
    public void enleverCreature(Creature c) {}

    /**

     Soigne toutes les créatures.
     */
    public void soignerCreatures() {}

    /**

     Révise le budget (méthode abstraite).
     */
    public abstract void reviserBudget();
}
