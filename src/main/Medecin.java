package main;

/**

 Représente un médecin de l'hôpital.
 */
public class Medecin {
    private String nom;
    private String sexe;
    private int age;
    private TypeCreature typeCreature;

    /**

     Affiche les infos d’un service.

     @param service le service à examiner
     */
    public void examiner(ServiceMedical service) {}

    /**

     Soigne les créatures d’un service.

     @param service le service à soigner
     */
    public void soigner(ServiceMedical service) {}

    /**

     Révise le budget d’un service.

     @param service le service concerné
     */
    public void reviserBudget(ServiceMedical service) {}

    /**

     Transfère une créature d’un service à un autre.

     @param creature la créature à transférer

     @param src le service source

     @param dest le service destination
     */
    public void transferer(Creature creature, ServiceMedical src, ServiceMedical dest) {}
}
