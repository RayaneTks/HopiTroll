package Class;
/**
 * Représente une maladie affectant une créature.
 */
public class Maladie {
    private String nomComplet;
    private String nomAbrege;
    private int niveauActuel;
    private int niveauMax;

    public Maladie(String nomComplet, String nomAbrege, int niveauActuel, int niveauMax) {
        this.nomComplet = nomComplet;
        this.nomAbrege = nomAbrege;
        this.niveauActuel = niveauActuel;
        this.niveauMax = niveauMax;
    }

    public void augmenterNiveau() {
        if (niveauActuel < niveauMax) {
            niveauActuel++;
        }
    }

    public void diminuerNiveau() {
        if (niveauActuel > 0) {
            niveauActuel--;
        }
    }

    public boolean estLetale() {
        return niveauActuel >= niveauMax;
    }

    public String getNomComplet() { return nomComplet; }
    public String getNomAbrege() { return nomAbrege; }
    public int getNiveauActuel() { return niveauActuel; }
    public int getNiveauMax() { return niveauMax; }
}
