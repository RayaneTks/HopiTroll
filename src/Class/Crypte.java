package Class;
public class Crypte extends ServiceMedical {
    private String ventilation;
    private double temperature;

    public Crypte(String nom, double superficie, int capaciteMax, Budget budget, TypeCreature typeCreature, String ventilation, double temperature) {
        super(nom, superficie, capaciteMax, budget, typeCreature);
        this.ventilation = ventilation;
        this.temperature = temperature;
    }

    @Override
    public void reviserBudget() {
        // Exemple : température basse = budget plus faible
        if (temperature < 5) {
            budget = Budget.FAIBLE;
        }
    }
}
