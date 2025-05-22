package Class;
public class CentreDeQuarantaine extends ServiceMedical {
    private boolean isolation;

    public CentreDeQuarantaine(String nom, double superficie, int capaciteMax, Budget budget, TypeCreature typeCreature, boolean isolation) {
        super(nom, superficie, capaciteMax, budget, typeCreature);
        this.isolation = isolation;
    }

    @Override
    public void reviserBudget() {
        // Exemple : isolation coûte plus cher
        if (isolation) {
            budget = Budget.INSUFFISANT;
        }
    }
}
