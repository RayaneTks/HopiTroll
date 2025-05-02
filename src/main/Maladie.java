package main;

/**

 Représente une maladie affectant une créature.
 */
public class Maladie {
    private String nomComplet;
    private String nomAbrege;
    private int niveauActuel;
    private int niveauMax;

    public Maladie(String grippe, String grp, int i, int i1) {
    }

    /**

     Incrémente le niveau de la maladie.

     Prévoir assert niveauActuel <= niveauMax.
     */
    public void augmenterNiveau() {}

    /**

     Décrémente le niveau de la maladie.

     Prévoir assert niveauActuel >= 0.
     */
    public void diminuerNiveau() {}

    /**

     Indique si la maladie est létale (niveauActuel >= niveauMax).

     @return true si létale, sinon false.
     */
    public boolean estLetale() { return false; }


    public int getNiveauActuel() {
        return niveauActuel;
    }
}
