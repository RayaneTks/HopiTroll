package main;

import java.util.List;
import java.util.ArrayList;



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

    /**

     Fait attendre la créature, diminue le moral.

     assert moral >= 0 après appel.
     */
    public void attendre() {}

    /**

     Fait hurler la créature si moral très bas.
     */
    public void hurler() {}

    /**

     La créature s'emporte après plusieurs hurlements.
     */
    public void sEmporter() {}

    /**

     Ajoute une maladie à la liste.

     @param m la maladie à ajouter
     */
    public void tomberMalade(Maladie m) {}

    /**

     Soigne une maladie et redonne du moral.
     */
    public void etreSoigne() {}

    /**

     La créature meurt si trop malade.
     */
    public void trepasser() {}
}
