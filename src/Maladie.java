/**
 * Représente une maladie affectant une créature.
 */
public class Maladie {
    private String nomComplet;
    private String nomAbrege;
    private int niveauActuel;
    private int niveauMax;

    /**
     * Constructeur de la maladie.
     * @param nomComplet Nom complet de la maladie
     * @param nomAbrege Nom abrégé
     * @param niveauActuel Niveau actuel de gravité
     * @param niveauMax Niveau maximal avant létalité
     */
    public Maladie(String nomComplet, String nomAbrege, int niveauActuel, int niveauMax) {
        this.nomComplet = nomComplet;
        this.nomAbrege = nomAbrege;
        this.niveauActuel = niveauActuel;
        this.niveauMax = niveauMax;
    }

    /** Incrémente le niveau de la maladie, sans dépasser le niveau max. */
    public void augmenterNiveau() {
        if (niveauActuel < niveauMax) {
            niveauActuel++;
        }
    }

    /** Décrémente le niveau de la maladie, sans descendre sous 0. */
    public void diminuerNiveau() {
        if (niveauActuel > 0) {
            niveauActuel--;
        }
    }

    /** Indique si la maladie est létale (niveau actuel >= niveau max). */
    public boolean estLetale() {
        return niveauActuel >= niveauMax;
    }

    // Getters et setters utiles
    public String getNomComplet() { return nomComplet; }
    public String getNomAbrege() { return nomAbrege; }
    public int getNiveauActuel() { return niveauActuel; }
    public int getNiveauMax() { return niveauMax; }
}
